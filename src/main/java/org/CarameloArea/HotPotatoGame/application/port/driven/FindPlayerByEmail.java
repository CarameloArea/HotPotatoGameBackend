package org.CarameloArea.HotPotatoGame.application.port.driven;

import org.CarameloArea.HotPotatoGame.domain.model.Player;
import java.util.Optional;

public interface FindPlayerByEmail {

    Optional<Player> findByEmail(String email);
}