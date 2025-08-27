package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayerById;
import org.CarameloArea.HotPotatoGame.application.port.driver.FindPlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;

@AllArgsConstructor
public class FindPlayerUseCaseImpl implements FindPlayerUseCase {

    private final FindPlayerById findPlayerById;

    @Override
    public Player execute(Integer id) {
        return findPlayerById.findById(id);
    }

}
