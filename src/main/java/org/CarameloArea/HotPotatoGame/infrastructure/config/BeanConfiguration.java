package org.CarameloArea.HotPotatoGame.infrastructure.config;

import org.CarameloArea.HotPotatoGame.application.port.driven.*;
import org.CarameloArea.HotPotatoGame.application.port.driver.*;
import org.CarameloArea.HotPotatoGame.application.usecase.*;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.PlayerAdapter;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;
import org.CarameloArea.HotPotatoGame.infrastructure.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public PlayerAdapter playerAdapter(final PlayerRepository playerRepository, final PlayerPersistenceMapper playerPersistenceMapper) {
        return new PlayerAdapter(playerRepository, playerPersistenceMapper);
    }

    @Bean
    public CreatePlayerUseCase createPlayerUseCase(final SavePlayer savePlayer, final CheckPlayerByEmail checkPlayerByEmail, final CheckPlayerByNickname checkPlayerByNickname, final PasswordEncoder passwordEncoder) {
        return new CreatePlayerUseCaseImpl(savePlayer, checkPlayerByEmail, checkPlayerByNickname, passwordEncoder);
    }

    @Bean
    public FindPlayerUseCase findPlayerUseCase(final FindPlayerById findPlayerById) {
        return new FindPlayerUseCaseImpl(findPlayerById);
    }

    @Bean
    public FindPlayerByEmail findPlayerByEmail(PlayerAdapter playerAdapter) {
        return playerAdapter::findByEmail;
    }

    @Bean
    public UpdatePlayerUseCase updatePlayerUseCase(final UpdatePlayer updatePlayer) {
        return new UpdatePlayerUseCaseImpl(updatePlayer);
    }

    @Bean
    public DeletePlayerUseCase deletePlayerUseCase(final DeletePlayer deletePlayer) {
        return new DeletePlayerUseCaseImpl(deletePlayer);
    }

    @Bean
    public CheckPlayerByEmail checkPlayerByEmail(PlayerAdapter playerAdapter) {
        return playerAdapter::existsByEmail;
    }

    @Bean
    public CheckPlayerByNickname checkPlayerByNickname(PlayerAdapter playerAdapter) {
        return playerAdapter::existsByNickname;
    }

    @Bean
    public CheckPlayerById checkPlayerById(PlayerAdapter playerAdapter) {
        return playerAdapter::existsById;
    }

    @Bean
    public LoginUseCase loginUseCase(final FindPlayerByEmail findPlayerByEmail, final PasswordEncoder passwordEncoder, final JwtService jwtService) {
        return new LoginUseCaseImpl(findPlayerByEmail, passwordEncoder, jwtService);
    }
}
