package com.grk.config;

import com.grk.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.grk.rest.controller.fixture.RestDataFixture.standardPlayerJSON;
import static com.grk.rest.controller.fixture.RestDataFixture.standardTeamJSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
public class RestDomainIntegrationTest {

	@Autowired
	WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void addANewEntitiesToTheSystem() throws Exception {
		this.mockMvc
				.perform(
						post("/teams").content(standardTeamJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc
				.perform(get("/teams").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
						jsonPath("$[0].id").value("ABC-DEF-XYZ"));
		
		this.mockMvc
				.perform(
						post("/players").content(standardPlayerJSON())
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc
				.perform(get("/players").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(
						jsonPath("$[0].firstname").value("rest-data-fixture"));

	}
}
