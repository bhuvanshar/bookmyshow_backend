package com.bookmyshow.controller;

import com.bookmyshow.dto.request.LoginRequest;
import com.bookmyshow.dto.request.RegisterRequest;
import com.bookmyshow.dto.response.AuthResponse;
import com.bookmyshow.security.*;
import com.bookmyshow.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(AuthControllerTest.MockConfig.class)
@Disabled("ByteBuddy 1.14.11 does not support JDK 25 - re-enable when upgrading to Spring Boot 3.4+ with Mockito 5.14+")
class AuthControllerTest {

    static class MockConfig {
        @Bean public AuthService authService() { return Mockito.mock(AuthService.class); }
        @Bean public JwtTokenProvider jwtTokenProvider() { return Mockito.mock(JwtTokenProvider.class); }
        @Bean public CustomUserDetailsService customUserDetailsService() { return Mockito.mock(CustomUserDetailsService.class); }
        @Bean public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() { return Mockito.mock(JwtAuthenticationEntryPoint.class); }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("POST /api/auth/register - should return 201 on successful registration")
    void testRegisterSuccess() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .fullName("John Doe")
                .email("john@test.com")
                .password("password123")
                .phoneNumber("9876543210")
                .build();

        AuthResponse response = AuthResponse.builder()
                .token("jwt-token-here")
                .email("john@test.com")
                .fullName("John Doe")
                .roles(List.of("ROLE_USER"))
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("jwt-token-here"))
                .andExpect(jsonPath("$.data.email").value("john@test.com"));
    }

    @Test
    @DisplayName("POST /api/auth/login - should return 200 on successful login")
    void testLoginSuccess() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("john@test.com")
                .password("password123")
                .build();

        AuthResponse response = AuthResponse.builder()
                .token("jwt-token-here")
                .email("john@test.com")
                .fullName("John Doe")
                .roles(List.of("ROLE_USER"))
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    @DisplayName("POST /api/auth/register - should return 400 for invalid data")
    void testRegisterValidationFailure() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .fullName("")
                .email("invalid-email")
                .password("123")
                .build();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - should return 400 for blank fields")
    void testLoginValidationFailure() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("")
                .password("")
                .build();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
