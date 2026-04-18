package com.bookmyshow.dto.response; public class ScreenResponse {
    private Long id;

    private String name;

    private Long theatreId;

    private String theatreName;

    private Integer totalSeats;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public Long getTheatreId() { return this.theatreId; }
    public String getTheatreName() { return this.theatreName; }
    public Integer getTotalSeats() { return this.totalSeats; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTheatreId(Long theatreId) { this.theatreId = theatreId; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public ScreenResponse() {}

    public ScreenResponse(Long id, String name, Long theatreId, String theatreName, Integer totalSeats) {
        this.id = id;
        this.name = name;
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.totalSeats = totalSeats;
    }

    public static  ScreenResponseBuilder builder() { return new ScreenResponseBuilder(); }

    public static class ScreenResponseBuilder {
        private Long id;
        private String name;
        private Long theatreId;
        private String theatreName;
        private Integer totalSeats;

        public ScreenResponseBuilder id(Long id) { this.id = id; return this; }
        public ScreenResponseBuilder name(String name) { this.name = name; return this; }
        public ScreenResponseBuilder theatreId(Long theatreId) { this.theatreId = theatreId; return this; }
        public ScreenResponseBuilder theatreName(String theatreName) { this.theatreName = theatreName; return this; }
        public ScreenResponseBuilder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }

        public ScreenResponse build() { return new ScreenResponse(id, name, theatreId, theatreName, totalSeats); }
    }

}
