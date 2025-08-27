package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto;

import jakarta.validation.constraints.Size;

public record UpdatePlayerRequest(
        @Size(max = 100)
        String nickname,

        String password,

        String icon
) {
}
