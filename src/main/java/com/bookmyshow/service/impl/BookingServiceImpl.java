package com.bookmyshow.service.impl;

import com.bookmyshow.dto.request.BookingRequest;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.entity.Booking;
import com.bookmyshow.entity.BookingSeat;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.entity.User;
import com.bookmyshow.enums.BookingStatus;
import com.bookmyshow.enums.PaymentStatus;
import com.bookmyshow.enums.SeatStatus;
import com.bookmyshow.enums.ShowStatus;
import com.bookmyshow.exception.BookingException;
import com.bookmyshow.exception.ConcurrencyException;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.exception.SeatAlreadyBookedException;
import com.bookmyshow.mapper.BookingMapper;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.BookingSeatRepository;
import com.bookmyshow.repository.ShowRepository;
import com.bookmyshow.repository.ShowSeatRepository;
import com.bookmyshow.repository.UserRepository;
import com.bookmyshow.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CompletionException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class BookingServiceImpl implements BookingService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingResponse initiateBooking(BookingRequest request, Long userId) {
        log.info("Initiating booking for user {} with {} seats for show {}",
            userId, request.getShowSeatIds().size(), request.getShowId());

        // 1. Validate show
        Show show = showRepository.findById(request.getShowId())
            .orElseThrow(() -> new ResourceNotFoundException("Show", "id", request.getShowId()));
        if (show.getStatus() != ShowStatus.SCHEDULED) {
            throw new BookingException("Show is not available for booking. Status: " + show.getStatus());
        }

        // 2. Fetch requested seats
        List<ShowSeat> showSeats = showSeatRepository.findAllByIdIn(request.getShowSeatIds());
        if (showSeats.size() != request.getShowSeatIds().size()) {
            throw new ResourceNotFoundException("ShowSeat", "ids", "Some seats not found");
        }

        // 3. Validate all seats belong to the show
        for (ShowSeat seat : showSeats) {
            if (!seat.getShow().getId().equals(request.getShowId())) {
                throw new BookingException("Seat " + seat.getId() + " does not belong to show " + request.getShowId());
            }
        }

        // 4. Parallel seat validation using CompletableFuture
        List<CompletableFuture<Void>> validationFutures = showSeats.stream()
            .map(seat -> CompletableFuture.runAsync(() -> {
                if (seat.getStatus() != SeatStatus.AVAILABLE) {
                    throw new CompletionException(new SeatAlreadyBookedException(
                        "Seat " + seat.getSeat().getRowName() + seat.getSeat().getSeatNumber()
                        + " is already " + seat.getStatus()));
                }
            }))
            .collect(Collectors.toList());

        try {
            CompletableFuture.allOf(validationFutures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            if (e.getCause() instanceof SeatAlreadyBookedException) {
                throw (SeatAlreadyBookedException) e.getCause();
            }
            throw new BookingException("Seat validation failed: " + e.getMessage());
        }

        // 5. Lock seats with optimistic locking
        try {
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (ShowSeat seat : showSeats) {
                seat.setStatus(SeatStatus.LOCKED);
                seat.setLockedAt(LocalDateTime.now());
                seat.setLockedBy(userId);
                totalAmount = totalAmount.add(seat.getPrice());
            }
            showSeatRepository.saveAll(showSeats);

            // 6. Create booking
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

            Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .bookingReference(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .totalAmount(totalAmount)
                .status(BookingStatus.PENDING)
                .bookingSeats(new ArrayList<>())
                .build();
            booking = bookingRepository.save(booking);

            // 7. Create booking seats
            for (ShowSeat showSeat : showSeats) {
                BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(booking)
                    .showSeat(showSeat)
                    .priceAtBooking(showSeat.getPrice())
                    .build();
                booking.getBookingSeats().add(bookingSeat);
            }
            bookingSeatRepository.saveAll(booking.getBookingSeats());

            log.info("Booking created successfully: {} for user {}", booking.getBookingReference(), userId);
            return bookingMapper.toResponse(booking);

        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Optimistic lock conflict during booking for user {}", userId);
            throw new ConcurrencyException("These seats were just booked by another user. Please select different seats.");
        }
    }

    @Override
    @Transactional
    public BookingResponse confirmBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingException("Booking cannot be confirmed. Current status: " + booking.getStatus());
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        for (BookingSeat bs : booking.getBookingSeats()) {
            bs.getShowSeat().setStatus(SeatStatus.BOOKED);
        }
        bookingRepository.save(booking);
        log.info("Booking confirmed: {}", booking.getBookingReference());
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingException("Booking is already cancelled");
        }

        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new BookingException("Booking cannot be cancelled. Current status: " + booking.getStatus());
        }

        booking.setStatus(BookingStatus.CANCELLED);
        for (BookingSeat bs : booking.getBookingSeats()) {
            ShowSeat showSeat = bs.getShowSeat();
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeat.setLockedAt(null);
            showSeat.setLockedBy(null);
        }

        if (booking.getPayment() != null) {
            booking.getPayment().setStatus(PaymentStatus.REFUNDED);
        }

        bookingRepository.save(booking);
        log.info("Booking cancelled: {}", booking.getBookingReference());
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BookingResponse> getUserBookings(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings = bookingRepository.findByUserIdOrderByBookingTimeDesc(userId, pageable);

        List<BookingResponse> content = bookings.getContent().stream()
            .map(bookingMapper::toResponse)
            .collect(Collectors.toList());

        return PagedResponse.<BookingResponse>builder()
            .content(content)
            .pageNumber(bookings.getNumber())
            .pageSize(bookings.getSize())
            .totalElements(bookings.getTotalElements())
            .totalPages(bookings.getTotalPages())
            .last(bookings.isLast())
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredLocks() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(10);
        int released = showSeatRepository.releaseExpiredLocks(expiryTime);
        if (released > 0) {
            log.info("Released {} expired seat locks", released);
        }
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    public BookingServiceImpl(ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository, UserRepository userRepository, BookingMapper bookingMapper) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

}
