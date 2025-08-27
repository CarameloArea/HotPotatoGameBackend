package org.carameloarea.hotpotatogame.application.usecase;

import lombok.AllArgsConstructor;
import org.carameloarea.hotpotatogame.application.port.driven.FindPlayerById;
import org.carameloarea.hotpotatogame.application.port.driver.FindPlayerUseCase;
import org.carameloarea.hotpotatogame.domain.model.Player;

@AllArgsConstructor
public class FindPlayerUseCaseImpl implements FindPlayerUseCase {

    private final FindPlayerById findPlayerById;

    @Override
    public Player execute(Integer id) {
        return findPlayerById.findById(id);
    }

}
