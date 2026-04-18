package com.bookmyshow.repository;

import com.bookmyshow.entity.Booking;
import com.bookmyshow.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByUserIdOrderByBookingTimeDesc(Long userId, Pageable pageable);
    Optional<Booking> findByBookingReference(String bookingReference);
    Optional<Booking> findByIdAndUserId(Long id, Long userId);
    long countByStatus(BookingStatus status);

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Booking b WHERE b.status = 'CONFIRMED'")
    BigDecimal getTotalRevenue();
}
