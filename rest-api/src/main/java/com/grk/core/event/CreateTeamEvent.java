package com.grk.core.event;

public class CreateTeamEvent {
	
	private TeamDetails teamDetails;

	public CreateTeamEvent(TeamDetails teamDetails) {
		this.teamDetails = teamDetails;
	}

	public TeamDetails getTeamDetails() {
		return teamDetails;
	}
}
