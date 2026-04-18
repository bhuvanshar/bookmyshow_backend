package com.bookmyshow.dto.request;

import com.bookmyshow.enums.SeatType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.Map; public class ShowRequest {
    @NotNull
    private Long movieId;

    @NotNull
    private Long screenId;

    @NotNull
    private LocalDate showDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @Positive
    private BigDecimal basePrice;

    private Map<SeatType, BigDecimal> seatPricing;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getMovieId() { return this.movieId; }
    public Long getScreenId() { return this.screenId; }
    public LocalDate getShowDate() { return this.showDate; }
    public LocalTime getStartTime() { return this.startTime; }
    public LocalTime getEndTime() { return this.endTime; }
    public BigDecimal getBasePrice() { return this.basePrice; }
    public Map<SeatType, BigDecimal> getSeatPricing() { return this.seatPricing; }

    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public void setScreenId(Long screenId) { this.screenId = screenId; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    public void setSeatPricing(Map<SeatType, BigDecimal> seatPricing) { this.seatPricing = seatPricing; }

    public ShowRequest() {}

    public ShowRequest(Long movieId, Long screenId, LocalDate showDate, LocalTime startTime, LocalTime endTime, BigDecimal basePrice, Map<SeatType, BigDecimal> seatPricing) {
        this.movieId = movieId;
        this.screenId = screenId;
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.seatPricing = seatPricing;
    }

    public static  ShowRequestBuilder builder() { return new ShowRequestBuilder(); }

    public static class ShowRequestBuilder {
        private Long movieId;
        private Long screenId;
        private LocalDate showDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private BigDecimal basePrice;
        private Map<SeatType, BigDecimal> seatPricing;

        public ShowRequestBuilder movieId(Long movieId) { this.movieId = movieId; return this; }
        public ShowRequestBuilder screenId(Long screenId) { this.screenId = screenId; return this; }
        public ShowRequestBuilder showDate(LocalDate showDate) { this.showDate = showDate; return this; }
        public ShowRequestBuilder startTime(LocalTime startTime) { this.startTime = startTime; return this; }
        public ShowRequestBuilder endTime(LocalTime endTime) { this.endTime = endTime; return this; }
        public ShowRequestBuilder basePrice(BigDecimal basePrice) { this.basePrice = basePrice; return this; }
        public ShowRequestBuilder seatPricing(Map<SeatType, BigDecimal> seatPricing) { this.seatPricing = seatPricing; return this; }

        public ShowRequest build() { return new ShowRequest(movieId, screenId, showDate, startTime, endTime, basePrice, seatPricing); }
    }

}
