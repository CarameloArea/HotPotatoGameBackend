package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.carameloarea.hotpotatogame.application.port.driver.CreatePlayerUseCase;
import org.carameloarea.hotpotatogame.application.port.driver.DeletePlayerUseCase;
import org.carameloarea.hotpotatogame.application.port.driver.FindPlayerUseCase;
import org.carameloarea.hotpotatogame.application.port.driver.UpdatePlayerUseCase;
import org.carameloarea.hotpotatogame.domain.model.Player;
import org.carameloarea.hotpotatogame.infrastructure.adapter.HeaderUtil;
import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.dto.*;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.player.mapper.PlayerRestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.carameloarea.hotpotatogame.util.EntityUtils.getBaseNameFromEntity;

@Slf4j
@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerRestAdapter {

    private final FindPlayerUseCase findPlayerUseCase;
    private final CreatePlayerUseCase createPlayerUseCase;
    private final UpdatePlayerUseCase updatePlayerUseCase;
    private final DeletePlayerUseCase deletePlayerUseCase;
    private final PlayerRestMapper playerRestMapper;

    @Value("${app.name}")
    private String applicationName;
    public static final String ENTITY_NAME = getBaseNameFromEntity(PlayerEntity.class.getName());

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

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a player")
    public ResponseEntity<UpdatePlayerResponse> updatePlayer(@PathVariable @Valid final Integer id,
                                                             @RequestBody @Valid final UpdatePlayerRequest updatePlayerRequest) throws URISyntaxException {
        log.debug("REST request to update Player : {}", updatePlayerRequest);
        Player player = this.playerRestMapper.toPlayer(updatePlayerRequest);
        player = this.updatePlayerUseCase.execute(id, player);

        UpdatePlayerResponse response = this.playerRestMapper.toUpdatePlayerResponse(player);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, response.id().toString()))
                .body(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a player")
    public ResponseEntity<Object> deletePlayer(@PathVariable @Valid final Integer id) throws URISyntaxException {
        this.deletePlayerUseCase.execute(id);

        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(id))).build();
    }
}
