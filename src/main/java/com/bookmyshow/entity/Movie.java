package com.bookmyshow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "movies", indexes = {
    @Index(name = "idx_movie_genre", columnList = "genre"),
    @Index(name = "idx_movie_active", columnList = "isActive")
}) public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String genre;
    private String language;

    @Column(nullable = false)
    private Integer durationMinutes;

    private LocalDate releaseDate;
    private String posterUrl;
    private Double rating = 0.0;
    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "movie")
    private List<Show> shows = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getGenre() { return this.genre; }
    public String getLanguage() { return this.language; }
    public Integer getDurationMinutes() { return this.durationMinutes; }
    public LocalDate getReleaseDate() { return this.releaseDate; }
    public String getPosterUrl() { return this.posterUrl; }
    public Double getRating() { return this.rating; }
    public Boolean getIsActive() { return this.isActive; }
    public List<Show> getShows() { return this.shows; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setLanguage(String language) { this.language = language; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setShows(List<Show> shows) { this.shows = shows; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Movie() {}

    public Movie(Long id, String title, String description, String genre, String language, Integer durationMinutes, LocalDate releaseDate, String posterUrl, Double rating, Boolean isActive, List<Show> shows, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.language = language;
        this.durationMinutes = durationMinutes;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.isActive = isActive;
        this.shows = shows;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static  MovieBuilder builder() { return new MovieBuilder(); }

    public static class MovieBuilder {
        private Long id;
        private String title;
        private String description;
        private String genre;
        private String language;
        private Integer durationMinutes;
        private LocalDate releaseDate;
        private String posterUrl;
        private Double rating = 0.0;
        private Boolean isActive = true;
        private List<Show> shows = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MovieBuilder id(Long id) { this.id = id; return this; }
        public MovieBuilder title(String title) { this.title = title; return this; }
        public MovieBuilder description(String description) { this.description = description; return this; }
        public MovieBuilder genre(String genre) { this.genre = genre; return this; }
        public MovieBuilder language(String language) { this.language = language; return this; }
        public MovieBuilder durationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; return this; }
        public MovieBuilder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public MovieBuilder posterUrl(String posterUrl) { this.posterUrl = posterUrl; return this; }
        public MovieBuilder rating(Double rating) { this.rating = rating; return this; }
        public MovieBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public MovieBuilder shows(List<Show> shows) { this.shows = shows; return this; }
        public MovieBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MovieBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Movie build() { return new Movie(id, title, description, genre, language, durationMinutes, releaseDate, posterUrl, rating, isActive, shows, createdAt, updatedAt); }
    }

}
