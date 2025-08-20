package org.CarameloArea.HotPotatoGame.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Player {
    private Integer id;

    @NotNull(message = "The nickname must be informed")
    private String nickname;

    @NotNull(message = "The email must be informed")
    private String email;

    @NotNull(message = "The password must be informed")
    private String password;

    private String icon;

}
