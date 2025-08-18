package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.CheckPlayerByEmail;
import org.CarameloArea.HotPotatoGame.application.port.driven.CheckPlayerByNickname;
import org.CarameloArea.HotPotatoGame.application.port.driven.SavePlayer;
import org.CarameloArea.HotPotatoGame.application.port.driver.CreatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.exception.EmailAlreadyUsedException;
import org.CarameloArea.HotPotatoGame.domain.exception.NicknameAlreadyUsedException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;

@AllArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {

    private final SavePlayer savePlayer;
    private final CheckPlayerByEmail checkPlayerByEmail;
    private final CheckPlayerByNickname checkPlayerByNickname;

    @Override
    public Player execute(Player player) {
        if (this.checkPlayerByEmail.execute(player.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        if (this.checkPlayerByNickname.execute(player.getNickname())) {
            throw new NicknameAlreadyUsedException();
        }

        return savePlayer.execute(player);
    }
}
