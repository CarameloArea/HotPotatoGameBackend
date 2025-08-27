package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto;

public record UpdatePlayerResponse(
        Integer id,
        String nickname,
        String email,
        String icon
) {
}
