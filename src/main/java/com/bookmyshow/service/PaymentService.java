package com.bookmyshow.service;

import com.bookmyshow.dto.request.PaymentRequest;
import com.bookmyshow.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request, Long userId);
    PaymentResponse getPaymentByBooking(Long bookingId);
}
