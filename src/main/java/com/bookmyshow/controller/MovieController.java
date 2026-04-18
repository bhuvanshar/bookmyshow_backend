package com.bookmyshow.controller;

import com.bookmyshow.dto.request.MovieRequest;
import com.bookmyshow.dto.response.ApiResponse;
import com.bookmyshow.dto.response.MovieResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.service.MovieService;
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
@RequestMapping("/api/movies") @Tag(name = "Movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all movies (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<MovieResponse>>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.debug("Fetching movies - page: {}, size: {}", page, size);
        return ResponseEntity.ok(ApiResponse.success(movieService.getAllMovies(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(@PathVariable Long id) {
        log.debug("Fetching movie with id: {}", id);
        return ResponseEntity.ok(ApiResponse.success(movieService.getMovieById(id)));
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get movies by genre")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getMoviesByGenre(@PathVariable String genre) {
        log.debug("Fetching movies by genre: {}", genre);
        return ResponseEntity.ok(ApiResponse.success(movieService.getMoviesByGenre(genre)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies by title")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> searchMovies(@RequestParam String keyword) {
        log.debug("Searching movies with keyword: {}", keyword);
        return ResponseEntity.ok(ApiResponse.success(movieService.searchMovies(keyword)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create movie (Admin)")
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(@Valid @RequestBody MovieRequest request) {
        log.debug("Creating movie: {}", request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Movie created", movieService.createMovie(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update movie (Admin)")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequest request) {
        log.debug("Updating movie with id: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Movie updated", movieService.updateMovie(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete movie (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        log.debug("Deleting movie with id: {}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.ok(ApiResponse.success("Movie deleted", null));
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

}
