package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper;

import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerPersistenceMapper {

    Player toDomain(PlayerEntity entity);

    PlayerEntity toEntity(Player player);
}
