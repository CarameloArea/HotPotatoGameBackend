package org.carameloarea.hotpotatogame.application.usecase;

import org.carameloarea.hotpotatogame.application.port.driven.UpdatePlayer;
import org.carameloarea.hotpotatogame.domain.exception.EntityNotFoundException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePlayerUseCaseImplTest {

    @Mock
    private UpdatePlayer updatePlayer;

    @InjectMocks
    private UpdatePlayerUseCaseImpl updatePlayerUseCase;

    private Player playerToUpdate;
    private Player expectedPlayerUpdated;
    private static final String ENTITY_NAME = "Player";
    private static final int NON_EXISTENT_ID = 99999;


    @BeforeEach
    void setUp() {
        playerToUpdate = Player.builder()
                .id(1)
                .nickname("Generic PLayer")
                .email("generic.player@new.com")
                .password("Pass12345")
                .build();

        expectedPlayerUpdated = Player.builder()
                .id(1)
                .nickname("Generic PLayer UP")
                .email("generic.player@updated.com")
                .password("Pass12345")
                .build();
    }

    @Test
    @DisplayName("It should be able to update a player")
    void testUpdate() {
        when(updatePlayer.update(playerToUpdate.getId(), playerToUpdate)).thenReturn(expectedPlayerUpdated);

        Player playerUpdated = this.updatePlayerUseCase.execute(playerToUpdate.getId(), playerToUpdate);

        assertNotNull(playerUpdated.getId());
        assertEquals(playerToUpdate.getId(), playerUpdated.getId());
        assertEquals(playerUpdated.getNickname(), expectedPlayerUpdated.getNickname());
        assertEquals(playerUpdated.getEmail(), expectedPlayerUpdated.getEmail());

        verify(this.updatePlayer, times(1)).update(any(Integer.class), any(Player.class));
    }

    @Test
    @DisplayName("It should make sure to return 404 when the player does not exist on update")
    void testReturnNotFoundErrorWhenUpdateWithNonExistentPlayer() {
        when(updatePlayer.update(NON_EXISTENT_ID, playerToUpdate))
                .thenThrow(new EntityNotFoundException(ENTITY_NAME));

        assertThrows(EntityNotFoundException.class, () -> {
            updatePlayerUseCase.execute(NON_EXISTENT_ID, playerToUpdate);
        });

        verify(updatePlayer, times(1)).update(NON_EXISTENT_ID, playerToUpdate);
    }

}