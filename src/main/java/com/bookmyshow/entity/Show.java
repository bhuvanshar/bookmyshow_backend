package com.bookmyshow.entity;

import com.bookmyshow.enums.ShowStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "shows", indexes = {
    @Index(name = "idx_show_movie", columnList = "movie_id"),
    @Index(name = "idx_show_date", columnList = "showDate")
}) public class Show {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowStatus status = ShowStatus.SCHEDULED;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<ShowSeat> showSeats = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public Movie getMovie() { return this.movie; }
    public Screen getScreen() { return this.screen; }
    public LocalDate getShowDate() { return this.showDate; }
    public LocalTime getStartTime() { return this.startTime; }
    public LocalTime getEndTime() { return this.endTime; }
    public BigDecimal getBasePrice() { return this.basePrice; }
    public ShowStatus getStatus() { return this.status; }
    public List<ShowSeat> getShowSeats() { return this.showSeats; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setMovie(Movie movie) { this.movie = movie; }
    public void setScreen(Screen screen) { this.screen = screen; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    public void setStatus(ShowStatus status) { this.status = status; }
    public void setShowSeats(List<ShowSeat> showSeats) { this.showSeats = showSeats; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Show() {}

    public Show(Long id, Movie movie, Screen screen, LocalDate showDate, LocalTime startTime, LocalTime endTime, BigDecimal basePrice, ShowStatus status, List<ShowSeat> showSeats, LocalDateTime createdAt) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.status = status;
        this.showSeats = showSeats;
        this.createdAt = createdAt;
    }

    public static  ShowBuilder builder() { return new ShowBuilder(); }

    public static class ShowBuilder {
        private Long id;
        private Movie movie;
        private Screen screen;
        private LocalDate showDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private BigDecimal basePrice;
        private ShowStatus status = ShowStatus.SCHEDULED;
        private List<ShowSeat> showSeats = new ArrayList<>();
        private LocalDateTime createdAt;

        public ShowBuilder id(Long id) { this.id = id; return this; }
        public ShowBuilder movie(Movie movie) { this.movie = movie; return this; }
        public ShowBuilder screen(Screen screen) { this.screen = screen; return this; }
        public ShowBuilder showDate(LocalDate showDate) { this.showDate = showDate; return this; }
        public ShowBuilder startTime(LocalTime startTime) { this.startTime = startTime; return this; }
        public ShowBuilder endTime(LocalTime endTime) { this.endTime = endTime; return this; }
        public ShowBuilder basePrice(BigDecimal basePrice) { this.basePrice = basePrice; return this; }
        public ShowBuilder status(ShowStatus status) { this.status = status; return this; }
        public ShowBuilder showSeats(List<ShowSeat> showSeats) { this.showSeats = showSeats; return this; }
        public ShowBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Show build() { return new Show(id, movie, screen, showDate, startTime, endTime, basePrice, status, showSeats, createdAt); }
    }

}
