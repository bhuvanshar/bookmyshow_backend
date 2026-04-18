package com.bookmyshow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "screens") public class Screen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @Column(nullable = false)
    private Integer totalSeats;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "screen")
    private List<Show> shows = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public Theatre getTheatre() { return this.theatre; }
    public Integer getTotalSeats() { return this.totalSeats; }
    public List<Seat> getSeats() { return this.seats; }
    public List<Show> getShows() { return this.shows; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTheatre(Theatre theatre) { this.theatre = theatre; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public void setShows(List<Show> shows) { this.shows = shows; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Screen() {}

    public Screen(Long id, String name, Theatre theatre, Integer totalSeats, List<Seat> seats, List<Show> shows, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.theatre = theatre;
        this.totalSeats = totalSeats;
        this.seats = seats;
        this.shows = shows;
        this.createdAt = createdAt;
    }

    public static  ScreenBuilder builder() { return new ScreenBuilder(); }

    public static class ScreenBuilder {
        private Long id;
        private String name;
        private Theatre theatre;
        private Integer totalSeats;
        private List<Seat> seats = new ArrayList<>();
        private List<Show> shows = new ArrayList<>();
        private LocalDateTime createdAt;

        public ScreenBuilder id(Long id) { this.id = id; return this; }
        public ScreenBuilder name(String name) { this.name = name; return this; }
        public ScreenBuilder theatre(Theatre theatre) { this.theatre = theatre; return this; }
        public ScreenBuilder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }
        public ScreenBuilder seats(List<Seat> seats) { this.seats = seats; return this; }
        public ScreenBuilder shows(List<Show> shows) { this.shows = shows; return this; }
        public ScreenBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Screen build() { return new Screen(id, name, theatre, totalSeats, seats, shows, createdAt); }
    }

}
