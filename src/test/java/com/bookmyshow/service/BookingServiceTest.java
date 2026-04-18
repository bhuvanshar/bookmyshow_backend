package com.bookmyshow.service;

import com.bookmyshow.dto.request.BookingRequest;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.entity.*;
import com.bookmyshow.enums.*;
import com.bookmyshow.exception.*;
import com.bookmyshow.mapper.BookingMapper;
import com.bookmyshow.repository.*;
import com.bookmyshow.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock private ShowRepository showRepository;
    @Mock private ShowSeatRepository showSeatRepository;
    @Mock private BookingRepository bookingRepository;
    @Mock private BookingSeatRepository bookingSeatRepository;
    @Mock private UserRepository userRepository;

    private final BookingMapper bookingMapper = new BookingMapper();

    private BookingServiceImpl bookingService;

    private Show testShow;
    private User testUser;
    private ShowSeat testSeat1;
    private ShowSeat testSeat2;
    private Seat seat1;
    private Seat seat2;

    @BeforeEach
    void setUp() {
        bookingService = new BookingServiceImpl(showRepository, showSeatRepository, bookingRepository, bookingSeatRepository, userRepository, bookingMapper);

        testUser = User.builder().id(1L).fullName("John Doe").email("john@test.com").build();

        Movie movie = Movie.builder().id(1L).title("Test Movie").durationMinutes(120).build();

        Theatre theatre = Theatre.builder().id(1L).name("PVR").city("Mumbai").build();
        Screen screen = Screen.builder().id(1L).name("Screen 1").theatre(theatre).totalSeats(15).build();

        testShow = Show.builder()
                .id(1L).movie(movie).screen(screen)
                .showDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .basePrice(BigDecimal.valueOf(200))
                .status(ShowStatus.SCHEDULED)
                .showSeats(new ArrayList<>())
                .build();

        seat1 = Seat.builder().id(1L).screen(screen).rowName("A").seatNumber(1).seatType(SeatType.REGULAR).build();
        seat2 = Seat.builder().id(2L).screen(screen).rowName("A").seatNumber(2).seatType(SeatType.REGULAR).build();

        testSeat1 = ShowSeat.builder()
                .id(1L).show(testShow).seat(seat1)
                .status(SeatStatus.AVAILABLE).price(BigDecimal.valueOf(200)).version(0)
                .build();
        testSeat2 = ShowSeat.builder()
                .id(2L).show(testShow).seat(seat2)
                .status(SeatStatus.AVAILABLE).price(BigDecimal.valueOf(200)).version(0)
                .build();
    }

    @Test
    @DisplayName("Should initiate booking successfully for available seats")
    void testInitiateBookingSuccess() {
        BookingRequest request = BookingRequest.builder()
                .showId(1L).showSeatIds(List.of(1L, 2L)).build();

        when(showRepository.findById(1L)).thenReturn(Optional.of(testShow));
        when(showSeatRepository.findAllByIdIn(List.of(1L, 2L))).thenReturn(List.of(testSeat1, testSeat2));
        when(showSeatRepository.saveAll(anyList())).thenReturn(List.of(testSeat1, testSeat2));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });
        when(bookingSeatRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        BookingResponse response = bookingService.initiateBooking(request, 1L);

        assertNotNull(response);
        assertNotNull(response.getBookingReference());
        assertEquals("PENDING", response.getStatus());
        verify(showSeatRepository).saveAll(anyList());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should throw exception when seats are already booked")
    void testInitiateBookingSeatAlreadyBooked() {
        testSeat1.setStatus(SeatStatus.BOOKED);
        BookingRequest request = BookingRequest.builder()
                .showId(1L).showSeatIds(List.of(1L)).build();

        when(showRepository.findById(1L)).thenReturn(Optional.of(testShow));
        when(showSeatRepository.findAllByIdIn(List.of(1L))).thenReturn(List.of(testSeat1));

        assertThrows(SeatAlreadyBookedException.class,
                () -> bookingService.initiateBooking(request, 1L));
    }

    @Test
    @DisplayName("Should throw ConcurrencyException on optimistic lock failure")
    void testConcurrentBookingConflict() {
        BookingRequest request = BookingRequest.builder()
                .showId(1L).showSeatIds(List.of(1L)).build();

        when(showRepository.findById(1L)).thenReturn(Optional.of(testShow));
        when(showSeatRepository.findAllByIdIn(List.of(1L))).thenReturn(List.of(testSeat1));
        when(showSeatRepository.saveAll(anyList()))
                .thenThrow(new ObjectOptimisticLockingFailureException(ShowSeat.class.getName(), 1L));

        assertThrows(ConcurrencyException.class,
                () -> bookingService.initiateBooking(request, 1L));
    }

    @Test
    @DisplayName("Should throw when show is not SCHEDULED")
    void testBookingForCancelledShow() {
        testShow.setStatus(ShowStatus.CANCELLED);
        BookingRequest request = BookingRequest.builder()
                .showId(1L).showSeatIds(List.of(1L)).build();

        when(showRepository.findById(1L)).thenReturn(Optional.of(testShow));

        assertThrows(BookingException.class,
                () -> bookingService.initiateBooking(request, 1L));
    }

    @Test
    @DisplayName("Should confirm a PENDING booking")
    void testConfirmBooking() {
        BookingSeat bs = BookingSeat.builder()
                .id(1L).showSeat(testSeat1).priceAtBooking(BigDecimal.valueOf(200)).build();
        testSeat1.setStatus(SeatStatus.LOCKED);

        Booking booking = Booking.builder()
                .id(1L).user(testUser).show(testShow)
                .status(BookingStatus.PENDING)
                .bookingReference("ABC12345")
                .totalAmount(BigDecimal.valueOf(200))
                .bookingTime(LocalDateTime.now())
                .bookingSeats(new ArrayList<>(List.of(bs)))
                .build();
        bs.setBooking(booking);

        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);

        BookingResponse response = bookingService.confirmBooking(1L, 1L);

        assertEquals("CONFIRMED", response.getStatus());
        assertEquals(SeatStatus.BOOKED, testSeat1.getStatus());
    }

    @Test
    @DisplayName("Should cancel booking and release seats")
    void testCancelBooking() {
        testSeat1.setStatus(SeatStatus.BOOKED);
        testSeat1.setLockedBy(1L);
        testSeat1.setLockedAt(LocalDateTime.now());

        BookingSeat bs = BookingSeat.builder()
                .id(1L).showSeat(testSeat1).priceAtBooking(BigDecimal.valueOf(200)).build();

        Booking booking = Booking.builder()
                .id(1L).user(testUser).show(testShow)
                .status(BookingStatus.CONFIRMED)
                .bookingReference("ABC12345")
                .totalAmount(BigDecimal.valueOf(200))
                .bookingTime(LocalDateTime.now())
                .bookingSeats(new ArrayList<>(List.of(bs)))
                .build();
        bs.setBooking(booking);

        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);

        BookingResponse response = bookingService.cancelBooking(1L, 1L);

        assertEquals("CANCELLED", response.getStatus());
        assertEquals(SeatStatus.AVAILABLE, testSeat1.getStatus());
        assertNull(testSeat1.getLockedAt());
        assertNull(testSeat1.getLockedBy());
    }

    @Test
    @DisplayName("Should throw when cancelling already cancelled booking")
    void testCancelAlreadyCancelledBooking() {
        Booking booking = Booking.builder()
                .id(1L).user(testUser).show(testShow)
                .status(BookingStatus.CANCELLED)
                .bookingReference("ABC12345")
                .totalAmount(BigDecimal.valueOf(200))
                .bookingTime(LocalDateTime.now())
                .bookingSeats(new ArrayList<>())
                .build();

        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(booking));

        assertThrows(BookingException.class,
                () -> bookingService.cancelBooking(1L, 1L));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException for non-existent show")
    void testBookingNonExistentShow() {
        BookingRequest request = BookingRequest.builder()
                .showId(999L).showSeatIds(List.of(1L)).build();

        when(showRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.initiateBooking(request, 1L));
    }
}
