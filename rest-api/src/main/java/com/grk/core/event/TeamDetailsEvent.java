package com.grk.core.event;

public class TeamDetailsEvent {

	private boolean entityFound = true;
	private String id;
	private TeamDetails details;

	public TeamDetailsEvent(String id) {
		this.id = id;
	}

	public TeamDetailsEvent(String id, TeamDetails details) {
		this.id = id;
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public TeamDetails getTeamDetails() {
		return details;
	}

	public static TeamDetailsEvent notFound(String id) {
		TeamDetailsEvent evt = new TeamDetailsEvent(id);
		evt.entityFound = false;
		return evt;
	}
}
