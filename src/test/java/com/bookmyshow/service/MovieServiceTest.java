package com.bookmyshow.service;

import com.bookmyshow.dto.request.MovieRequest;
import com.bookmyshow.dto.response.MovieResponse;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.entity.Movie;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.mapper.MovieMapper;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    private final MovieMapper movieMapper = new MovieMapper();

    private MovieServiceImpl movieService;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        movieService = new MovieServiceImpl(movieRepository, movieMapper);

        testMovie = Movie.builder()
                .id(1L)
                .title("Inception")
                .description("A mind-bending thriller")
                .genre("Sci-Fi")
                .language("English")
                .durationMinutes(148)
                .releaseDate(LocalDate.of(2010, 7, 16))
                .rating(8.8)
                .isActive(true)
                .shows(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should get all active movies with pagination")
    void testGetAllMovies() {
        Page<Movie> page = new PageImpl<>(List.of(testMovie), PageRequest.of(0, 10), 1);
        when(movieRepository.findByIsActiveTrue(any(Pageable.class))).thenReturn(page);

        PagedResponse<MovieResponse> response = movieService.getAllMovies(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("Inception", response.getContent().get(0).getTitle());
        assertEquals(1, response.getTotalElements());
        assertTrue(response.isLast());
    }

    @Test
    @DisplayName("Should get movie by ID")
    void testGetMovieById() {
        when(movieRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(testMovie));

        MovieResponse response = movieService.getMovieById(1L);

        assertNotNull(response);
        assertEquals("Inception", response.getTitle());
        assertEquals("Sci-Fi", response.getGenre());
        assertEquals(8.8, response.getRating());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException for non-existent movie")
    void testGetMovieByIdNotFound() {
        when(movieRepository.findByIdAndIsActiveTrue(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> movieService.getMovieById(999L));
    }

    @Test
    @DisplayName("Should create a new movie")
    void testCreateMovie() {
        MovieRequest request = MovieRequest.builder()
                .title("New Movie")
                .description("A great film")
                .genre("Action")
                .language("Hindi")
                .durationMinutes(150)
                .releaseDate(LocalDate.now())
                .rating(7.5)
                .build();

        when(movieRepository.save(any(Movie.class))).thenAnswer(inv -> {
            Movie m = inv.getArgument(0);
            m.setId(2L);
            return m;
        });

        MovieResponse response = movieService.createMovie(request);

        assertNotNull(response);
        assertEquals("New Movie", response.getTitle());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    @DisplayName("Should soft-delete a movie by setting isActive to false")
    void testDeleteMovie() {
        when(movieRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(testMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        movieService.deleteMovie(1L);

        assertFalse(testMovie.getIsActive());
        verify(movieRepository).save(testMovie);
    }

    @Test
    @DisplayName("Should search movies by title keyword")
    void testSearchMovies() {
        when(movieRepository.searchByTitle("inception")).thenReturn(List.of(testMovie));

        List<MovieResponse> results = movieService.searchMovies("inception");

        assertEquals(1, results.size());
        assertEquals("Inception", results.get(0).getTitle());
    }

    @Test
    @DisplayName("Should get movies by genre")
    void testGetMoviesByGenre() {
        when(movieRepository.findByGenreAndIsActiveTrue("Sci-Fi")).thenReturn(List.of(testMovie));

        List<MovieResponse> results = movieService.getMoviesByGenre("Sci-Fi");

        assertEquals(1, results.size());
        assertEquals("Sci-Fi", results.get(0).getGenre());
    }

    @Test
    @DisplayName("Should update an existing movie")
    void testUpdateMovie() {
        MovieRequest request = MovieRequest.builder()
                .title("Inception Updated")
                .description("Updated description")
                .genre("Sci-Fi")
                .language("English")
                .durationMinutes(150)
                .rating(9.0)
                .build();

        when(movieRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(testMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        MovieResponse response = movieService.updateMovie(1L, request);

        assertNotNull(response);
        assertEquals("Inception Updated", testMovie.getTitle());
        verify(movieRepository).save(testMovie);
    }
}
