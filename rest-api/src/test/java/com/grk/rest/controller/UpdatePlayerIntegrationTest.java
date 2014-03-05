package com.grk.rest.controller;

import com.grk.core.event.PlayerUpdateEvent;
import com.grk.core.services.PlayerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static com.grk.rest.controller.fixture.RestDataFixture.standardPlayerJSON;
import static com.grk.rest.controller.fixture.RestEventFixture.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by grk on 2/18/14.
 */
public class UpdatePlayerIntegrationTest {

    MockMvc mockMvc;

    @InjectMocks
    PlayerController controller;

    @Mock
    PlayerService playerService;

    String key = "f3512d26";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void thatUpdatePlayerUsesHttpOkOnSuccess() throws Exception {

        when(playerService.updatePlayer(any(PlayerUpdateEvent.class)))
                .thenReturn(
                        playerUpdated(key));

        this.mockMvc.perform(
                put("/players/{id}", key).content(standardPlayerJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(playerService).updatePlayer(argThat(
                Matchers.<PlayerUpdateEvent>hasProperty("id",
                        Matchers.equalTo(key))));
    }

    @Test
    public void thatUpdatePlayerUsesHttpNotFoundOnEntityLookupFailure() throws Exception {

        when(playerService.updatePlayer(any(PlayerUpdateEvent.class)))
                .thenReturn(
                        playerUpdatedNotFound(key));

        this.mockMvc.perform(
                put("/players/{id}", key).content(standardPlayerJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void thatUpdatePlayerUsesHttpForbiddenOnEntityUpdateFailure() throws Exception {

        when(playerService.updatePlayer(any(PlayerUpdateEvent.class)))
                .thenReturn(
                        playerUpdatedFailed(key));

        this.mockMvc.perform(
                put("/players/{id}", key).content(standardPlayerJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
