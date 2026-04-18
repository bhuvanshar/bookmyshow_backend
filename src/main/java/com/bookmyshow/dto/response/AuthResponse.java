package com.bookmyshow.dto.response;

import java.util.List; public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";

    private String email;

    private String fullName;

    private List<String> roles;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public String getToken() { return this.token; }
    public String getTokenType() { return this.tokenType; }
    public String getEmail() { return this.email; }
    public String getFullName() { return this.fullName; }
    public List<String> getRoles() { return this.roles; }

    public void setToken(String token) { this.token = token; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public void setEmail(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public AuthResponse() {}

    public AuthResponse(String token, String tokenType, String email, String fullName, List<String> roles) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }

    public static  AuthResponseBuilder builder() { return new AuthResponseBuilder(); }

    public static class AuthResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private String email;
        private String fullName;
        private List<String> roles;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public AuthResponseBuilder roles(List<String> roles) { this.roles = roles; return this; }

        public AuthResponse build() { return new AuthResponse(token, tokenType, email, fullName, roles); }
    }

}
