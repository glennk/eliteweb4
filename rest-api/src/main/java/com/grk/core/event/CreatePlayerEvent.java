package com.grk.core.event;

public class CreatePlayerEvent {

	private PlayerDetails playerDetails;
	
	public CreatePlayerEvent(PlayerDetails playerDetails) {
		this.playerDetails = playerDetails;
		
	}
	
	public PlayerDetails getPlayerDetails() {
		return playerDetails;
	}
}
