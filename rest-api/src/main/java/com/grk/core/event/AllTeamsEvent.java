package com.grk.core.event;

import java.util.Collections;
import java.util.List;

public class AllTeamsEvent {
	
	private List<TeamDetails> teamDetails;
	
	public AllTeamsEvent(List<TeamDetails> teamDetails) {
		this.teamDetails = Collections.unmodifiableList(teamDetails);
	}

	public List<TeamDetails> getTeamDetails() {
		return teamDetails;
	}
}
