package org.carameloarea.hotpotatogame.application.port.driver;

import org.carameloarea.hotpotatogame.domain.model.Player;

public interface LoginUseCase {
    String execute(Player player);
}