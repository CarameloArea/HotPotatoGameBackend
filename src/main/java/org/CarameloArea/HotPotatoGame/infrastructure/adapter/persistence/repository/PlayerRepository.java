package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.repository;

import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlayerRepository extends  JpaRepository<PlayerEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
