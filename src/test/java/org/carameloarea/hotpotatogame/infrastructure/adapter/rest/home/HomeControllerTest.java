package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.home;

import org.carameloarea.hotpotatogame.infrastructure.config.SecurityConfiguration;
import org.carameloarea.hotpotatogame.infrastructure.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@Import(SecurityConfiguration.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.repository.PlayerRepository playerRepository;

    @Test
    @DisplayName("It should redirect from root path '/' to Swagger UI")
    void testRedirectToSwagger() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }

    @Test
    @DisplayName("It should redirect from '/redoc' to ReDoc UI")
    void testRedirectToRedoc() throws Exception {
        mockMvc.perform(get("/redoc"))
                .andExpect(status().isOk());
    }
}