package org.CarameloArea.HotPotatoGame.application.port.driven;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface FindPlayer {

    Player execute(Integer id);
}
