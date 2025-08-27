package org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.repository;

import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PlayerRepository extends  JpaRepository<PlayerEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<PlayerEntity> findByEmail(String email);
}
