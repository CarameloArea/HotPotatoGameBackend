package org.CarameloArea.HotPotatoGame.application.usecase;

import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayerByEmail;
import org.CarameloArea.HotPotatoGame.domain.exception.InvalidCredentialsException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @Mock
    private FindPlayerByEmail findPlayerByEmail;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    private Player playerSaved;
    private Player playerLoginAttempt;

    @BeforeEach
    void setUp() {
        playerSaved = Player.builder()
                .id(1)
                .nickname("Test Player")
                .email("test@player.com")
                .password("hashedPassword123")
                .build();

        playerLoginAttempt = Player.builder()
                .email("test@player.com")
                .password("plainPassword123")
                .build();
    }

    @Test
    @DisplayName("It should be able to login and return a JWT token on valid credentials")
    void testLoginSuccessfully() {
        when(findPlayerByEmail.findByEmail(playerLoginAttempt.getEmail())).thenReturn(Optional.of(playerSaved));
        when(passwordEncoder.matches(playerLoginAttempt.getPassword(), playerSaved.getPassword())).thenReturn(true);
        when(jwtService.generateToken(playerSaved)).thenReturn("mocked.jwt.token");

        String token = loginUseCase.execute(playerLoginAttempt);

        assertNotNull(token);
        assertEquals("mocked.jwt.token", token);
        verify(findPlayerByEmail, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(jwtService, times(1)).generateToken(any(Player.class));
    }

    @Test
    @DisplayName("It should throw InvalidCredentialsException when player is not found")
    void testThrowExceptionWhenPlayerNotFound() {
        when(findPlayerByEmail.findByEmail(playerLoginAttempt.getEmail())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> {
            loginUseCase.execute(playerLoginAttempt);
        });

        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(any(Player.class));
    }

    @Test
    @DisplayName("It should throw InvalidCredentialsException when password does not match")
    void testThrowExceptionWhenPasswordIsIncorrect() {
        when(findPlayerByEmail.findByEmail(playerLoginAttempt.getEmail())).thenReturn(Optional.of(playerSaved));
        when(passwordEncoder.matches(playerLoginAttempt.getPassword(), playerSaved.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            loginUseCase.execute(playerLoginAttempt);
        });

        verify(jwtService, never()).generateToken(any(Player.class));
    }
}