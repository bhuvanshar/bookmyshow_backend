package com.bookmyshow.service.impl;

import com.bookmyshow.dto.request.MovieRequest;
import com.bookmyshow.dto.response.MovieResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.entity.Movie;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.mapper.MovieMapper;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MovieResponse> getAllMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieRepository.findByIsActiveTrue(pageable);

        List<MovieResponse> content = movies.getContent().stream()
            .map(movieMapper::toResponse)
            .collect(Collectors.toList());

        return PagedResponse.<MovieResponse>builder()
            .content(content)
            .pageNumber(movies.getNumber())
            .pageSize(movies.getSize())
            .totalElements(movies.getTotalElements())
            .totalPages(movies.getTotalPages())
            .last(movies.isLast())
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovieById(Long movieId) {
        Movie movie = movieRepository.findByIdAndIsActiveTrue(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getMoviesByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenreAndIsActiveTrue(genre);
        return movies.stream()
            .map(movieMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> searchMovies(String keyword) {
        List<Movie> movies = movieRepository.searchByTitle(keyword);
        return movies.stream()
            .map(movieMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest request) {
        Movie movie = movieMapper.toEntity(request);
        movie.setIsActive(true);
        movie = movieRepository.save(movie);
        log.info("Movie created successfully: {}", movie.getId());
        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long movieId, MovieRequest request) {
        Movie movie = movieRepository.findByIdAndIsActiveTrue(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setRating(request.getRating());
        movie = movieRepository.save(movie);
        log.info("Movie updated successfully: {}", movieId);
        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findByIdAndIsActiveTrue(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));
        movie.setIsActive(false);
        movieRepository.save(movie);
        log.info("Movie soft deleted successfully: {}", movieId);
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

}
