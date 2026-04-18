package com.bookmyshow.service;

import com.bookmyshow.dto.request.BookingRequest;
import com.bookmyshow.dto.request.PaymentRequest;
import com.bookmyshow.dto.response.BookingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class BookingFacade {
    private final BookingService bookingService;
    private final PaymentService paymentService;

    @Transactional
    public BookingResponse createBookingWithPayment(BookingRequest bookingRequest, PaymentRequest paymentRequest, Long userId) {
        log.info("Creating booking with payment for user {}", userId);
        BookingResponse booking = bookingService.initiateBooking(bookingRequest, userId);
        paymentRequest.setBookingId(booking.getId());
        paymentService.processPayment(paymentRequest, userId);
        return bookingService.getBookingById(booking.getId(), userId);
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(BookingFacade.class);

    public BookingFacade(BookingService bookingService, PaymentService paymentService) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

}
