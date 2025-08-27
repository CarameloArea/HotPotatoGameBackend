package org.carameloarea.hotpotatogame.application.usecase;

import lombok.AllArgsConstructor;
import org.carameloarea.hotpotatogame.application.port.driven.CheckPlayerByEmail;
import org.carameloarea.hotpotatogame.application.port.driven.CheckPlayerByNickname;
import org.carameloarea.hotpotatogame.application.port.driven.SavePlayer;
import org.carameloarea.hotpotatogame.application.port.driver.CreatePlayerUseCase;
import org.carameloarea.hotpotatogame.domain.exception.EmailAlreadyUsedException;
import org.carameloarea.hotpotatogame.domain.exception.NicknameAlreadyUsedException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {

    private final SavePlayer savePlayer;
    private final CheckPlayerByEmail checkPlayerByEmail;
    private final CheckPlayerByNickname checkPlayerByNickname;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Player execute(Player player) {
        if (this.checkPlayerByEmail.execute(player.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        if (this.checkPlayerByNickname.execute(player.getNickname())) {
            throw new NicknameAlreadyUsedException();
        }

        player.setPassword(passwordEncoder.encode(player.getPassword()));

        return savePlayer.save(player);
    }
}
