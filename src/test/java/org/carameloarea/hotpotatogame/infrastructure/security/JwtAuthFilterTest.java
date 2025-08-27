package org.carameloarea.hotpotatogame.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("It should set Authentication when token is valid")
    void testFilterWithValidToken() throws ServletException, IOException {
        final String token = "valid.jwt.token";
        final String userEmail = "test@user.com";
        final UserDetails userDetails = new User(userEmail, "password", new ArrayList<>());

        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("It should not set Authentication when Authorization header is missing")
    void testFilterWithoutAuthorizationHeader() throws ServletException, IOException {
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("It should not set Authentication when header does not start with Bearer")
    void testFilterWithInvalidHeaderPrefix() throws ServletException, IOException {
        request.addHeader(HttpHeaders.AUTHORIZATION, "Basic some_credentials");

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("It should not set Authentication when token is invalid")
    void testFilterWithInvalidToken() throws ServletException, IOException {
        final String token = "invalid.jwt.token";
        final String userEmail = "test@user.com";
        final UserDetails userDetails = new User(userEmail, "password", new ArrayList<>());

        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("It should not set Authentication if user is already authenticated")
    void testFilterWhenUserIsAlreadyAuthenticated() throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(mock(org.springframework.security.core.Authentication.class));
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer some.token");

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}