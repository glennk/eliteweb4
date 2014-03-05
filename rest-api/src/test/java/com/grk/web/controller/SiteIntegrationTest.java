package com.grk.web.controller;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.grk.core.event.RequestAllPlayersEvent;
import com.grk.core.services.PlayerService;
import com.grk.web.controller.fixture.WebDataFixture;
import com.grk.web.domain.PlayerListModel;

public class SiteIntegrationTest {

	private static final String VIEW = "home";
	private static final String FORWARDED_URL = "classpath:/templates/home.html";

	MockMvc mockMvc;

	@InjectMocks
	SiteController controller;

	@Mock
	PlayerService playerService;

	@Mock
	PlayerListModel playerListModel;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockMvc = standaloneSetup(controller).setViewResolvers(viewResolver())
				.build();

		when(playerService.getAllPlayers(any(RequestAllPlayersEvent.class)))
				.thenReturn(WebDataFixture.allPlayerDetails());
	}

	private InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("classpath:/templates/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void rootUrlPopulatesViewModel() throws Exception {
		System.out.println("rootUrlPopulatesViewModel()");
		mockMvc.perform(get("/")).andDo(print())
				.andExpect(model().attributeExists("playerList"))
		.andExpect(model().attribute("playerList", hasSize(2)))
		.andExpect(model().attribute("playerList", hasItems(hasProperty("lastname", is("pLastname")),
												   			hasProperty("firstname", is("pFirstname")))
									)
				  );
	}

	@Test
	public void rootUrlforwardsCorrectly() throws Exception {
		System.out.println("rootUrlforwardsCorrectly()");
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name(VIEW))
				.andExpect(forwardedUrl(FORWARDED_URL));

	}

	// @Test
	// public void thatTextReturned() throws Exception {
	// mockMvc.perform(get("/")).andDo(print())
	// .andExpect(content().string(RESPONSE_BOOY));
	//
	// }
}
