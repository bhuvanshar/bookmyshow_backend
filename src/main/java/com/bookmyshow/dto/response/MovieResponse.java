package com.bookmyshow.dto.response;

import java.time.LocalDate; public class MovieResponse {
    private Long id;

    private String title;

    private String description;

    private String genre;

    private String language;

    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String posterUrl;

    private Double rating;

    private Boolean isActive;

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

    public MovieResponse() {}

    public MovieResponse(Long id, String title, String description, String genre, String language, Integer durationMinutes, LocalDate releaseDate, String posterUrl, Double rating, Boolean isActive) {
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
    }

    public static  MovieResponseBuilder builder() { return new MovieResponseBuilder(); }

    public static class MovieResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private String genre;
        private String language;
        private Integer durationMinutes;
        private LocalDate releaseDate;
        private String posterUrl;
        private Double rating;
        private Boolean isActive;

        public MovieResponseBuilder id(Long id) { this.id = id; return this; }
        public MovieResponseBuilder title(String title) { this.title = title; return this; }
        public MovieResponseBuilder description(String description) { this.description = description; return this; }
        public MovieResponseBuilder genre(String genre) { this.genre = genre; return this; }
        public MovieResponseBuilder language(String language) { this.language = language; return this; }
        public MovieResponseBuilder durationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; return this; }
        public MovieResponseBuilder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public MovieResponseBuilder posterUrl(String posterUrl) { this.posterUrl = posterUrl; return this; }
        public MovieResponseBuilder rating(Double rating) { this.rating = rating; return this; }
        public MovieResponseBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }

        public MovieResponse build() { return new MovieResponse(id, title, description, genre, language, durationMinutes, releaseDate, posterUrl, rating, isActive); }
    }

}
