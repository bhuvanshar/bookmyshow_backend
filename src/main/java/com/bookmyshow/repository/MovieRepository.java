package com.bookmyshow.repository;

import com.bookmyshow.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByIsActiveTrue();
    Page<Movie> findByIsActiveTrue(Pageable pageable);
    Optional<Movie> findByIdAndIsActiveTrue(Long id);
    List<Movie> findByGenreAndIsActiveTrue(String genre);

    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchByTitle(String keyword);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.shows s JOIN s.screen sc JOIN sc.theatre t WHERE t.city = :city AND m.isActive = true")
    List<Movie> findByCity(String city);
}
