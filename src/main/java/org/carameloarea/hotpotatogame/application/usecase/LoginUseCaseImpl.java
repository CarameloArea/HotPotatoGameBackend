package org.carameloarea.hotpotatogame.application.usecase;

import lombok.AllArgsConstructor;
import org.carameloarea.hotpotatogame.application.port.driven.FindPlayerByEmail;
import org.carameloarea.hotpotatogame.application.port.driver.LoginUseCase;
import org.carameloarea.hotpotatogame.domain.exception.InvalidCredentialsException;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.carameloarea.hotpotatogame.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final FindPlayerByEmail findPlayerByEmail;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String execute(Player playerLoginAttempt) {
        Player player = findPlayerByEmail.findByEmail(playerLoginAttempt.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(playerLoginAttempt.getPassword(), player.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return jwtService.generateToken(player);
    }
}
