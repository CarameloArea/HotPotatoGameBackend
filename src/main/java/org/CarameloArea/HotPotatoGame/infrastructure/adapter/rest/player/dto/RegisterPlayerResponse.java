package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto;

public record RegisterPlayerResponse(
        Integer id,
        String nickname,
        String email
) {
}
