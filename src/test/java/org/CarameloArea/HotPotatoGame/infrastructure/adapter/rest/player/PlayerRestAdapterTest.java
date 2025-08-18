package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.CarameloArea.HotPotatoGame.AbstractIntegrationTest;
import org.CarameloArea.HotPotatoGame.application.port.driver.CreatePlayerUseCase;
import org.CarameloArea.HotPotatoGame.domain.model.Player;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.player.dto.RegisterPlayerRequest;
import org.CarameloArea.HotPotatoGame.util.TestFixtureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class PlayerRestAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePlayerUseCase createPlayerUseCase;

    @Autowired
    private TestFixtureUtil testFixtureUtil;

    public static final String BASE_URL = "/api/v1/players";


    @Test
    @DisplayName("It should be able to register a new player in the system")
    void testCreateWithValidPayload() throws Exception {
        RegisterPlayerRequest requestBody = new RegisterPlayerRequest("Generic PLayer", "generic.player@example.com", "12345678");

        Player playerCreated = new Player(1, "Generic PLayer", "generic.player@example.com", "12345678", null);

        when(createPlayerUseCase.execute(any(Player.class))).thenReturn(playerCreated);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_URL.concat("/1")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nickname", is("Generic PLayer")));
    }

    @Test
    @DisplayName("It should make sure to inform the user an error when nickname is blank")
    void testReturnBadRequestErrorWhenCreateWithInvalidPayload() throws Exception {
        RegisterPlayerRequest invalidRequestBody = new RegisterPlayerRequest("", "generic.player@example.com", "12345678");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.[0].message", is("Nickname cannot be empty")));
    }

    @Test
    @DisplayName("It should be able to return player")
    void testFindById() throws Exception {
        Player playerCreated = new Player(1, "Generic PLayer", "generic.player@example.com", "12345678", null);

        when(createPlayerUseCase.execute(any(Player.class))).thenReturn(playerCreated);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerCreated)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_URL.concat("/1")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nickname", is("Generic PLayer")));
    }

}