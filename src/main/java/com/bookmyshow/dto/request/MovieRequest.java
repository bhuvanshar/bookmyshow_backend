package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate; public class MovieRequest {
    @NotBlank
    private String title;

    private String description;

    private String genre;

    private String language;

    @NotNull
    @Positive
    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String posterUrl;

    private Double rating;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getGenre() { return this.genre; }
    public String getLanguage() { return this.language; }
    public Integer getDurationMinutes() { return this.durationMinutes; }
    public LocalDate getReleaseDate() { return this.releaseDate; }
    public String getPosterUrl() { return this.posterUrl; }
    public Double getRating() { return this.rating; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setLanguage(String language) { this.language = language; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public void setRating(Double rating) { this.rating = rating; }

    public MovieRequest() {}

    public MovieRequest(String title, String description, String genre, String language, Integer durationMinutes, LocalDate releaseDate, String posterUrl, Double rating) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.language = language;
        this.durationMinutes = durationMinutes;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    public static  MovieRequestBuilder builder() { return new MovieRequestBuilder(); }

    public static class MovieRequestBuilder {
        private String title;
        private String description;
        private String genre;
        private String language;
        private Integer durationMinutes;
        private LocalDate releaseDate;
        private String posterUrl;
        private Double rating;

        public MovieRequestBuilder title(String title) { this.title = title; return this; }
        public MovieRequestBuilder description(String description) { this.description = description; return this; }
        public MovieRequestBuilder genre(String genre) { this.genre = genre; return this; }
        public MovieRequestBuilder language(String language) { this.language = language; return this; }
        public MovieRequestBuilder durationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; return this; }
        public MovieRequestBuilder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public MovieRequestBuilder posterUrl(String posterUrl) { this.posterUrl = posterUrl; return this; }
        public MovieRequestBuilder rating(Double rating) { this.rating = rating; return this; }

        public MovieRequest build() { return new MovieRequest(title, description, genre, language, durationMinutes, releaseDate, posterUrl, rating); }
    }

}
