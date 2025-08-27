package org.carameloarea.hotpotatogame.application.usecase;

import org.carameloarea.hotpotatogame.application.port.driven.CheckPlayerByEmail;
import org.carameloarea.hotpotatogame.application.port.driven.CheckPlayerByNickname;
import org.carameloarea.hotpotatogame.application.port.driven.SavePlayer;
import org.carameloarea.hotpotatogame.domain.exception.EmailAlreadyUsedException;
import org.carameloarea.hotpotatogame.domain.exception.NicknameAlreadyUsedException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePlayerUseCaseImplTest {

    @Mock
    private SavePlayer savePlayer;

    @Mock
    private CheckPlayerByEmail checkPlayerByEmail;

    @Mock
    private CheckPlayerByNickname checkPlayerByNickname;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreatePlayerUseCaseImpl createPlayerUseCase;

    private Player playerToCreate;
    private Player expectedPlayerSaved;

    @BeforeEach
    void setUp() {
        playerToCreate = Player.builder()
                .nickname("Generic PLayer")
                .email("generic.player@new.com")
                .password("Pass12345")
                .build();

        expectedPlayerSaved = Player.builder()
                .id(1)
                .nickname("Generic PLayer")
                .email("generic.player@new.com")
                .password("Pass12345")
                .build();
    }

    @Test
    @DisplayName("It should be able to create a player when email and nickname are unics")
    void testCreate() {
        when(this.checkPlayerByEmail.execute(playerToCreate.getEmail())).thenReturn(false);
        when(this.checkPlayerByNickname.execute(playerToCreate.getNickname())).thenReturn(false);
        when(this.passwordEncoder.encode(playerToCreate.getPassword())).thenReturn("Pass12345");
        when(this.savePlayer.save(playerToCreate)).thenReturn(expectedPlayerSaved);

        Player playerSaved = this.createPlayerUseCase.execute(playerToCreate);

        assertNotNull(playerSaved.getId());
        assertEquals(playerToCreate.getNickname(), playerSaved.getNickname());
        assertEquals(playerToCreate.getEmail(), playerSaved.getEmail());

        verify(this.checkPlayerByEmail, times(1)).execute(playerToCreate.getEmail());
        verify(this.checkPlayerByNickname, times(1)).execute(playerToCreate.getNickname());
        verify(this.passwordEncoder, times(1)).encode(playerToCreate.getPassword());
        verify(this.savePlayer, times(1)).save(any(Player.class));
    }

    @Test
    @DisplayName("It should make sure to return 409 when email already exists")
    void testReturnConflictErrorWhenCreateWithAlreadyExistingEmail() {
        when(checkPlayerByEmail.execute(playerToCreate.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyUsedException.class, () -> {
            createPlayerUseCase.execute(playerToCreate);
        });

        verify(checkPlayerByNickname, never()).execute(anyString());
        verify(savePlayer, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("It should make sure to return 409 when nickname already exists")
    void testReturnConflictErrorWhenCreateWithAlreadyExistingNickname() {
        when(checkPlayerByEmail.execute(playerToCreate.getEmail())).thenReturn(false);
        when(checkPlayerByNickname.execute(playerToCreate.getNickname())).thenReturn(true);

        assertThrows(NicknameAlreadyUsedException.class, () -> {
            createPlayerUseCase.execute(playerToCreate);
        });

        verify(this.passwordEncoder, never()).encode(playerToCreate.getPassword());
        verify(savePlayer, never()).save(any(Player.class));
    }

}