package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.DeletePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.DeletePlayerUseCase;

@AllArgsConstructor
public class DeletePlayerUseCaseImpl implements DeletePlayerUseCase {

    private DeletePlayer deletePlayer;

    @Override
    public void execute(Integer id) {
        deletePlayer.deleteById(id);
    }
}
