package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.FindPlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;

@AllArgsConstructor
public class FindPlayerUseCaseImpl implements FindPlayerUseCase {

    private final FindPlayer findPlayer;

    @Override
    public Player execute(Integer id) {
        return findPlayer.findById(id);
    }

}
