package com.bookmyshow.repository;

import com.bookmyshow.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.bookmyshow.enums.ShowStatus;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieIdAndShowDateGreaterThanEqual(Long movieId, LocalDate date);
    List<Show> findByScreenIdAndShowDate(Long screenId, LocalDate date);
    List<Show> findByMovieIdAndStatus(Long movieId, ShowStatus status);

    @Query("SELECT s FROM Show s JOIN s.screen sc JOIN sc.theatre t WHERE s.movie.id = :movieId AND t.city = :city AND s.status = :status AND s.showDate >= CURRENT_DATE")
    List<Show> findByMovieIdAndScreenTheatreCityAndStatus(Long movieId, String city, ShowStatus status);

    @Query("SELECT s FROM Show s JOIN s.screen sc JOIN sc.theatre t WHERE s.movie.id = :movieId AND t.city = :city AND s.showDate >= CURRENT_DATE AND s.status = 'SCHEDULED'")
    List<Show> findByMovieIdAndCity(Long movieId, String city);
}
