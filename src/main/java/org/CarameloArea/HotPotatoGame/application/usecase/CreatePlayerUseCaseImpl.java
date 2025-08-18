package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.SavePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.CreatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;

@AllArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {

    private final SavePlayer savePlayer;

    @Override
    public Player execute(Player player) {
        return savePlayer.execute(player);
    }
}
