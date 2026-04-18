package com.bookmyshow.entity;

import com.bookmyshow.enums.SeatStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "show_seats", uniqueConstraints = {
    @UniqueConstraint(name = "uk_show_seat", columnNames = {"show_id", "seat_id"})
}, indexes = {
    @Index(name = "idx_showseat_show", columnList = "show_id"),
    @Index(name = "idx_showseat_status", columnList = "status"),
    @Index(name = "idx_showseat_locked", columnList = "lockedAt")
}) public class ShowSeat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Version
    private Integer version;

    private LocalDateTime lockedAt;
    private Long lockedBy;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public Show getShow() { return this.show; }
    public Seat getSeat() { return this.seat; }
    public SeatStatus getStatus() { return this.status; }
    public BigDecimal getPrice() { return this.price; }
    public Integer getVersion() { return this.version; }
    public LocalDateTime getLockedAt() { return this.lockedAt; }
    public Long getLockedBy() { return this.lockedBy; }

    public void setId(Long id) { this.id = id; }
    public void setShow(Show show) { this.show = show; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public void setStatus(SeatStatus status) { this.status = status; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setVersion(Integer version) { this.version = version; }
    public void setLockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; }
    public void setLockedBy(Long lockedBy) { this.lockedBy = lockedBy; }

    public ShowSeat() {}

    public ShowSeat(Long id, Show show, Seat seat, SeatStatus status, BigDecimal price, Integer version, LocalDateTime lockedAt, Long lockedBy) {
        this.id = id;
        this.show = show;
        this.seat = seat;
        this.status = status;
        this.price = price;
        this.version = version;
        this.lockedAt = lockedAt;
        this.lockedBy = lockedBy;
    }

    public static  ShowSeatBuilder builder() { return new ShowSeatBuilder(); }

    public static class ShowSeatBuilder {
        private Long id;
        private Show show;
        private Seat seat;
        private SeatStatus status = SeatStatus.AVAILABLE;
        private BigDecimal price;
        private Integer version;
        private LocalDateTime lockedAt;
        private Long lockedBy;

        public ShowSeatBuilder id(Long id) { this.id = id; return this; }
        public ShowSeatBuilder show(Show show) { this.show = show; return this; }
        public ShowSeatBuilder seat(Seat seat) { this.seat = seat; return this; }
        public ShowSeatBuilder status(SeatStatus status) { this.status = status; return this; }
        public ShowSeatBuilder price(BigDecimal price) { this.price = price; return this; }
        public ShowSeatBuilder version(Integer version) { this.version = version; return this; }
        public ShowSeatBuilder lockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; return this; }
        public ShowSeatBuilder lockedBy(Long lockedBy) { this.lockedBy = lockedBy; return this; }

        public ShowSeat build() { return new ShowSeat(id, show, seat, status, price, version, lockedAt, lockedBy); }
    }

}
