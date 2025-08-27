package org.carameloarea.hotpotatogame.application.port.driven;

import org.carameloarea.hotpotatogame.domain.model.Player;

public interface FindPlayerById {

    Player findById(Integer id);
}
