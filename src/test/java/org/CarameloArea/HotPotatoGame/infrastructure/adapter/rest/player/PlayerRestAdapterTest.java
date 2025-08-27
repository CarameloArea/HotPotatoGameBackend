package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.CarameloArea.HotPotatoGame.AbstractIntegrationTest;
import org.CarameloArea.HotPotatoGame.application.port.driver.CreatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.application.port.driver.DeletePlayerUseCase;
import org.CarameloArea.HotPotatoGame.application.port.driver.UpdatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.exception.EmailAlreadyUsedException;
import org.CarameloArea.HotPotatoGame.domain.exception.EntityNotFoundException;
import org.CarameloArea.HotPotatoGame.domain.exception.NicknameAlreadyUsedException;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerRequest;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.UpdatePlayerRequest;
import org.CarameloArea.HotPotatoGame.util.TestFixtureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class PlayerRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePlayerUseCase createPlayerUseCase;

    @MockBean
    private UpdatePlayerUseCase updatePlayerUseCase;

    @MockBean
    private DeletePlayerUseCase deletePlayerUseCase;

    @Autowired
    private TestFixtureUtil testFixtureUtil;

    public static final String BASE_URL = "/api/v1/players";
    private static final String JSON_PATH_ID = "$.id";
    private static final String JSON_PATH_NICKNAME = "$.nickname";
    private static final String JSON_PATH_FIRST_FIELD_ERROR = "$.fieldErrors.[0].message";
    private static final String JSON_PATH_TITLE_ERROR = "$.title";
    private static final String ENTITY_NAME = "Player";

    @Test
    @DisplayName("It should be able to register a new player in the system")
    void testCreateWithValidPayload() throws Exception {
        Player player = this.testFixtureUtil.createPlayer();
        RegisterPlayerRequest requestBody = this.testFixtureUtil.createRegisterPlayerRequest(player);

        when(createPlayerUseCase.execute(any(Player.class))).thenReturn(player);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_URL.concat("/".concat(player.getId().toString()))))
                .andExpect(jsonPath(JSON_PATH_ID, is(player.getId())))
                .andExpect(jsonPath(JSON_PATH_NICKNAME, is(player.getNickname())));

        verify(createPlayerUseCase, times(1)).execute(any(Player.class));
    }

    @Test
    @DisplayName("It should make sure to return 400 when nickname is blank")
    void testReturnBadRequestErrorWhenCreateWithInvalidPayload() throws Exception {
        Player player = this.testFixtureUtil.createPlayer();
        player.setNickname("");
        RegisterPlayerRequest invalidRequestBody = this.testFixtureUtil.createRegisterPlayerRequest(player);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_PATH_FIRST_FIELD_ERROR, is("Nickname cannot be empty")));
    }

    @Test
    @DisplayName("It should make sure to return 409 when email already exists")
    void testReturnConflictErrorWhenCreateWithAlreadyExistingEmail() throws Exception {
        Player player = this.testFixtureUtil.createPlayer();
        RegisterPlayerRequest requestBody = this.testFixtureUtil.createRegisterPlayerRequest(player);

        when(createPlayerUseCase.execute(any(Player.class))).thenThrow(new EmailAlreadyUsedException());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath(JSON_PATH_TITLE_ERROR, is("Email is already in use!")));

        verify(createPlayerUseCase, times(1)).execute(any(Player.class));
    }

    @Test
    @DisplayName("It should make sure to return 409 when nickname already exists")
    void testReturnConflictErrorWhenCreateWithAlreadyExistingNickname() throws Exception {
        Player player = this.testFixtureUtil.createPlayer();
        RegisterPlayerRequest requestBody = this.testFixtureUtil.createRegisterPlayerRequest(player);

        when(createPlayerUseCase.execute(any(Player.class))).thenThrow(new NicknameAlreadyUsedException());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath(JSON_PATH_TITLE_ERROR, is("Nickname is already in use!")));

        verify(createPlayerUseCase, times(1)).execute(any(Player.class));
    }

    @Test
    @WithMockUser
    @DisplayName("It should be able to return player")
    void testFindById() throws Exception {
        PlayerEntity playerCreated = this.testFixtureUtil.createPlayerEntity();

        mockMvc.perform(get(BASE_URL.concat("/".concat(playerCreated.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerCreated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_PATH_ID, is(playerCreated.getId())))
                .andExpect(jsonPath(JSON_PATH_NICKNAME, is(playerCreated.getNickname())));
    }

    @Test
    @WithMockUser
    @DisplayName("It should make sure to return 404 when the player does not exist")
    void testReturnNotFoundErrorWhenFindByIdWithNonExistentPlayer() throws Exception {
        PlayerEntity playerCreated = this.testFixtureUtil.createPlayerEntity();

        mockMvc.perform(get(BASE_URL.concat("/123".concat(playerCreated.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JSON_PATH_TITLE_ERROR, is("Player not found.")));
    }

    @Test
    @WithMockUser
    @DisplayName("It should be able to update a player")
    void testUpdate() throws Exception {
        PlayerEntity playerCreated = this.testFixtureUtil.createPlayerEntity();
        UpdatePlayerRequest updatePlayerRequest = new UpdatePlayerRequest("new PLayer", "newPass123", "RED");

        Player playerUpdated = Player.builder()
                .id(playerCreated.getId())
                .nickname(updatePlayerRequest.nickname())
                .password(updatePlayerRequest.password())
                .email(playerCreated.getEmail())
                .icon(updatePlayerRequest.icon())
                .build();

        when(updatePlayerUseCase.execute(any(Integer.class), any(Player.class))).thenReturn(playerUpdated);

        mockMvc.perform(put(BASE_URL.concat("/".concat(playerCreated.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePlayerRequest)))
                .andExpect(status().isOk());

        verify(updatePlayerUseCase, times(1)).execute(any(Integer.class), any(Player.class));
    }

    @Test
    @WithMockUser
    @DisplayName("It should make sure to return 404 when the player does not exist on update")
    void testReturnNotFoundErrorWhenUpdateWithNonExistentPlayer() throws Exception {
        PlayerEntity playerCreated = this.testFixtureUtil.createPlayerEntity();
        UpdatePlayerRequest updatePlayerRequest = new UpdatePlayerRequest("new PLayer", "newPass123", "RED");

        Player playerUpdated = Player.builder()
                .id(playerCreated.getId())
                .nickname(updatePlayerRequest.nickname())
                .password(updatePlayerRequest.password())
                .email(playerCreated.getEmail())
                .icon(updatePlayerRequest.icon())
                .build();

        when(updatePlayerUseCase.execute(any(Integer.class), any(Player.class))).thenThrow(new EntityNotFoundException(ENTITY_NAME));

        mockMvc.perform(put(BASE_URL.concat("/123".concat(playerCreated.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePlayerRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JSON_PATH_TITLE_ERROR, is("Player not found.")));

        verify(updatePlayerUseCase, times(1)).execute(any(Integer.class), any(Player.class));
    }

    @Test
    @WithMockUser
    @DisplayName("It should be able to delete a player")
    void testDelete() throws Exception {
        PlayerEntity playerCreated = this.testFixtureUtil.createPlayerEntity();

        doNothing().when(deletePlayerUseCase).execute(any(Integer.class));

        mockMvc.perform(delete(BASE_URL.concat("/".concat(playerCreated.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(deletePlayerUseCase, times(1)).execute(any(Integer.class));
    }

    @Test
    @WithMockUser
    @DisplayName("It should make sure to return 404 when the player does not exist on delete")
    void testReturnNotFoundErrorWhenDeleteWithNonExistentPlayer() throws Exception {
        PlayerEntity playerCreated = this.testFixtureUtil.createPlayerEntity();

        doThrow(new EntityNotFoundException(ENTITY_NAME)).when(deletePlayerUseCase).execute(any(Integer.class));

        mockMvc.perform(delete(BASE_URL.concat("/123".concat(playerCreated.getId().toString())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JSON_PATH_TITLE_ERROR, is("Player not found.")));

        verify(deletePlayerUseCase, times(1)).execute(any(Integer.class));
    }
}