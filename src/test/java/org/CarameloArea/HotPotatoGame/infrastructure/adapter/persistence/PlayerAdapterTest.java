package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence;

import org.CarameloArea.HotPotatoGame.domain.exception.EntityNotFoundException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@Import({PlayerAdapter.class, PlayerAdapterTest.MapperTestConfig.class})
class PlayerAdapterTest {

    @TestConfiguration
    static class MapperTestConfig {
        @Bean
        public PlayerPersistenceMapper playerPersistenceMapper() {
            return Mappers.getMapper(PlayerPersistenceMapper.class);
        }
    }

    @Autowired
    private PlayerAdapter playerAdapter;

    @Autowired
    private PlayerRepository playerRepository;


    private static final String ENTITY_NAME = "Player";
    private static final int NON_EXISTENT_ID = 99999;

    @Test
    @DisplayName("It should be able to register a new player in the system")
    void testSave() {
        Player playerToSave = Player.builder()
                .nickname("newPLayer")
                .email("generic.player@new.com")
                .password("Pass123456")
                .build();

        Player playerSaved = playerAdapter.save(playerToSave);

        assertThat(playerSaved).isNotNull();
        assertThat(playerSaved.getId()).isNotNull();
        assertThat(playerSaved.getNickname()).isEqualTo("newPLayer");

        assertThat(playerRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("It should be able to return player")
    void testFindById() {
        PlayerEntity playerEntity =
                new PlayerEntity()
                        .nickname("existentPlayer")
                        .email("player@find.com")
                        .password("pass")
                        .icon("RED");

        PlayerEntity entity = playerRepository.save(playerEntity);

        Player playerFound = playerAdapter.findById(entity.getId());

        assertThat(playerFound).isNotNull();
        assertThat(playerFound.getId()).isEqualTo(entity.getId());
        assertThat(playerFound.getNickname()).isEqualTo("existentPlayer");
    }

    @Test
    @DisplayName("It should make sure to return 404 when the player does not exist")
    void testReturnNotFoundErrorWhenFindByIdWithNonExistentPlayer() {
        assertThrows(EntityNotFoundException.class, () -> {
            playerAdapter.findById(NON_EXISTENT_ID);
        });
    }

    @Test
    @DisplayName("It should be able to update a player")
    void testUpdate() {
        PlayerEntity playerEntity =
                new PlayerEntity()
                        .nickname("existentPlayer")
                        .email("player@find.com")
                        .password("pass")
                        .icon("RED");

        PlayerEntity originalEntity = playerRepository.save(playerEntity);

        Player playerWithNewData = Player.builder()
                .nickname("newNickname")
                .email("player@update.com")
                .build();

        playerAdapter.update(originalEntity.getId(), playerWithNewData);

        PlayerEntity updatedEntity = playerRepository.findById(originalEntity.getId()).get();
        assertThat(updatedEntity.getNickname()).isEqualTo("newNickname");
    }

    @Test
    @DisplayName("It should be able to delete a player")
    void testDeleteById() {
        PlayerEntity playerEntity =
                new PlayerEntity()
                        .nickname("existentPlayer")
                        .email("player@find.com")
                        .password("pass")
                        .icon("RED");

        PlayerEntity entityToDelete = playerRepository.save(playerEntity);

        Integer id = entityToDelete.getId();
        assertThat(playerRepository.existsById(id)).isTrue();

        playerAdapter.deleteById(id);

        assertThat(playerRepository.existsById(id)).isFalse();
    }
}