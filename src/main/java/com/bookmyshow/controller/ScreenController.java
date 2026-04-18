package com.bookmyshow.controller;

import com.bookmyshow.dto.request.ScreenRequest;
import com.bookmyshow.dto.request.SeatBulkCreateRequest;
import com.bookmyshow.dto.response.ApiResponse;
import com.bookmyshow.dto.response.ScreenResponse;
import com.bookmyshow.service.ScreenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @RestController
@RequestMapping("/api/screens") @Tag(name = "Screens")
public class ScreenController {
    private final ScreenService screenService;

    @GetMapping("/theatre/{theatreId}")
    @Operation(summary = "Get screens by theatre")
    public ResponseEntity<ApiResponse<List<ScreenResponse>>> getScreensByTheatre(@PathVariable Long theatreId) {
        log.debug("Fetching screens for theatre id: {}", theatreId);
        return ResponseEntity.ok(ApiResponse.success(screenService.getScreensByTheatre(theatreId)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get screen by ID")
    public ResponseEntity<ApiResponse<ScreenResponse>> getScreenById(@PathVariable Long id) {
        log.debug("Fetching screen with id: {}", id);
        return ResponseEntity.ok(ApiResponse.success(screenService.getScreenById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create screen (Admin)")
    public ResponseEntity<ApiResponse<ScreenResponse>> createScreen(@Valid @RequestBody ScreenRequest request) {
        log.debug("Creating screen: {}", request.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Screen created", screenService.createScreen(request)));
    }

    @PostMapping("/seats/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Bulk create seats for screen (Admin)")
    public ResponseEntity<ApiResponse<Void>> bulkCreateSeats(@Valid @RequestBody SeatBulkCreateRequest request) {
        log.debug("Creating bulk seats for screen id: {}", request.getScreenId());
        screenService.addSeatsToScreen(request.getScreenId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Seats created", null));
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ScreenController.class);

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

}
