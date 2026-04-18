package com.bookmyshow.dto.response;

import java.math.BigDecimal;
import java.time.*;
import java.util.List; public class BookingResponse {
    private Long id;

    private String bookingReference;

    private String movieTitle;

    private String theatreName;

    private String screenName;

    private LocalDate showDate;

    private LocalTime startTime;

    private List<BookedSeatInfo> seats;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime bookingTime;

    private String paymentStatus;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getBookingReference() { return this.bookingReference; }
    public String getMovieTitle() { return this.movieTitle; }
    public String getTheatreName() { return this.theatreName; }
    public String getScreenName() { return this.screenName; }
    public LocalDate getShowDate() { return this.showDate; }
    public LocalTime getStartTime() { return this.startTime; }
    public List<BookedSeatInfo> getSeats() { return this.seats; }
    public BigDecimal getTotalAmount() { return this.totalAmount; }
    public String getStatus() { return this.status; }
    public LocalDateTime getBookingTime() { return this.bookingTime; }
    public String getPaymentStatus() { return this.paymentStatus; }

    public void setId(Long id) { this.id = id; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }
    public void setScreenName(String screenName) { this.screenName = screenName; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setSeats(List<BookedSeatInfo> seats) { this.seats = seats; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public BookingResponse() {}

    public BookingResponse(Long id, String bookingReference, String movieTitle, String theatreName, String screenName, LocalDate showDate, LocalTime startTime, List<BookedSeatInfo> seats, BigDecimal totalAmount, String status, LocalDateTime bookingTime, String paymentStatus) {
        this.id = id;
        this.bookingReference = bookingReference;
        this.movieTitle = movieTitle;
        this.theatreName = theatreName;
        this.screenName = screenName;
        this.showDate = showDate;
        this.startTime = startTime;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookingTime = bookingTime;
        this.paymentStatus = paymentStatus;
    }

    public static  BookingResponseBuilder builder() { return new BookingResponseBuilder(); }

    public static class BookingResponseBuilder {
        private Long id;
        private String bookingReference;
        private String movieTitle;
        private String theatreName;
        private String screenName;
        private LocalDate showDate;
        private LocalTime startTime;
        private List<BookedSeatInfo> seats;
        private BigDecimal totalAmount;
        private String status;
        private LocalDateTime bookingTime;
        private String paymentStatus;

        public BookingResponseBuilder id(Long id) { this.id = id; return this; }
        public BookingResponseBuilder bookingReference(String bookingReference) { this.bookingReference = bookingReference; return this; }
        public BookingResponseBuilder movieTitle(String movieTitle) { this.movieTitle = movieTitle; return this; }
        public BookingResponseBuilder theatreName(String theatreName) { this.theatreName = theatreName; return this; }
        public BookingResponseBuilder screenName(String screenName) { this.screenName = screenName; return this; }
        public BookingResponseBuilder showDate(LocalDate showDate) { this.showDate = showDate; return this; }
        public BookingResponseBuilder startTime(LocalTime startTime) { this.startTime = startTime; return this; }
        public BookingResponseBuilder seats(List<BookedSeatInfo> seats) { this.seats = seats; return this; }
        public BookingResponseBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public BookingResponseBuilder status(String status) { this.status = status; return this; }
        public BookingResponseBuilder bookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; return this; }
        public BookingResponseBuilder paymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; return this; }

        public BookingResponse build() { return new BookingResponse(id, bookingReference, movieTitle, theatreName, screenName, showDate, startTime, seats, totalAmount, status, bookingTime, paymentStatus); }
    }

}
