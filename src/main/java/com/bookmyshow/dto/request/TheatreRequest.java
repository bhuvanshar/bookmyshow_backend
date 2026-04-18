package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*; public class TheatreRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    private String state;

    private String pincode;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getName() { return this.name; }
    public String getAddress() { return this.address; }
    public String getCity() { return this.city; }
    public String getState() { return this.state; }
    public String getPincode() { return this.pincode; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public TheatreRequest() {}

    public TheatreRequest(String name, String address, String city, String state, String pincode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public static  TheatreRequestBuilder builder() { return new TheatreRequestBuilder(); }

    public static class TheatreRequestBuilder {
        private String name;
        private String address;
        private String city;
        private String state;
        private String pincode;

        public TheatreRequestBuilder name(String name) { this.name = name; return this; }
        public TheatreRequestBuilder address(String address) { this.address = address; return this; }
        public TheatreRequestBuilder city(String city) { this.city = city; return this; }
        public TheatreRequestBuilder state(String state) { this.state = state; return this; }
        public TheatreRequestBuilder pincode(String pincode) { this.pincode = pincode; return this; }

        public TheatreRequest build() { return new TheatreRequest(name, address, city, state, pincode); }
    }

}
