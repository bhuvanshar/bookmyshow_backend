package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*; public class PaymentRequest {
    @NotNull
    private Long bookingId;

    @NotBlank
    private String paymentMethod;
    private Boolean simulateFailure = false;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getBookingId() { return this.bookingId; }
    public String getPaymentMethod() { return this.paymentMethod; }
    public Boolean getSimulateFailure() { return this.simulateFailure; }

    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setSimulateFailure(Boolean simulateFailure) { this.simulateFailure = simulateFailure; }

    public PaymentRequest() {}

    public PaymentRequest(Long bookingId, String paymentMethod, Boolean simulateFailure) {
        this.bookingId = bookingId;
        this.paymentMethod = paymentMethod;
        this.simulateFailure = simulateFailure;
    }

    public static  PaymentRequestBuilder builder() { return new PaymentRequestBuilder(); }

    public static class PaymentRequestBuilder {
        private Long bookingId;
        private String paymentMethod;
        private Boolean simulateFailure = false;

        public PaymentRequestBuilder bookingId(Long bookingId) { this.bookingId = bookingId; return this; }
        public PaymentRequestBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public PaymentRequestBuilder simulateFailure(Boolean simulateFailure) { this.simulateFailure = simulateFailure; return this; }

        public PaymentRequest build() { return new PaymentRequest(bookingId, paymentMethod, simulateFailure); }
    }

}
