package com.bookmyshow.service.impl;

import com.bookmyshow.dto.response.AdminDashboardResponse;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.entity.Booking;
import com.bookmyshow.mapper.BookingMapper;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.TheatreRepository;
import com.bookmyshow.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class AdminServiceImpl implements AdminService {
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {
        long totalMovies = movieRepository.count();
        long totalTheatres = theatreRepository.count();
        long totalBookings = bookingRepository.count();
        BigDecimal totalRevenue = bookingRepository.getTotalRevenue();

        return AdminDashboardResponse.builder()
            .totalMovies(totalMovies)
            .totalTheatres(totalTheatres)
            .totalBookings(totalBookings)
            .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BookingResponse> getAllBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "bookingTime"));
        Page<Booking> bookings = bookingRepository.findAll(pageable);

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

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(MovieRepository movieRepository, TheatreRepository theatreRepository, BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

}
