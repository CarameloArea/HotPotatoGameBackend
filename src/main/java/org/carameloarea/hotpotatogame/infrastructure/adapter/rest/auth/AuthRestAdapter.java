package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.auth;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.carameloarea.hotpotatogame.application.port.driver.LoginUseCase;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.carameloarea.hotpotatogame.infrastructure.adapter.HeaderUtil;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto.LoginRequest;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto.LoginResponse;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.mapper.PlayerRestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestAdapter {

    private final LoginUseCase loginUseCase;
    private final PlayerRestMapper playerRestMapper;

    @Value("${app.name}")
    private String applicationName;

    @PostMapping("/login")
    @Operation(summary = "Authenticate a player and return a JWT token")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        log.debug("REST request to authenticate Player : {}", loginRequest.email());
        Player player = this.playerRestMapper.toPlayer(loginRequest);
        String jwtToken = this.loginUseCase.execute(player);

        HttpHeaders headers = new HttpHeaders();

        HttpHeaders alertHeaders = HeaderUtil.createLoginSuccessAlert(
                applicationName,
                player.getEmail()
        );
        headers.addAll(alertHeaders);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        return ResponseEntity.ok().headers(headers).body(new LoginResponse(jwtToken));
    }
}
