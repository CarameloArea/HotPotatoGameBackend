package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterPlayerRequest(
        @NotEmpty(message = "Nickname cannot be empty")
        @Size(max = 100)
        String nickname,

        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email must be valid")
        String email,

        @NotEmpty(message = "Password cannot be empty")
        String password
) {
}
