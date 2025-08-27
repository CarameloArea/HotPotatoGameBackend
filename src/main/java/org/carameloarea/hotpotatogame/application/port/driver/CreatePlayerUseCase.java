package org.carameloarea.hotpotatogame.application.port.driver;

import org.carameloarea.hotpotatogame.domain.model.Player;

public interface CreatePlayerUseCase {

    Player execute(Player player);
}
