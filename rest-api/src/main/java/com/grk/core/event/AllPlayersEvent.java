package com.grk.core.event;

import java.util.Collections;
import java.util.List;

public class AllPlayersEvent {

	private List<PlayerDetails> playerDetails;

	public AllPlayersEvent(List<PlayerDetails> playerDetails) {
		this.playerDetails = Collections.unmodifiableList(playerDetails);
	}

	public List<PlayerDetails> getPlayerDetails() {
		return playerDetails;
	}

}
