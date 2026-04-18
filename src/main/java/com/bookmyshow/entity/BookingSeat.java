package com.bookmyshow.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "booking_seats") public class BookingSeat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "show_seat_id", nullable = false)
    private ShowSeat showSeat;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtBooking;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public Booking getBooking() { return this.booking; }
    public ShowSeat getShowSeat() { return this.showSeat; }
    public BigDecimal getPriceAtBooking() { return this.priceAtBooking; }

    public void setId(Long id) { this.id = id; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public void setShowSeat(ShowSeat showSeat) { this.showSeat = showSeat; }
    public void setPriceAtBooking(BigDecimal priceAtBooking) { this.priceAtBooking = priceAtBooking; }

    public BookingSeat() {}

    public BookingSeat(Long id, Booking booking, ShowSeat showSeat, BigDecimal priceAtBooking) {
        this.id = id;
        this.booking = booking;
        this.showSeat = showSeat;
        this.priceAtBooking = priceAtBooking;
    }

    public static  BookingSeatBuilder builder() { return new BookingSeatBuilder(); }

    public static class BookingSeatBuilder {
        private Long id;
        private Booking booking;
        private ShowSeat showSeat;
        private BigDecimal priceAtBooking;

        public BookingSeatBuilder id(Long id) { this.id = id; return this; }
        public BookingSeatBuilder booking(Booking booking) { this.booking = booking; return this; }
        public BookingSeatBuilder showSeat(ShowSeat showSeat) { this.showSeat = showSeat; return this; }
        public BookingSeatBuilder priceAtBooking(BigDecimal priceAtBooking) { this.priceAtBooking = priceAtBooking; return this; }

        public BookingSeat build() { return new BookingSeat(id, booking, showSeat, priceAtBooking); }
    }

}
