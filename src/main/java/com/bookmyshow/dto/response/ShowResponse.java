package com.bookmyshow.dto.response;

import java.math.BigDecimal;
import java.time.*; public class ShowResponse {
    private Long id;

    private Long movieId;

    private String movieTitle;

    private Long theatreId;

    private String theatreName;

    private Long screenId;

    private String screenName;

    private LocalDate showDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private BigDecimal basePrice;

    private String status;

    private int availableSeats;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public Long getMovieId() { return this.movieId; }
    public String getMovieTitle() { return this.movieTitle; }
    public Long getTheatreId() { return this.theatreId; }
    public String getTheatreName() { return this.theatreName; }
    public Long getScreenId() { return this.screenId; }
    public String getScreenName() { return this.screenName; }
    public LocalDate getShowDate() { return this.showDate; }
    public LocalTime getStartTime() { return this.startTime; }
    public LocalTime getEndTime() { return this.endTime; }
    public BigDecimal getBasePrice() { return this.basePrice; }
    public String getStatus() { return this.status; }
    public int getAvailableSeats() { return this.availableSeats; }

    public void setId(Long id) { this.id = id; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setTheatreId(Long theatreId) { this.theatreId = theatreId; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }
    public void setScreenId(Long screenId) { this.screenId = screenId; }
    public void setScreenName(String screenName) { this.screenName = screenName; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    public void setStatus(String status) { this.status = status; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public ShowResponse() {}

    public ShowResponse(Long id, Long movieId, String movieTitle, Long theatreId, String theatreName, Long screenId, String screenName, LocalDate showDate, LocalTime startTime, LocalTime endTime, BigDecimal basePrice, String status, int availableSeats) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.screenId = screenId;
        this.screenName = screenName;
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.status = status;
        this.availableSeats = availableSeats;
    }

    public static  ShowResponseBuilder builder() { return new ShowResponseBuilder(); }

    public static class ShowResponseBuilder {
        private Long id;
        private Long movieId;
        private String movieTitle;
        private Long theatreId;
        private String theatreName;
        private Long screenId;
        private String screenName;
        private LocalDate showDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private BigDecimal basePrice;
        private String status;
        private int availableSeats;

        public ShowResponseBuilder id(Long id) { this.id = id; return this; }
        public ShowResponseBuilder movieId(Long movieId) { this.movieId = movieId; return this; }
        public ShowResponseBuilder movieTitle(String movieTitle) { this.movieTitle = movieTitle; return this; }
        public ShowResponseBuilder theatreId(Long theatreId) { this.theatreId = theatreId; return this; }
        public ShowResponseBuilder theatreName(String theatreName) { this.theatreName = theatreName; return this; }
        public ShowResponseBuilder screenId(Long screenId) { this.screenId = screenId; return this; }
        public ShowResponseBuilder screenName(String screenName) { this.screenName = screenName; return this; }
        public ShowResponseBuilder showDate(LocalDate showDate) { this.showDate = showDate; return this; }
        public ShowResponseBuilder startTime(LocalTime startTime) { this.startTime = startTime; return this; }
        public ShowResponseBuilder endTime(LocalTime endTime) { this.endTime = endTime; return this; }
        public ShowResponseBuilder basePrice(BigDecimal basePrice) { this.basePrice = basePrice; return this; }
        public ShowResponseBuilder status(String status) { this.status = status; return this; }
        public ShowResponseBuilder availableSeats(int availableSeats) { this.availableSeats = availableSeats; return this; }

        public ShowResponse build() { return new ShowResponse(id, movieId, movieTitle, theatreId, theatreName, screenId, screenName, showDate, startTime, endTime, basePrice, status, availableSeats); }
    }

}
