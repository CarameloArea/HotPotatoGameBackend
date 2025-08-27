package org.CarameloArea.HotPotatoGame.application.port.driven;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface FindPlayerById {

    Player findById(Integer id);
}
