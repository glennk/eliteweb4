package com.grk.rest.controller;

import static com.grk.rest.controller.fixture.RestDataFixture.standardTeamJSON;
import static com.grk.rest.controller.fixture.RestEventFixture.teamCreated;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.grk.core.event.CreateTeamEvent;
import com.grk.core.services.TeamService;


//@Ignore
public class CreateNewTeamIntegrationTest {

	private String key = "QWERTY";
	
	MockMvc mockMvc;

	@InjectMocks
	TeamController controller;

	@Mock
	TeamService teamService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(controller).setMessageConverters(
				new MappingJackson2HttpMessageConverter()).build();

		when(teamService.createTeam(any(CreateTeamEvent.class)))
				.thenReturn(teamCreated(key));
	}

	@Test
	public void thatCreateTeamUsesHttpCreated() throws Exception {

		this.mockMvc
				.perform(
						post("/teams").content(standardTeamJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void thatCreateTeamRendersAsJson() throws Exception {

		this.mockMvc
				.perform(
						post("/teams").content(standardTeamJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(jsonPath("$.id").value(key));
	}

	@Test
	public void thatPlayerPassesLocationHeader() throws Exception {

		this.mockMvc
				.perform(
						post("/teams").content(standardTeamJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(
						header().string("Location",
								Matchers.endsWith("/teams/" + key)));
	}
}
