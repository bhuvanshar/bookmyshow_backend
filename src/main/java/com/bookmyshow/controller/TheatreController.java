package com.bookmyshow.controller;

import com.bookmyshow.dto.request.TheatreRequest;
import com.bookmyshow.dto.response.ApiResponse;
import com.bookmyshow.dto.response.TheatreResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.service.TheatreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @RestController
@RequestMapping("/api/theatres") @Tag(name = "Theatres")
public class TheatreController {
    private final TheatreService theatreService;

    @GetMapping
    @Operation(summary = "Get all theatres (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<TheatreResponse>>> getAllTheatres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.debug("Fetching theatres - page: {}, size: {}", page, size);
        return ResponseEntity.ok(ApiResponse.success(theatreService.getAllTheatres(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get theatre by ID")
    public ResponseEntity<ApiResponse<TheatreResponse>> getTheatreById(@PathVariable Long id) {
        log.debug("Fetching theatre with id: {}", id);
        return ResponseEntity.ok(ApiResponse.success(theatreService.getTheatreById(id)));
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Get theatres by city")
    public ResponseEntity<ApiResponse<List<TheatreResponse>>> getTheatresByCity(@PathVariable String city) {
        log.debug("Fetching theatres in city: {}", city);
        return ResponseEntity.ok(ApiResponse.success(theatreService.getTheatresByCity(city)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create theatre (Admin)")
    public ResponseEntity<ApiResponse<TheatreResponse>> createTheatre(@Valid @RequestBody TheatreRequest request) {
        log.debug("Creating theatre: {}", request.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Theatre created", theatreService.createTheatre(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update theatre (Admin)")
    public ResponseEntity<ApiResponse<TheatreResponse>> updateTheatre(
            @PathVariable Long id,
            @Valid @RequestBody TheatreRequest request) {
        log.debug("Updating theatre with id: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Theatre updated", theatreService.updateTheatre(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete theatre (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteTheatre(@PathVariable Long id) {
        log.debug("Deleting theatre with id: {}", id);
        theatreService.deleteTheatre(id);
        return ResponseEntity.ok(ApiResponse.success("Theatre deleted", null));
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(TheatreController.class);

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

}
