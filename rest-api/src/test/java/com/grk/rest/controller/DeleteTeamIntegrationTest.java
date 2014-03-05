package com.grk.rest.controller;

import com.grk.core.event.DeleteTeamEvent;
import com.grk.core.services.TeamService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static com.grk.rest.controller.fixture.RestEventFixture.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by grk on 2/18/14.
 */
public class DeleteTeamIntegrationTest {

    MockMvc mockMvc;

    @InjectMocks
    TeamController controller;

    @Mock
    TeamService teamService;

    String key = "f3512d26-72f6-4290-9265-63ad69eccc13";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void thatDeleteTeamUsesHttpOkOnSuccess() throws Exception {

        when(teamService.deleteTeam(any(DeleteTeamEvent.class)))
                .thenReturn(
                        teamDeleted(key));

        this.mockMvc.perform(
                delete("/teams/{id}", key)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(teamService).deleteTeam(argThat(
                Matchers.<DeleteTeamEvent>hasProperty("key",
                        Matchers.equalTo(key))));
    }

    @Test
    public void thatDeleteTeamUsesHttpNotFoundOnEntityLookupFailure() throws Exception {

        when(teamService.deleteTeam(any(DeleteTeamEvent.class)))
                .thenReturn(
                        teamDeletedNotFound(key));

        this.mockMvc.perform(
                delete("/teams/{id}", key)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void thatDeleteTeamUsesHttpForbiddenOnEntityDeletionFailure() throws Exception {

        when(teamService.deleteTeam(any(DeleteTeamEvent.class)))
                .thenReturn(
                        teamDeletedFailed(key));

        this.mockMvc.perform(
                delete("/teams/{id}", key)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
