package org.carameloarea.hotpotatogame.application.port.driver;

import org.carameloarea.hotpotatogame.domain.model.Player;

public interface FindPlayerUseCase {
    Player execute(Integer id);
}
