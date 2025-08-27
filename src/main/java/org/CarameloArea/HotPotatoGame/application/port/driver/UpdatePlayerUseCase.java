package org.CarameloArea.HotPotatoGame.application.port.driver;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface UpdatePlayerUseCase {

    Player execute(Integer id, Player player);
}
