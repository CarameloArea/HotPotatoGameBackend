package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto;

public record UpdatePlayerResponse(
        Integer id,
        String nickname,
        String email,
        String icon
) {
}
