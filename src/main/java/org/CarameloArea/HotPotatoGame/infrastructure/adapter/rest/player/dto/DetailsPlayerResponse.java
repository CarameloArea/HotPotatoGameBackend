package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto;

public record DetailsPlayerResponse(
        Integer id,
        String nickname,
        String email,
        String icon
) {
}
