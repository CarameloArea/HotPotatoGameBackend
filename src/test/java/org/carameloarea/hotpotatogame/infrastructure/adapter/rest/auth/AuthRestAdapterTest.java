package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.carameloarea.hotpotatogame.AbstractIntegrationTest;
import org.carameloarea.hotpotatogame.application.port.driver.LoginUseCase;
import org.carameloarea.hotpotatogame.domain.exception.InvalidCredentialsException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class AuthRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoginUseCase loginUseCase;

    public static final String AUTH_URL = "/api/v1/auth";
    public static final String LOGIN_URL = AUTH_URL + "/login";
    private static final String FAKE_JWT = "eyJhbGciOiJIUzI1NiJ9.e30.ZRrHA1DDvdvA-i2n_i-_M36Lw2ht_u2IBB5J3RTaXzU";
    private static final String JSON_PATH_TOKEN = "$.token";
    private static final String JSON_PATH_TITLE_ERROR = "$.title";

    @Test
    @DisplayName("It should be able to authenticate and return a JWT token")
    void testLoginWithValidCredentials() throws Exception {
        LoginRequest requestBody = new LoginRequest("test@player.com", "password123");

        when(loginUseCase.execute(any(Player.class))).thenReturn(FAKE_JWT);

        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + FAKE_JWT))
                .andExpect(jsonPath(JSON_PATH_TOKEN, is(FAKE_JWT)));

        verify(loginUseCase, times(1)).execute(any(Player.class));
    }

    @Test
    @DisplayName("It should return 401 Unauthorized for invalid credentials")
    void testLoginWithInvalidCredentials() throws Exception {
        LoginRequest requestBody = new LoginRequest("wrong@player.com", "wrongpassword");

        when(loginUseCase.execute(any(Player.class))).thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath(JSON_PATH_TITLE_ERROR, is("Invalid credentials")));

        verify(loginUseCase, times(1)).execute(any(Player.class));
    }
}