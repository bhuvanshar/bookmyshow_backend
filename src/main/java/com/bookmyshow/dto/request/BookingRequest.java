package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*;
import java.util.List; public class BookingRequest {
    @NotNull
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> showSeatIds;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getShowId() { return this.showId; }
    public List<Long> getShowSeatIds() { return this.showSeatIds; }

    public void setShowId(Long showId) { this.showId = showId; }
    public void setShowSeatIds(List<Long> showSeatIds) { this.showSeatIds = showSeatIds; }

    public BookingRequest() {}

    public BookingRequest(Long showId, List<Long> showSeatIds) {
        this.showId = showId;
        this.showSeatIds = showSeatIds;
    }

    public static  BookingRequestBuilder builder() { return new BookingRequestBuilder(); }

    public static class BookingRequestBuilder {
        private Long showId;
        private List<Long> showSeatIds;

        public BookingRequestBuilder showId(Long showId) { this.showId = showId; return this; }
        public BookingRequestBuilder showSeatIds(List<Long> showSeatIds) { this.showSeatIds = showSeatIds; return this; }

        public BookingRequest build() { return new BookingRequest(showId, showSeatIds); }
    }

}
