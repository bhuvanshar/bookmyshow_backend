package com.bookmyshow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "theatres", indexes = {@Index(name = "idx_theatre_city", columnList = "city")}) public class Theatre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    private String state;
    private String pincode;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Screen> screens = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getAddress() { return this.address; }
    public String getCity() { return this.city; }
    public String getState() { return this.state; }
    public String getPincode() { return this.pincode; }
    public List<Screen> getScreens() { return this.screens; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public void setScreens(List<Screen> screens) { this.screens = screens; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Theatre() {}

    public Theatre(Long id, String name, String address, String city, String state, String pincode, List<Screen> screens, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.screens = screens;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static  TheatreBuilder builder() { return new TheatreBuilder(); }

    public static class TheatreBuilder {
        private Long id;
        private String name;
        private String address;
        private String city;
        private String state;
        private String pincode;
        private List<Screen> screens = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TheatreBuilder id(Long id) { this.id = id; return this; }
        public TheatreBuilder name(String name) { this.name = name; return this; }
        public TheatreBuilder address(String address) { this.address = address; return this; }
        public TheatreBuilder city(String city) { this.city = city; return this; }
        public TheatreBuilder state(String state) { this.state = state; return this; }
        public TheatreBuilder pincode(String pincode) { this.pincode = pincode; return this; }
        public TheatreBuilder screens(List<Screen> screens) { this.screens = screens; return this; }
        public TheatreBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public TheatreBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Theatre build() { return new Theatre(id, name, address, city, state, pincode, screens, createdAt, updatedAt); }
    }

}
