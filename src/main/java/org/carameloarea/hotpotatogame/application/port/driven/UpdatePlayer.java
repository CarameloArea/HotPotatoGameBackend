package org.carameloarea.hotpotatogame.application.port.driven;

import org.carameloarea.hotpotatogame.domain.model.Player;

public interface UpdatePlayer {

    Player update(Integer id, Player player);
}
