package com.vishal.jira_clone.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    private void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(
                jwtService, "secretKey", Base64.getEncoder().encodeToString(new byte[32]));
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 60_000L);
        userDetails = User.withUsername("user@example.com")
                .password("password")
                .roles("USER")
                .build();
    }

    @Test
    void generatesAndValidatesTokenForMatchingUser() {
        setUp();
        String token = jwtService.generateToken(userDetails);

        assertEquals("user@example.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void rejectsTokenForDifferentUser() {
        setUp();
        String token = jwtService.generateToken(userDetails);
        UserDetails otherUser = User.withUsername("other@example.com")
                .password("password")
                .roles("USER")
                .build();

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void rejectsTokenWithInvalidSignature() {
        setUp();
        String token = jwtService.generateToken(userDetails);
        String[] tokenParts = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(tokenParts[1]))
                .replace("user@example.com", "other@example.com");
        tokenParts[1] = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes());
        String tamperedToken = String.join(".", tokenParts);

        JwtException exception = assertThrows(
                JwtException.class, () -> jwtService.extractUsername(tamperedToken));
        assertNotNull(exception);
    }
}
