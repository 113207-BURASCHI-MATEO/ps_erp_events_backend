package com.tup.ps.erpevents.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tup.ps.erpevents.services.impl.JWTServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class JWTServiceImplTest {

    private JWTServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTServiceImpl();
    }

    @Test
    @DisplayName("JWT-001/Should create and verify token successfully")
    void testCreateAndVerifyToken() {
        String token = jwtService.createToken("testUser", 10);
        assertNotNull(token);

        DecodedJWT decodedJWT = jwtService.verifyToken(token);
        assertEquals("testUser", decodedJWT.getClaim("userId").asString());
    }

    @Test
    @DisplayName("JWT-002/Should throw exception on invalid token")
    void testInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertThrows(JWTVerificationException.class, () -> jwtService.verifyToken(invalidToken));
    }

    @Test
    @DisplayName("JWT-003/Should extract userId from token")
    void testExtractUserId() {
        String token = jwtService.createToken("extractedUser", 10);
        String userId = jwtService.extractUserId(token);
        assertEquals("extractedUser", userId);
    }

    @Test
    @DisplayName("JWT-004/Should get token from cookie")
    void testGetJwtFromCookies() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie[] cookies = { new Cookie("jwt-token", "sampleToken") };
        when(request.getCookies()).thenReturn(cookies);

        String token = jwtService.getJwtFromCookies(request);
        assertEquals("sampleToken", token);
    }

    @Test
    @DisplayName("JWT-005/Should get token from Authorization header")
    void testGetJwtFromAuthorizationHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn("Bearer authToken123");

        String token = jwtService.getJwtFromCookies(request);
        assertEquals("authToken123", token);
    }

    @Test
    @DisplayName("JWT-006/Should return null if no token found")
    void testGetJwtFromCookiesReturnsNull() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn(null);

        String token = jwtService.getJwtFromCookies(request);
        assertNull(token);
    }
}

