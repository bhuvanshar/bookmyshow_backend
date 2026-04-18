package com.bookmyshow.dto.response;

import java.math.BigDecimal; public class ShowSeatResponse {
    private Long id;

    private String rowName;

    private Integer seatNumber;

    private String seatType;

    private String status;

    private BigDecimal price;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getRowName() { return this.rowName; }
    public Integer getSeatNumber() { return this.seatNumber; }
    public String getSeatType() { return this.seatType; }
    public String getStatus() { return this.status; }
    public BigDecimal getPrice() { return this.price; }

    public void setId(Long id) { this.id = id; }
    public void setRowName(String rowName) { this.rowName = rowName; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public void setSeatType(String seatType) { this.seatType = seatType; }
    public void setStatus(String status) { this.status = status; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public ShowSeatResponse() {}

    public ShowSeatResponse(Long id, String rowName, Integer seatNumber, String seatType, String status, BigDecimal price) {
        this.id = id;
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.status = status;
        this.price = price;
    }

    public static  ShowSeatResponseBuilder builder() { return new ShowSeatResponseBuilder(); }

    public static class ShowSeatResponseBuilder {
        private Long id;
        private String rowName;
        private Integer seatNumber;
        private String seatType;
        private String status;
        private BigDecimal price;

        public ShowSeatResponseBuilder id(Long id) { this.id = id; return this; }
        public ShowSeatResponseBuilder rowName(String rowName) { this.rowName = rowName; return this; }
        public ShowSeatResponseBuilder seatNumber(Integer seatNumber) { this.seatNumber = seatNumber; return this; }
        public ShowSeatResponseBuilder seatType(String seatType) { this.seatType = seatType; return this; }
        public ShowSeatResponseBuilder status(String status) { this.status = status; return this; }
        public ShowSeatResponseBuilder price(BigDecimal price) { this.price = price; return this; }

        public ShowSeatResponse build() { return new ShowSeatResponse(id, rowName, seatNumber, seatType, status, price); }
    }

}
