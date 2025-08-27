package org.carameloarea.hotpotatogame.application.usecase;

import org.carameloarea.hotpotatogame.application.port.driven.DeletePlayer;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePlayerUseCaseImplTest {

    @Mock
    private DeletePlayer deletePlayer;

    @InjectMocks
    private DeletePlayerUseCaseImpl deletePlayerUseCase;

    private Player playerToDelete;
    private static final String ENTITY_NAME = "Player";
    private static final int NON_EXISTENT_ID = 99999;

    @BeforeEach
    void setUp() {
        playerToDelete = Player.builder()
                .id(1)
                .nickname("Generic PLayer")
                .email("generic.player@new.com")
                .password("Pass12345")
                .build();
    }

    @Test
    @DisplayName("It should be able to delete a player")
    void testDelete() {
        doNothing().when(deletePlayer).deleteById(any(Integer.class));

        this.deletePlayerUseCase.execute(playerToDelete.getId());

        verify(this.deletePlayer, times(1)).deleteById(any(Integer.class));
    }

    @Test
    @DisplayName("It should make sure to return 404 when the player does not exist on delete")
    void testReturnNotFoundErrorWhenDeleteWithNonExistentPlayer() {
        doThrow(new EntityNotFoundException(ENTITY_NAME)).when(deletePlayer).deleteById(any(Integer.class));

        assertThrows(EntityNotFoundException.class, () -> {
            deletePlayerUseCase.execute(NON_EXISTENT_ID);
        });

        verify(this.deletePlayer, times(1)).deleteById(any(Integer.class));
    }

}