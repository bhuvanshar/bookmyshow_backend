package com.bookmyshow.controller;

import com.bookmyshow.dto.request.BookingRequest;
import com.bookmyshow.dto.request.CompleteBookingRequest;
import com.bookmyshow.dto.request.PaymentRequest;
import com.bookmyshow.dto.response.ApiResponse;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.security.CustomUserDetails;
import com.bookmyshow.service.BookingService;
import com.bookmyshow.service.BookingFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @RestController
@RequestMapping("/api/bookings") @Tag(name = "Bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingFacade bookingFacade;

    @PostMapping
    @Operation(summary = "Initiate booking (lock seats)")
    public ResponseEntity<ApiResponse<BookingResponse>> initiateBooking(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.debug("Booking request from user: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking initiated", bookingService.initiateBooking(request, user.getId())));
    }

    @PostMapping("/complete")
    @Operation(summary = "Create booking and process payment in one step")
    public ResponseEntity<ApiResponse<BookingResponse>> completeBooking(
            @Valid @RequestBody CompleteBookingRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.debug("Complete booking request from user: {}", user.getEmail());
        BookingRequest bookingReq = BookingRequest.builder()
                .showId(request.getShowId())
                .showSeatIds(request.getShowSeatIds())
                .build();
        PaymentRequest paymentReq = PaymentRequest.builder()
                .paymentMethod(request.getPaymentMethod())
                .simulateFailure(request.getSimulateFailure())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking completed",
                        bookingFacade.createBookingWithPayment(bookingReq, paymentReq, user.getId())));
    }

    @GetMapping("/history")
    @Operation(summary = "Get user booking history")
    public ResponseEntity<ApiResponse<PagedResponse<BookingResponse>>> getUserBookings(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.debug("Fetching booking history for user: {}", user.getEmail());
        return ResponseEntity.ok(ApiResponse.success(bookingService.getUserBookings(user.getId(), page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.debug("Fetching booking with id: {} for user: {}", id, user.getEmail());
        return ResponseEntity.ok(ApiResponse.success(bookingService.getBookingById(id, user.getId())));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel booking")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.debug("Cancelling booking with id: {} for user: {}", id, user.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled", bookingService.cancelBooking(id, user.getId())));
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingService bookingService, BookingFacade bookingFacade) {
        this.bookingService = bookingService;
        this.bookingFacade = bookingFacade;
    }

}
