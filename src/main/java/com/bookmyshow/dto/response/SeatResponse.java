package com.bookmyshow.dto.response; public class SeatResponse {
    private Long id;

    private String rowName;

    private Integer seatNumber;

    private String seatType;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getRowName() { return this.rowName; }
    public Integer getSeatNumber() { return this.seatNumber; }
    public String getSeatType() { return this.seatType; }

    public void setId(Long id) { this.id = id; }
    public void setRowName(String rowName) { this.rowName = rowName; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public SeatResponse() {}

    public SeatResponse(Long id, String rowName, Integer seatNumber, String seatType) {
        this.id = id;
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public static  SeatResponseBuilder builder() { return new SeatResponseBuilder(); }

    public static class SeatResponseBuilder {
        private Long id;
        private String rowName;
        private Integer seatNumber;
        private String seatType;

        public SeatResponseBuilder id(Long id) { this.id = id; return this; }
        public SeatResponseBuilder rowName(String rowName) { this.rowName = rowName; return this; }
        public SeatResponseBuilder seatNumber(Integer seatNumber) { this.seatNumber = seatNumber; return this; }
        public SeatResponseBuilder seatType(String seatType) { this.seatType = seatType; return this; }

        public SeatResponse build() { return new SeatResponse(id, rowName, seatNumber, seatType); }
    }

}
