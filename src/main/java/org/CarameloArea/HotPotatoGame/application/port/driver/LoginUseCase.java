package org.CarameloArea.HotPotatoGame.application.port.driver;

import org.CarameloArea.HotPotatoGame.domain.model.Player;

public interface LoginUseCase {
    String execute(Player player);
}