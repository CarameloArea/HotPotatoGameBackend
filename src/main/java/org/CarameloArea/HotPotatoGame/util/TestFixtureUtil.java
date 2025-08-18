package org.CarameloArea.HotPotatoGame.util;

import jakarta.transaction.Transactional;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestFixtureUtil {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerPersistenceMapper playerPersistenceMapper;

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
        int random = new Random().nextInt(10000000);
        return new RegisterPlayerRequest(player.getNickname(), player.getEmail(), this.getRandom());
    }

    private String getRandom() {
        return String.valueOf(new Random().nextInt(10000000));
    }

}
