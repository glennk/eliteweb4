package com.grk.core.event;

public class PlayerCreatedEvent {

	private String id;
	private PlayerDetails playerDetails;
	
	public PlayerCreatedEvent(String id, PlayerDetails playerDetails) {
		this.id = id;
		this.playerDetails = playerDetails;
	}

	public PlayerDetails getPlayerDetails() {
		return playerDetails;
	}

	public String getNewPlayerKey() {
		return id;
	}

}
