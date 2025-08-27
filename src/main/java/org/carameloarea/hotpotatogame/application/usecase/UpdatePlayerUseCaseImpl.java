package org.carameloarea.hotpotatogame.application.usecase;

import lombok.AllArgsConstructor;
import org.carameloarea.hotpotatogame.application.port.driven.UpdatePlayer;
import org.carameloarea.hotpotatogame.application.port.driver.UpdatePlayerUseCase;
import org.carameloarea.hotpotatogame.domain.model.Player;

@AllArgsConstructor
public class UpdatePlayerUseCaseImpl implements UpdatePlayerUseCase {

    private final UpdatePlayer updatePlayer;

    @Override
    public Player execute(Integer id, Player player) {

        return updatePlayer.update(id, player);
    }
}
