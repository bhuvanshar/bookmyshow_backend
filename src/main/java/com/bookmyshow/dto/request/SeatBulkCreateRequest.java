package com.bookmyshow.dto.request;

import com.bookmyshow.enums.SeatType;
import jakarta.validation.constraints.*;
import java.util.List; public class SeatBulkCreateRequest {
    @NotNull
    private Long screenId;

    @NotEmpty
    private List<RowConfig> rows; public static class RowConfig {
        @NotBlank
        private String rowName;

        @NotNull
        @Positive
        private Integer seatCount;

        @NotNull
        private SeatType seatType;

        public RowConfig() {}
        public RowConfig(String rowName, Integer seatCount, SeatType seatType) {
            this.rowName = rowName;
            this.seatCount = seatCount;
            this.seatType = seatType;
        }
        public String getRowName() { return this.rowName; }
        public Integer getSeatCount() { return this.seatCount; }
        public SeatType getSeatType() { return this.seatType; }
        public void setRowName(String rowName) { this.rowName = rowName; }
        public void setSeatCount(Integer seatCount) { this.seatCount = seatCount; }
        public void setSeatType(SeatType seatType) { this.seatType = seatType; }
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getScreenId() { return this.screenId; }
    public List<RowConfig> getRows() { return this.rows; }

    public void setScreenId(Long screenId) { this.screenId = screenId; }
    public void setRows(List<RowConfig> rows) { this.rows = rows; }

    public SeatBulkCreateRequest() {}

    public SeatBulkCreateRequest(Long screenId, List<RowConfig> rows) {
        this.screenId = screenId;
        this.rows = rows;
    }

    public static  SeatBulkCreateRequestBuilder builder() { return new SeatBulkCreateRequestBuilder(); }

    public static class SeatBulkCreateRequestBuilder {
        private Long screenId;
        private List<RowConfig> rows;

        public SeatBulkCreateRequestBuilder screenId(Long screenId) { this.screenId = screenId; return this; }
        public SeatBulkCreateRequestBuilder rows(List<RowConfig> rows) { this.rows = rows; return this; }

        public SeatBulkCreateRequest build() { return new SeatBulkCreateRequest(screenId, rows); }
    }

}
