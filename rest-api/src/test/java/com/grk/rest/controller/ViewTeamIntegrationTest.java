package com.grk.rest.controller;

import static com.grk.rest.controller.fixture.RestEventFixture.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.grk.core.event.RequestAllTeamsEvent;
import com.grk.core.event.RequestTeamDetailsEvent;
import com.grk.core.services.TeamService;

//@Ignore
public class ViewTeamIntegrationTest {

	private String key = "ABC";
	
	MockMvc mockMvc;

	@InjectMocks
	TeamController controller;

	@Mock
	TeamService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(controller).setMessageConverters(
				new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	public void getAllTeams() throws Exception {

		when(service.getAllTeams(any(RequestAllTeamsEvent.class))).thenReturn(allTeamsEvent());

		this.mockMvc
				.perform(get("/teams").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(key))
				.andExpect(jsonPath("$[1].id").value("ABCDEFG"))
				.andExpect(jsonPath("$[0].level").value("Major"));
	}

	@Test
	public void getTeam() throws Exception {

		when(service.getTeam(any(RequestTeamDetailsEvent.class))).thenReturn(teamDetailsEvent(key));

		this.mockMvc
				.perform(get("/teams/" + key).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(key))
				.andExpect(jsonPath("$.age").value("15U"));
	}

	@Test
	public void getTeamNotFound() throws Exception {

		when(service.getTeam(any(RequestTeamDetailsEvent.class))).thenReturn(teamDetailsNotFound(key));

		this.mockMvc
				.perform(get("/teams/XYZ").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());
	}
}
