package com.bookmyshow.entity;

import com.bookmyshow.enums.BookingStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "bookings", indexes = {
    @Index(name = "idx_booking_user", columnList = "user_id"),
    @Index(name = "idx_booking_ref", columnList = "bookingReference")
}) public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Column(nullable = false, unique = true)
    private String bookingReference;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); bookingTime = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public User getUser() { return this.user; }
    public Show getShow() { return this.show; }
    public String getBookingReference() { return this.bookingReference; }
    public LocalDateTime getBookingTime() { return this.bookingTime; }
    public BigDecimal getTotalAmount() { return this.totalAmount; }
    public BookingStatus getStatus() { return this.status; }
    public List<BookingSeat> getBookingSeats() { return this.bookingSeats; }
    public Payment getPayment() { return this.payment; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setShow(Show show) { this.show = show; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public void setBookingSeats(List<BookingSeat> bookingSeats) { this.bookingSeats = bookingSeats; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Booking() {}

    public Booking(Long id, User user, Show show, String bookingReference, LocalDateTime bookingTime, BigDecimal totalAmount, BookingStatus status, List<BookingSeat> bookingSeats, Payment payment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.bookingReference = bookingReference;
        this.bookingTime = bookingTime;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookingSeats = bookingSeats;
        this.payment = payment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static  BookingBuilder builder() { return new BookingBuilder(); }

    public static class BookingBuilder {
        private Long id;
        private User user;
        private Show show;
        private String bookingReference;
        private LocalDateTime bookingTime;
        private BigDecimal totalAmount;
        private BookingStatus status = BookingStatus.PENDING;
        private List<BookingSeat> bookingSeats = new ArrayList<>();
        private Payment payment;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public BookingBuilder id(Long id) { this.id = id; return this; }
        public BookingBuilder user(User user) { this.user = user; return this; }
        public BookingBuilder show(Show show) { this.show = show; return this; }
        public BookingBuilder bookingReference(String bookingReference) { this.bookingReference = bookingReference; return this; }
        public BookingBuilder bookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; return this; }
        public BookingBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public BookingBuilder status(BookingStatus status) { this.status = status; return this; }
        public BookingBuilder bookingSeats(List<BookingSeat> bookingSeats) { this.bookingSeats = bookingSeats; return this; }
        public BookingBuilder payment(Payment payment) { this.payment = payment; return this; }
        public BookingBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public BookingBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Booking build() { return new Booking(id, user, show, bookingReference, bookingTime, totalAmount, status, bookingSeats, payment, createdAt, updatedAt); }
    }

}
