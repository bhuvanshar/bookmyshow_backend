package com.bookmyshow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List; public class CompleteBookingRequest {
    @NotNull(message = "Show ID cannot be null")
    private Long showId;

    @NotEmpty(message = "Show seat IDs cannot be empty")
    private List<Long> showSeatIds;

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;
    private Boolean simulateFailure = false;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getShowId() { return this.showId; }
    public List<Long> getShowSeatIds() { return this.showSeatIds; }
    public String getPaymentMethod() { return this.paymentMethod; }
    public Boolean getSimulateFailure() { return this.simulateFailure; }

    public void setShowId(Long showId) { this.showId = showId; }
    public void setShowSeatIds(List<Long> showSeatIds) { this.showSeatIds = showSeatIds; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setSimulateFailure(Boolean simulateFailure) { this.simulateFailure = simulateFailure; }

    public CompleteBookingRequest() {}

    public CompleteBookingRequest(Long showId, List<Long> showSeatIds, String paymentMethod, Boolean simulateFailure) {
        this.showId = showId;
        this.showSeatIds = showSeatIds;
        this.paymentMethod = paymentMethod;
        this.simulateFailure = simulateFailure;
    }

    public static  CompleteBookingRequestBuilder builder() { return new CompleteBookingRequestBuilder(); }

    public static class CompleteBookingRequestBuilder {
        private Long showId;
        private List<Long> showSeatIds;
        private String paymentMethod;
        private Boolean simulateFailure = false;

        public CompleteBookingRequestBuilder showId(Long showId) { this.showId = showId; return this; }
        public CompleteBookingRequestBuilder showSeatIds(List<Long> showSeatIds) { this.showSeatIds = showSeatIds; return this; }
        public CompleteBookingRequestBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public CompleteBookingRequestBuilder simulateFailure(Boolean simulateFailure) { this.simulateFailure = simulateFailure; return this; }

        public CompleteBookingRequest build() { return new CompleteBookingRequest(showId, showSeatIds, paymentMethod, simulateFailure); }
    }

}
