package com.grk.web.controller.fixture;

import static com.grk.rest.controller.fixture.RestEventFixture.allPlayersEvent;

import com.grk.core.event.AllPlayersEvent;

public class WebDataFixture {

	public static AllPlayersEvent allPlayerDetails() {
		return allPlayersEvent();
	}

}
