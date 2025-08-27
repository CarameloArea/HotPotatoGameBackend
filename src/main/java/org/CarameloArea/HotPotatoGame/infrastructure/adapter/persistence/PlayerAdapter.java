package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.*;
import org.CarameloArea.HotPotatoGame.domain.exception.EntityNotFoundException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper.PlayerPersistenceMapper;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository.PlayerRepository;

import java.util.Optional;

import static org.CarameloArea.HotPotatoGame.util.EntityUtils.getBaseNameFromEntity;

@RequiredArgsConstructor
public class PlayerAdapter implements SavePlayer, FindPlayerById, UpdatePlayer, DeletePlayer, FindPlayerByEmail {

    private final PlayerRepository playerRepository;

    private final PlayerPersistenceMapper playerPersistenceMapper;

    public static final String ENTITY_NAME = getBaseNameFromEntity(PlayerEntity.class.getName());

    @Override
    public Player findById(Integer id) {
        PlayerEntity playerEntity = this.playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME));
        ;
        return this.playerPersistenceMapper.toDomain(playerEntity);
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        return this.playerRepository.findByEmail(email)
                .map(this.playerPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public Player save(Player player) {
        PlayerEntity playerEntity = this.playerPersistenceMapper.toEntity(player);
        playerEntity = this.playerRepository.save(playerEntity);
        return this.playerPersistenceMapper.toDomain(playerEntity);
    }

    @Override
    @Transactional
    public Player update(Integer id, Player player) {
        PlayerEntity playerToUpdate = this.playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME));

        this.playerPersistenceMapper.toUpdateEntity(player, playerToUpdate);
        PlayerEntity updatedEntity = this.playerRepository.save(playerToUpdate);

        return this.playerPersistenceMapper.toDomain(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        PlayerEntity playerEntity = this.playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME));

        this.playerRepository.delete(playerEntity);
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
