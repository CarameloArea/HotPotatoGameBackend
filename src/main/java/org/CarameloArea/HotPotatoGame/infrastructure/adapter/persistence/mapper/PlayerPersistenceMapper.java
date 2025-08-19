package org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.mapper;

import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PlayerPersistenceMapper {

    Player toDomain(PlayerEntity entity);

    PlayerEntity toEntity(Player player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateEntity(Player domain, @MappingTarget PlayerEntity entity);

}
