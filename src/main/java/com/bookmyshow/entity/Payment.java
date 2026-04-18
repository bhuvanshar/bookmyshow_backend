package com.bookmyshow.entity;

import com.bookmyshow.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payment_booking", columnList = "booking_id"),
    @Index(name = "idx_payment_txn", columnList = "transactionId")
}) public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String paymentMethod;

    @Column(unique = true)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    private LocalDateTime paymentTime;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public Booking getBooking() { return this.booking; }
    public BigDecimal getAmount() { return this.amount; }
    public String getPaymentMethod() { return this.paymentMethod; }
    public String getTransactionId() { return this.transactionId; }
    public PaymentStatus getStatus() { return this.status; }
    public LocalDateTime getPaymentTime() { return this.paymentTime; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Payment() {}

    public Payment(Long id, Booking booking, BigDecimal amount, String paymentMethod, String transactionId, PaymentStatus status, LocalDateTime paymentTime, LocalDateTime createdAt) {
        this.id = id;
        this.booking = booking;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
        this.paymentTime = paymentTime;
        this.createdAt = createdAt;
    }

    public static  PaymentBuilder builder() { return new PaymentBuilder(); }

    public static class PaymentBuilder {
        private Long id;
        private Booking booking;
        private BigDecimal amount;
        private String paymentMethod;
        private String transactionId;
        private PaymentStatus status = PaymentStatus.PENDING;
        private LocalDateTime paymentTime;
        private LocalDateTime createdAt;

        public PaymentBuilder id(Long id) { this.id = id; return this; }
        public PaymentBuilder booking(Booking booking) { this.booking = booking; return this; }
        public PaymentBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public PaymentBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public PaymentBuilder transactionId(String transactionId) { this.transactionId = transactionId; return this; }
        public PaymentBuilder status(PaymentStatus status) { this.status = status; return this; }
        public PaymentBuilder paymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; return this; }
        public PaymentBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Payment build() { return new Payment(id, booking, amount, paymentMethod, transactionId, status, paymentTime, createdAt); }
    }

}
