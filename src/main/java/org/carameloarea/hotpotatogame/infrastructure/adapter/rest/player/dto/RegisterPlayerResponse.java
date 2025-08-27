package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto;

public record RegisterPlayerResponse(
        Integer id,
        String nickname,
        String email
) {
}
