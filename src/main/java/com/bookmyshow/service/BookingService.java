package com.bookmyshow.service;

import com.bookmyshow.dto.request.BookingRequest;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.dto.response.PagedResponse;

public interface BookingService {
    BookingResponse initiateBooking(BookingRequest request, Long userId);
    BookingResponse confirmBooking(Long bookingId, Long userId);
    BookingResponse cancelBooking(Long bookingId, Long userId);
    PagedResponse<BookingResponse> getUserBookings(Long userId, int page, int size);
    BookingResponse getBookingById(Long bookingId, Long userId);
    void releaseExpiredLocks();
}
