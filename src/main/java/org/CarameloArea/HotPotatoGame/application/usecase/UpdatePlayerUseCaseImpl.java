package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.UpdatePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.UpdatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;

@AllArgsConstructor
public class UpdatePlayerUseCaseImpl implements UpdatePlayerUseCase {

    private final UpdatePlayer updatePlayer;

    @Override
    public Player execute(Integer id, Player player) {

        return updatePlayer.update(id, player);
    }
}
