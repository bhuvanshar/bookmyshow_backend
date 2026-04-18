package com.bookmyshow.service;

import com.bookmyshow.dto.request.MovieRequest;
import com.bookmyshow.dto.response.MovieResponse;
import com.bookmyshow.dto.response.PagedResponse;

import java.util.List;

public interface MovieService {
    PagedResponse<MovieResponse> getAllMovies(int page, int size);
    MovieResponse getMovieById(Long movieId);
    List<MovieResponse> getMoviesByGenre(String genre);
    List<MovieResponse> searchMovies(String keyword);
    MovieResponse createMovie(MovieRequest request);
    MovieResponse updateMovie(Long movieId, MovieRequest request);
    void deleteMovie(Long movieId);
}
