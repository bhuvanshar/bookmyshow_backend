package com.bookmyshow.repository;

import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowId(Long showId);
    List<ShowSeat> findByShowIdAndStatus(Long showId, SeatStatus status);
    List<ShowSeat> findAllByIdIn(List<Long> ids);
    long countByShowIdAndStatus(Long showId, SeatStatus status);

    @Query("SELECT ss FROM ShowSeat ss WHERE ss.status = 'LOCKED' AND ss.lockedAt < :expiryTime")
    List<ShowSeat> findExpiredLockedSeats(LocalDateTime expiryTime);

    @Modifying
    @Query("UPDATE ShowSeat ss SET ss.status = 'AVAILABLE', ss.lockedAt = null, ss.lockedBy = null WHERE ss.status = 'LOCKED' AND ss.lockedAt < :expiryTime")
    int releaseExpiredLocks(LocalDateTime expiryTime);
}
