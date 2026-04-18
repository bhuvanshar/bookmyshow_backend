package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*; public class RegisterRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phoneNumber;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getFullName() { return this.fullName; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public String getPhoneNumber() { return this.phoneNumber; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public RegisterRequest() {}

    public RegisterRequest(String fullName, String email, String password, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static  RegisterRequestBuilder builder() { return new RegisterRequestBuilder(); }

    public static class RegisterRequestBuilder {
        private String fullName;
        private String email;
        private String password;
        private String phoneNumber;

        public RegisterRequestBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public RegisterRequestBuilder email(String email) { this.email = email; return this; }
        public RegisterRequestBuilder password(String password) { this.password = password; return this; }
        public RegisterRequestBuilder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }

        public RegisterRequest build() { return new RegisterRequest(fullName, email, password, phoneNumber); }
    }

}
