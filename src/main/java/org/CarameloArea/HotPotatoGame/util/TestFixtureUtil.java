package org.CarameloArea.HotPotatoGame.util;

import jakarta.transaction.Transactional;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestFixtureUtil {

    @Autowired
    private PlayerRepository playerRepository;


    @Transactional
    public PlayerEntity criarPlayerEntity() {
        int random = new Random().nextInt(10000000);
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setNickname("Nickname" + random);
        playerEntity.setEmail("generic.player@email.com");
        playerEntity.setPassword("12345678");

        return playerRepository.save(playerEntity);
    }

}
