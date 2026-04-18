package com.bookmyshow.dto.response; public class TheatreResponse {
    private Long id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private int screenCount;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getAddress() { return this.address; }
    public String getCity() { return this.city; }
    public String getState() { return this.state; }
    public String getPincode() { return this.pincode; }
    public int getScreenCount() { return this.screenCount; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public void setScreenCount(int screenCount) { this.screenCount = screenCount; }

    public TheatreResponse() {}

    public TheatreResponse(Long id, String name, String address, String city, String state, String pincode, int screenCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.screenCount = screenCount;
    }

    public static  TheatreResponseBuilder builder() { return new TheatreResponseBuilder(); }

    public static class TheatreResponseBuilder {
        private Long id;
        private String name;
        private String address;
        private String city;
        private String state;
        private String pincode;
        private int screenCount;

        public TheatreResponseBuilder id(Long id) { this.id = id; return this; }
        public TheatreResponseBuilder name(String name) { this.name = name; return this; }
        public TheatreResponseBuilder address(String address) { this.address = address; return this; }
        public TheatreResponseBuilder city(String city) { this.city = city; return this; }
        public TheatreResponseBuilder state(String state) { this.state = state; return this; }
        public TheatreResponseBuilder pincode(String pincode) { this.pincode = pincode; return this; }
        public TheatreResponseBuilder screenCount(int screenCount) { this.screenCount = screenCount; return this; }

        public TheatreResponse build() { return new TheatreResponse(id, name, address, city, state, pincode, screenCount); }
    }

}
