package com.bookmyshow.controller;

import com.bookmyshow.dto.response.ApiResponse;
import com.bookmyshow.dto.response.AdminDashboardResponse;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @RestController
@RequestMapping("/api/admin") @PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard stats")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getDashboard() {
        log.debug("Fetching admin dashboard stats");
        return ResponseEntity.ok(ApiResponse.success(adminService.getDashboard()));
    }

    @GetMapping("/bookings")
    @Operation(summary = "Get all bookings (Admin)")
    public ResponseEntity<ApiResponse<PagedResponse<BookingResponse>>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.debug("Fetching all bookings - page: {}, size: {}", page, size);
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllBookings(page, size)));
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

}
