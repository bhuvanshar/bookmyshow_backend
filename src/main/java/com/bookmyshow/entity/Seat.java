package com.bookmyshow.entity;

import com.bookmyshow.enums.SeatType;
import jakarta.persistence.*;

@Entity
@Table(name = "seats", uniqueConstraints = {
    @UniqueConstraint(name = "uk_seat_screen_row_num", columnNames = {"screen_id", "rowName", "seatNumber"})
}) public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(nullable = false, length = 5)
    private String rowName;

    @Column(nullable = false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType = SeatType.REGULAR;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public Screen getScreen() { return this.screen; }
    public String getRowName() { return this.rowName; }
    public Integer getSeatNumber() { return this.seatNumber; }
    public SeatType getSeatType() { return this.seatType; }

    public void setId(Long id) { this.id = id; }
    public void setScreen(Screen screen) { this.screen = screen; }
    public void setRowName(String rowName) { this.rowName = rowName; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }

    public Seat() {}

    public Seat(Long id, Screen screen, String rowName, Integer seatNumber, SeatType seatType) {
        this.id = id;
        this.screen = screen;
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public static  SeatBuilder builder() { return new SeatBuilder(); }

    public static class SeatBuilder {
        private Long id;
        private Screen screen;
        private String rowName;
        private Integer seatNumber;
        private SeatType seatType = SeatType.REGULAR;

        public SeatBuilder id(Long id) { this.id = id; return this; }
        public SeatBuilder screen(Screen screen) { this.screen = screen; return this; }
        public SeatBuilder rowName(String rowName) { this.rowName = rowName; return this; }
        public SeatBuilder seatNumber(Integer seatNumber) { this.seatNumber = seatNumber; return this; }
        public SeatBuilder seatType(SeatType seatType) { this.seatType = seatType; return this; }

        public Seat build() { return new Seat(id, screen, rowName, seatNumber, seatType); }
    }

}
