package org.CarameloArea.HotPotatoGame.application.usecase;

import lombok.AllArgsConstructor;
import org.CarameloArea.HotPotatoGame.application.port.driven.FindPlayerByEmail;
import org.CarameloArea.HotPotatoGame.application.port.driver.LoginUseCase;
import org.CarameloArea.HotPotatoGame.domain.exception.InvalidCredentialsException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.security.JwtService;
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
