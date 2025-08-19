package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto;

import jakarta.validation.constraints.Size;

public record UpdatePlayerRequest(
        @Size(max = 100)
        String nickname,

        String password,

        String icon
) {
}
