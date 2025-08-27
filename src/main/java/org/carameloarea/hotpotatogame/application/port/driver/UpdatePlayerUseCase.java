package org.carameloarea.hotpotatogame.application.port.driver;

import org.carameloarea.hotpotatogame.domain.model.Player;

public interface UpdatePlayerUseCase {

    Player execute(Integer id, Player player);
}
