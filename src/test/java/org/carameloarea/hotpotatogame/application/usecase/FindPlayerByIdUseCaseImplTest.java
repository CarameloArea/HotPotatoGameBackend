package org.carameloarea.hotpotatogame.application.usecase;

import org.carameloarea.hotpotatogame.application.port.driven.FindPlayerById;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPlayerByIdUseCaseImplTest {

    @Mock
    private FindPlayerById findPlayerById;

    @InjectMocks
    private FindPlayerUseCaseImpl findPlayerUseCase;

    private Player playerToFind;
    private Player expectedPlayer;
    private static final String ENTITY_NAME = "Player";
    private static final int NON_EXISTENT_ID = 99999;

    @BeforeEach
    void setUp() {
        playerToFind = Player.builder()
                .id(1)
                .nickname("Generic PLayer")
                .email("generic.player@new.com")
                .password("Pass12345")
                .build();

        expectedPlayer = Player.builder()
                .id(1)
                .nickname("Generic PLayer")
                .email("generic.player@new.com")
                .password("Pass12345")
                .build();
    }

    @Test
    @DisplayName("It should be able to return player if exists")
    void testFindById() {
        when(findPlayerById.findById(playerToFind.getId())).thenReturn(expectedPlayer);

        Player playerFound = findPlayerUseCase.execute(playerToFind.getId());

        assertNotNull(playerFound.getId());
        assertEquals(playerToFind.getId(), playerFound.getId());

        verify(findPlayerById, times(1)).findById(playerToFind.getId());
    }

    @Test
    @DisplayName("It should make sure to return 404 when the player does not exist")
    void testReturnNotFoundErrorWhenFindByIdWithNonExistentPlayer() {
        when(findPlayerById.findById(NON_EXISTENT_ID))
                .thenThrow(new EntityNotFoundException(ENTITY_NAME));

        assertThrows(EntityNotFoundException.class, () -> {
            findPlayerUseCase.execute(NON_EXISTENT_ID);
        });

        verify(findPlayerById, times(1)).findById(NON_EXISTENT_ID);
    }

}