package com.grk.core.event;

public class TeamCreatedEvent {

	private String id;
	private TeamDetails teamDetails;

	public TeamCreatedEvent(String id, TeamDetails teamDetails) {
		this.id = id;
		this.teamDetails = teamDetails;
	}

	public TeamDetails getTeamDetails() {
		return teamDetails;
	}

	public String getNewTeamKey() {
		return id;
	}

}
