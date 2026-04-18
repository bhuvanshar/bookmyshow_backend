package com.bookmyshow.controller;

import com.bookmyshow.dto.request.ShowRequest;
import com.bookmyshow.dto.response.ApiResponse;
import com.bookmyshow.dto.response.ShowResponse;
import com.bookmyshow.dto.response.ShowSeatResponse;
import com.bookmyshow.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @RestController
@RequestMapping("/api/shows") @Tag(name = "Shows")
public class ShowController {
    private final ShowService showService;

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get shows for a movie")
    public ResponseEntity<ApiResponse<List<ShowResponse>>> getShowsByMovie(@PathVariable Long movieId) {
        log.debug("Fetching shows for movie id: {}", movieId);
        return ResponseEntity.ok(ApiResponse.success(showService.getShowsByMovie(movieId)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get show by ID")
    public ResponseEntity<ApiResponse<ShowResponse>> getShowById(@PathVariable Long id) {
        log.debug("Fetching show with id: {}", id);
        return ResponseEntity.ok(ApiResponse.success(showService.getShowById(id)));
    }

    @GetMapping("/movie/{movieId}/city/{city}")
    @Operation(summary = "Get shows by movie and city")
    public ResponseEntity<ApiResponse<List<ShowResponse>>> getShowsByMovieAndCity(
            @PathVariable Long movieId,
            @PathVariable String city) {
        log.debug("Fetching shows for movie id: {} in city: {}", movieId, city);
        return ResponseEntity.ok(ApiResponse.success(showService.getShowsByMovieAndCity(movieId, city)));
    }

    @GetMapping("/{showId}/seats")
    @Operation(summary = "Get seat layout for a show")
    public ResponseEntity<ApiResponse<List<ShowSeatResponse>>> getSeatLayout(@PathVariable Long showId) {
        log.debug("Fetching seat layout for show id: {}", showId);
        return ResponseEntity.ok(ApiResponse.success(showService.getShowSeats(showId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create show (Admin)")
    public ResponseEntity<ApiResponse<ShowResponse>> createShow(@Valid @RequestBody ShowRequest request) {
        log.debug("Creating show for movie id: {} on screen id: {}", request.getMovieId(), request.getScreenId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Show created", showService.createShow(request)));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cancel show (Admin)")
    public ResponseEntity<ApiResponse<Void>> cancelShow(@PathVariable Long id) {
        log.debug("Cancelling show with id: {}", id);
        showService.cancelShow(id);
        return ResponseEntity.ok(ApiResponse.success("Show cancelled", null));
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ShowController.class);

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

}
