package com.bookmyshow.dto.response;

import java.math.BigDecimal; public class BookedSeatInfo {
    private String rowName;

    private Integer seatNumber;

    private String seatType;

    private BigDecimal price;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getRowName() { return this.rowName; }
    public Integer getSeatNumber() { return this.seatNumber; }
    public String getSeatType() { return this.seatType; }
    public BigDecimal getPrice() { return this.price; }

    public void setRowName(String rowName) { this.rowName = rowName; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public void setSeatType(String seatType) { this.seatType = seatType; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BookedSeatInfo() {}

    public BookedSeatInfo(String rowName, Integer seatNumber, String seatType, BigDecimal price) {
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.price = price;
    }

    public static  BookedSeatInfoBuilder builder() { return new BookedSeatInfoBuilder(); }

    public static class BookedSeatInfoBuilder {
        private String rowName;
        private Integer seatNumber;
        private String seatType;
        private BigDecimal price;

        public BookedSeatInfoBuilder rowName(String rowName) { this.rowName = rowName; return this; }
        public BookedSeatInfoBuilder seatNumber(Integer seatNumber) { this.seatNumber = seatNumber; return this; }
        public BookedSeatInfoBuilder seatType(String seatType) { this.seatType = seatType; return this; }
        public BookedSeatInfoBuilder price(BigDecimal price) { this.price = price; return this; }

        public BookedSeatInfo build() { return new BookedSeatInfo(rowName, seatNumber, seatType, price); }
    }

}
