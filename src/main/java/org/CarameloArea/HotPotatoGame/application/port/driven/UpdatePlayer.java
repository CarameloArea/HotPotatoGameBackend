package org.CarameloArea.HotPotatoGame.application.port.driven;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface UpdatePlayer {

    Player update(Integer id, Player player);
}
