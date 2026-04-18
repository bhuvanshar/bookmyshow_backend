package com.bookmyshow.service;

import com.bookmyshow.dto.response.AdminDashboardResponse;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.dto.response.PagedResponse;

public interface AdminService {
    AdminDashboardResponse getDashboard();
    PagedResponse<BookingResponse> getAllBookings(int page, int size);
}
