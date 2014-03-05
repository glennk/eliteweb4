package com.grk.rest.controller;

import static com.grk.rest.controller.fixture.RestDataFixture.standardPlayerJSON;
import static com.grk.rest.controller.fixture.RestEventFixture.playerCreated;
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

import com.grk.core.event.CreatePlayerEvent;
import com.grk.core.services.PlayerService;

//@Ignore
public class CreateNewPlayerIntegrationTest {

	MockMvc mockMvc;

	@InjectMocks
	PlayerController controller;

	@Mock
	PlayerService playerService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(controller).setMessageConverters(
				new MappingJackson2HttpMessageConverter()).build();

		when(playerService.createPlayer(any(CreatePlayerEvent.class)))
				.thenReturn(playerCreated("109"));
	}

	@Test
	public void thatCreatePlayerUsesHttpCreated() throws Exception {

		System.out.println("standardPlayerJSON(): " + standardPlayerJSON());
		this.mockMvc
				.perform(
						post("/players").content(standardPlayerJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void thatCreatePlayerRendersAsJson() throws Exception {

		this.mockMvc
				.perform(
						post("/players").content(standardPlayerJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(jsonPath("$.id").value("109"));
	}

	@Test
	public void thatPlayerPassesLocationHeader() throws Exception {

		this.mockMvc
				.perform(
						post("/players").content(standardPlayerJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(
						header().string("Location",
								Matchers.endsWith("/players/109")));
	}
}
