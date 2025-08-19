package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayer;
import org.CarameloArea.HotPotatoGame.application.port.driven.UpdatePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.UpdatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
public class UpdatePlayerUseCaseImpl implements UpdatePlayerUseCase {

    private final UpdatePlayer updatePlayer;
    private final FindPlayer findPlayer;

    @Override
    public Player execute(Integer id, Player player) {
        Player playerToUpdate = this.findPlayer.execute(id);
        BeanUtils.copyProperties(player, playerToUpdate);

        return updatePlayer.execute(id, playerToUpdate);
    }
}
