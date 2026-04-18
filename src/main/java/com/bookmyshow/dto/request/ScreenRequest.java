package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*; public class ScreenRequest {
    @NotBlank
    private String name;

    @NotNull
    private Long theatreId;

    @NotNull
    @Positive
    private Integer totalSeats;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getName() { return this.name; }
    public Long getTheatreId() { return this.theatreId; }
    public Integer getTotalSeats() { return this.totalSeats; }

    public void setName(String name) { this.name = name; }
    public void setTheatreId(Long theatreId) { this.theatreId = theatreId; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public ScreenRequest() {}

    public ScreenRequest(String name, Long theatreId, Integer totalSeats) {
        this.name = name;
        this.theatreId = theatreId;
        this.totalSeats = totalSeats;
    }

    public static  ScreenRequestBuilder builder() { return new ScreenRequestBuilder(); }

    public static class ScreenRequestBuilder {
        private String name;
        private Long theatreId;
        private Integer totalSeats;

        public ScreenRequestBuilder name(String name) { this.name = name; return this; }
        public ScreenRequestBuilder theatreId(Long theatreId) { this.theatreId = theatreId; return this; }
        public ScreenRequestBuilder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }

        public ScreenRequest build() { return new ScreenRequest(name, theatreId, totalSeats); }
    }

}
