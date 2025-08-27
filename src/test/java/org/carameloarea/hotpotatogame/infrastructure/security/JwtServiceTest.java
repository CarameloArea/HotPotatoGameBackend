package org.carameloarea.hotpotatogame.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private Player player;
    private UserDetails userDetails;

    private static final String SECRET_KEY = "4840562D506153572A4265282126242B40252D33362A5A586A5E39773F7A2443";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L);

        player = Player.builder()
                .id(1)
                .email("generic@player.com")
                .nickname("genericPlayer")
                .build();

        userDetails = new User(player.getEmail(), "pass1234", new ArrayList<>());
    }

    @Test
    @DisplayName("It should be able to generate a valid token")
    void testGenerateToken() {
        String token = jwtService.generateToken(player);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("It should be able to extract username from token")
    void testExtractUsername() {
        String token = jwtService.generateToken(player);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(player.getEmail(), extractedUsername);
    }

    @Test
    @DisplayName("It should be able to extract custom claims from token")
    void testExtractCustomClaims() {
        String token = jwtService.generateToken(player);
        Integer extractedId = jwtService.extractClaim(token, claims -> claims.get("id", Integer.class));
        String extractedNickname = jwtService.extractClaim(token, claims -> claims.get("nickname", String.class));
        assertEquals(player.getId(), extractedId);
        assertEquals(player.getNickname(), extractedNickname);
    }

    @Test
    @DisplayName("It should be able to validate a correct token")
    void testReturnTrueWhenTokenIsValid() {
        String token = jwtService.generateToken(player);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("It should return false for a token with wrong username")
    void testReturnFalseWhenTokenHasWrongUsername() {
        String token = jwtService.generateToken(player);
        UserDetails otherUserDetails = new User("other@user.com", "password", new ArrayList<>());
        boolean isValid = jwtService.isTokenValid(token, otherUserDetails);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("It should throw exception for an expired token")
    void testThrowExceptionWhenTokenIsExpired() {
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1L);
        String expiredToken = jwtService.generateToken(player);
        assertThrows(ExpiredJwtException.class, () -> jwtService.extractUsername(expiredToken));
    }
}