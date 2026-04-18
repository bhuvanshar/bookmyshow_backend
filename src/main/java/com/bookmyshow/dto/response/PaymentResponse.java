package com.bookmyshow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime; public class PaymentResponse {
    private Long id;

    private String transactionId;

    private BigDecimal amount;

    private String paymentMethod;

    private String status;

    private LocalDateTime paymentTime;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getTransactionId() { return this.transactionId; }
    public BigDecimal getAmount() { return this.amount; }
    public String getPaymentMethod() { return this.paymentMethod; }
    public String getStatus() { return this.status; }
    public LocalDateTime getPaymentTime() { return this.paymentTime; }

    public void setId(Long id) { this.id = id; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setStatus(String status) { this.status = status; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }

    public PaymentResponse() {}

    public PaymentResponse(Long id, String transactionId, BigDecimal amount, String paymentMethod, String status, LocalDateTime paymentTime) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentTime = paymentTime;
    }

    public static  PaymentResponseBuilder builder() { return new PaymentResponseBuilder(); }

    public static class PaymentResponseBuilder {
        private Long id;
        private String transactionId;
        private BigDecimal amount;
        private String paymentMethod;
        private String status;
        private LocalDateTime paymentTime;

        public PaymentResponseBuilder id(Long id) { this.id = id; return this; }
        public PaymentResponseBuilder transactionId(String transactionId) { this.transactionId = transactionId; return this; }
        public PaymentResponseBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public PaymentResponseBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public PaymentResponseBuilder status(String status) { this.status = status; return this; }
        public PaymentResponseBuilder paymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; return this; }

        public PaymentResponse build() { return new PaymentResponse(id, transactionId, amount, paymentMethod, status, paymentTime); }
    }

}
