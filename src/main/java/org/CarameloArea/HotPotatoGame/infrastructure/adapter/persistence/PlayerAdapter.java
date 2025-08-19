package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayer;
import org.CarameloArea.HotPotatoGame.application.port.driven.SavePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driven.UpdatePlayer;
import org.CarameloArea.HotPotatoGame.domain.exception.EntityNotFoundException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;

@RequiredArgsConstructor
public class PlayerAdapter implements SavePlayer, FindPlayer, UpdatePlayer {

    private final PlayerRepository playerRepository;
    private final PlayerPersistenceMapper playerPersistenceMapper;

    public static final String ENTITY_NAME = "Player";

    @Override
    public Player execute(Integer id) {
        PlayerEntity playerEntity = this.playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME));
        ;
        return this.playerPersistenceMapper.toDomain(playerEntity);
    }

    @Override
    public Player execute(Player player) {
        PlayerEntity playerEntity = this.playerPersistenceMapper.toEntity(player);
        playerEntity = this.playerRepository.save(playerEntity);
        return this.playerPersistenceMapper.toDomain(playerEntity);
    }

    @Override
    public Player execute(Integer id, Player player) {
        PlayerEntity playerToUpdate = this.playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME));

        this.playerPersistenceMapper.toUpdateEntity(player, playerToUpdate);
        PlayerEntity updatedEntity = this.playerRepository.save(playerToUpdate);

        return this.playerPersistenceMapper.toDomain(updatedEntity);
    }

    public boolean existsByEmail(String email) {
        return this.playerRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return this.playerRepository.existsByNickname(nickname);
    }

    public boolean existsById(Integer id) {
        return this.playerRepository.existsById(id);
    }

}
