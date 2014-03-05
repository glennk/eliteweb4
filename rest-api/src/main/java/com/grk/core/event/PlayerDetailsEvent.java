package com.grk.core.event;

public class PlayerDetailsEvent {

	private boolean entityFound = true;
	private String id;
	private PlayerDetails details;

	public PlayerDetailsEvent(String id) {
		this.id = id;
	}

	public PlayerDetailsEvent(String id, PlayerDetails details) {
		this.id = id;
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public PlayerDetails getPlayerDetails() {
		return details;
	}

	public static PlayerDetailsEvent notFound(String id) {
		PlayerDetailsEvent evt = new PlayerDetailsEvent(id);
		evt.entityFound = false;
		return evt;
	}
}
