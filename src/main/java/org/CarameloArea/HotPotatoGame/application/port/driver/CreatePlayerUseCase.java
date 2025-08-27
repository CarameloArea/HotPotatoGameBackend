package org.CarameloArea.HotPotatoGame.application.port.driver;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface CreatePlayerUseCase {

    Player execute(Player player);
}
