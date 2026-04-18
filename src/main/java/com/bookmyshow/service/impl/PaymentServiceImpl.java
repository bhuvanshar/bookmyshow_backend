package com.bookmyshow.service.impl;

import com.bookmyshow.dto.request.PaymentRequest;
import com.bookmyshow.dto.response.PaymentResponse;
import com.bookmyshow.entity.Booking;
import com.bookmyshow.entity.Payment;
import com.bookmyshow.enums.BookingStatus;
import com.bookmyshow.enums.PaymentStatus;
import com.bookmyshow.exception.BookingException;
import com.bookmyshow.exception.PaymentFailedException;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.mapper.PaymentMapper;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.PaymentRepository;
import com.bookmyshow.service.BookingService;
import com.bookmyshow.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(request.getBookingId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", request.getBookingId()));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingException("Booking is not in PENDING status");
        }

        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();

        Payment payment = Payment.builder()
            .booking(booking)
            .amount(booking.getTotalAmount())
            .paymentMethod(request.getPaymentMethod())
            .transactionId(transactionId)
            .paymentTime(LocalDateTime.now())
            .build();

        if (Boolean.TRUE.equals(request.getSimulateFailure())) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            bookingService.cancelBooking(booking.getId(), userId);
            log.warn("Payment failed (simulated) for booking: {}", booking.getBookingReference());
            throw new PaymentFailedException("Payment failed. Booking has been cancelled.");
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        bookingService.confirmBooking(booking.getId(), userId);
        log.info("Payment successful: {} for booking: {}", transactionId, booking.getBookingReference());
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBooking(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", "bookingId", bookingId));
        return paymentMapper.toResponse(payment);
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository, BookingService bookingService, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.paymentMapper = paymentMapper;
    }

}
