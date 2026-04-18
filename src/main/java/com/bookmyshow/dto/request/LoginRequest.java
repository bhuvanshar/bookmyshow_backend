package com.bookmyshow.dto.request;

import jakarta.validation.constraints.*; public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static  LoginRequestBuilder builder() { return new LoginRequestBuilder(); }

    public static class LoginRequestBuilder {
        private String email;
        private String password;

        public LoginRequestBuilder email(String email) { this.email = email; return this; }
        public LoginRequestBuilder password(String password) { this.password = password; return this; }

        public LoginRequest build() { return new LoginRequest(email, password); }
    }

}
