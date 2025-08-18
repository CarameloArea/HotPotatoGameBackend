package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.mapper;

import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.DetailsPlayerResponse;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerRequest;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerRestMapper {

    Player toPlayer(RegisterPlayerRequest registerPlayerRequest);

    RegisterPlayerRequest toRegisterPlayerRequest(Player player);

    RegisterPlayerResponse toRegisterPlayerResponse(Player player);

    DetailsPlayerResponse toDetailsPlayerResponse(Player player);
}
