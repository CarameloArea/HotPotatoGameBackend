package org.carameloarea.hotpotatogame.application.usecase;

import lombok.AllArgsConstructor;
import org.carameloarea.hotpotatogame.application.port.driven.DeletePlayer;
import org.carameloarea.hotpotatogame.application.port.driver.DeletePlayerUseCase;

@AllArgsConstructor
public class DeletePlayerUseCaseImpl implements DeletePlayerUseCase {

    private DeletePlayer deletePlayer;

    @Override
    public void execute(Integer id) {
        deletePlayer.deleteById(id);
    }
}
