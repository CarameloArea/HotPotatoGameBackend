package org.carameloarea.hotpotatogame.infrastructure.adapter.persistence;

import org.carameloarea.hotpotatogame.domain.exception.EntityNotFoundException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

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

    private static final int NON_EXISTENT_ID = 99999;
    private PlayerEntity savedPlayer;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        PlayerEntity playerEntity = new PlayerEntity()
                .nickname("existentPlayer")
                .email("player@find.com")
                .password("pass")
                .icon("RED");
        savedPlayer = playerRepository.save(playerEntity);
    }

    @Test
    @DisplayName("It should be able to register a new player in the system")
    void testSave() {
        Player playerToSave = Player.builder()
                .nickname("newPLayer")
                .email("generic.player@new.com")
                .password("Pass123456")
                .build();
        Player playerSavedResult = playerAdapter.save(playerToSave);
        assertThat(playerSavedResult).isNotNull();
        assertThat(playerSavedResult.getId()).isNotNull();
        assertThat(playerSavedResult.getNickname()).isEqualTo("newPLayer");
        assertThat(playerRepository.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("It should be able to return player by id")
    void testFindById() {
        Player playerFound = playerAdapter.findById(savedPlayer.getId());
        assertThat(playerFound).isNotNull();
        assertThat(playerFound.getId()).isEqualTo(savedPlayer.getId());
        assertThat(playerFound.getNickname()).isEqualTo(savedPlayer.getNickname());
    }

    @Test
    @DisplayName("It should throw EntityNotFoundException when player is not found by id")
    void testThrowExceptionWhenPlayerNotFoundById() {
        assertThrows(EntityNotFoundException.class, () -> playerAdapter.findById(NON_EXISTENT_ID));
    }

    @Test
    @DisplayName("It should be able to update a player")
    void testUpdate() {
        Player playerWithNewData = Player.builder()
                .nickname("newNickname")
                .email("player@update.com")
                .build();

        playerAdapter.update(savedPlayer.getId(), playerWithNewData);
        PlayerEntity updatedEntity = playerRepository.findById(savedPlayer.getId()).get();
        assertThat(updatedEntity.getNickname()).isEqualTo("newNickname");
    }

    @Test
    @DisplayName("It should be able to delete a player")
    void testDeleteById() {
        Integer id = savedPlayer.getId();
        assertThat(playerRepository.existsById(id)).isTrue();
        playerAdapter.deleteById(id);
        assertThat(playerRepository.existsById(id)).isFalse();
    }

    @Test
    @DisplayName("It should return true when player exists by email")
    void testReturnTrueWhenPlayerExistsByEmail() {
        boolean exists = playerAdapter.existsByEmail("player@find.com");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should return false when player does not exist by email")
    void testReturnFalseWhenPlayerDoesNotExistByEmail() {
        boolean exists = playerAdapter.existsByEmail("nonexistent@email.com");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("It should return true when player exists by nickname")
    void testReturnTrueWhenPlayerExistsByNickname() {
        boolean exists = playerAdapter.existsByNickname("existentPlayer");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should return false when player does not exist by nickname")
    void testReturnFalseWhenPlayerDoesNotExistByNickname() {
        boolean exists = playerAdapter.existsByNickname("nonExistentNickname");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("It should return true when player exists by ID")
    void testReturnTrueWhenPlayerExistsById() {
        boolean exists = playerAdapter.existsById(savedPlayer.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should return false when player does not exist by ID")
    void testReturnFalseWhenPlayerDoesNotExistById() {
        boolean exists = playerAdapter.existsById(NON_EXISTENT_ID);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("It should find a player by email")
    void testFindByEmail() {
        Optional<Player> playerOptional = playerAdapter.findByEmail("player@find.com");
        assertThat(playerOptional).isPresent();
        playerOptional.ifPresent(player -> {
            assertThat(player.getId()).isEqualTo(savedPlayer.getId());
            assertThat(player.getEmail()).isEqualTo("player@find.com");
        });
    }

    @Test
    @DisplayName("It should return empty when player does not exist by email")
    void testReturnEmptyWhenPlayerDoesNotExistByEmail() {
        Optional<Player> playerOptional = playerAdapter.findByEmail("nonexistent@email.com");
        assertThat(playerOptional).isNotPresent();
    }
}