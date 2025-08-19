package org.CarameloArea.HotPotatoGame.application.port.driven;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface UpdatePlayer {

    Player execute(Integer id, Player player);
}
