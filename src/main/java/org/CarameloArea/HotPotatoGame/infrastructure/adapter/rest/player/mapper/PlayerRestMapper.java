package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.mapper;

import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerRestMapper {

    Player toPlayer(RegisterPlayerRequest registerPlayerRequest);

    Player toPlayer(UpdatePlayerRequest updatePlayerRequest);

    RegisterPlayerRequest toRegisterPlayerRequest(Player player);

    RegisterPlayerResponse toRegisterPlayerResponse(Player player);

    UpdatePlayerResponse toUpdatePlayerResponse(Player player);

    DetailsPlayerResponse toDetailsPlayerResponse(Player player);

    Player toPlayer(LoginRequest loginRequest);
}
