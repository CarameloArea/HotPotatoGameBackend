package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.CarameloArea.HotPotatoGame.application.port.driver.CreatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.application.port.driver.FindPlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.HeaderUtil;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.DetailsPlayerResponse;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerRequest;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerResponse;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.mapper.PlayerRestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerRestAdapter {

    private final FindPlayerUseCase findPlayerUseCase;
    private final CreatePlayerUseCase createPlayerUseCase;
    private final PlayerRestMapper playerRestMapper;

    @Value("${app.name}")
    private String applicationName;
    public static final String ENTITY_NAME = PlayerEntity.class.getName();

    @GetMapping(value = "/{id}")
    @Operation(summary = "Search for a player by id")
    public ResponseEntity<DetailsPlayerResponse> getPlayer(@PathVariable @Valid final Integer id) {
        Player player = this.findPlayerUseCase.execute(id);
        DetailsPlayerResponse response = this.playerRestMapper.toDetailsPlayerResponse(player);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a player")
    public ResponseEntity<RegisterPlayerResponse> createPlayer(@RequestBody @Valid final RegisterPlayerRequest registerPlayerRequest) throws URISyntaxException {
        log.debug("REST request to save Player : {}", registerPlayerRequest);
        Player player = this.playerRestMapper.toPlayer(registerPlayerRequest);
        player = this.createPlayerUseCase.execute(player);

        RegisterPlayerResponse response = this.playerRestMapper.toRegisterPlayerResponse(player);

        return ResponseEntity
                .created(new URI("/api/v1/players/" + response.id()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, response.id().toString()))
                .body(response);
    }
}
