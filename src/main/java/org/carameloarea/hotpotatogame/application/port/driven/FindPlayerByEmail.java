package org.carameloarea.hotpotatogame.application.port.driven;

import org.carameloarea.hotpotatogame.domain.model.Player;
import java.util.Optional;

public interface FindPlayerByEmail {

    Optional<Player> findByEmail(String email);
}