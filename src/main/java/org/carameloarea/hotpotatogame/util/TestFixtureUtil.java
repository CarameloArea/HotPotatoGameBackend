package org.carameloarea.hotpotatogame.util;

import jakarta.transaction.Transactional;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto.RegisterPlayerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class TestFixtureUtil {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerPersistenceMapper playerPersistenceMapper;

    private static final Random RANDOM = new SecureRandom();

    @Transactional
    public PlayerEntity createPlayerEntity() {
        String random = this.getRandom();
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setNickname(String.format("Nickname%s", random));
        playerEntity.setEmail(String.format("generic.player%s@email.com", random));
        playerEntity.setPassword(random);

        return playerRepository.save(playerEntity);
    }

    @Transactional
    public Player createPlayer() {
        String random = this.getRandom();
        return Player.builder()
                .id(Integer.valueOf(random))
                .nickname(String.format("Nickname%s", random))
                .email(String.format("generic.player%s@email.com", random))
                .password(random)
                .build();
    }

    public RegisterPlayerRequest createRegisterPlayerRequest(Player player) {
        return new RegisterPlayerRequest(player.getNickname(), player.getEmail(), this.getRandom());
    }

    private String getRandom() {
        return String.valueOf(RANDOM.nextInt(10000000));
    }

}
