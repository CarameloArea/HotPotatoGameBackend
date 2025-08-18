package org.CarameloArea.HotPotatoGame.infrastructure.config;

import org.CarameloArea.HotPotatoGame.application.port.driven.CheckPlayerByEmail;
import org.CarameloArea.HotPotatoGame.application.port.driven.CheckPlayerByNickname;
import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayer;
import org.CarameloArea.HotPotatoGame.application.port.driven.SavePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.CreatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.application.port.driver.FindPlayerUseCase;
import org.CarameloArea.HotPotatoGame.application.usecase.CreatePlayerUseCaseImpl;
import org.CarameloArea.HotPotatoGame.application.usecase.FindPlayerUseCaseImpl;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.PlayerAdapter;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PlayerAdapter playerAdapter(final PlayerRepository playerRepository, final PlayerPersistenceMapper playerPersistenceMapper) {
        return new PlayerAdapter(playerRepository, playerPersistenceMapper);
    }

    @Bean
    public CreatePlayerUseCase createPlayerUseCase(final SavePlayer savePlayer, final CheckPlayerByEmail checkPlayerByEmail, final CheckPlayerByNickname checkPlayerByNickname) {
        return new CreatePlayerUseCaseImpl(savePlayer, checkPlayerByEmail, checkPlayerByNickname);
    }

    @Bean
    public FindPlayerUseCase findPlayerUseCase(final FindPlayer findPlayerPort) {
        return new FindPlayerUseCaseImpl(findPlayerPort);
    }

    @Bean
    public CheckPlayerByEmail checkPlayerByEmail(PlayerAdapter playerAdapter) {
        return playerAdapter::existsByEmail;
    }

    @Bean
    public CheckPlayerByNickname checkPlayerByNickname(PlayerAdapter playerAdapter) {
        return playerAdapter::existsByNickname;
    }
}
