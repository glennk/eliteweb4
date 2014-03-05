package com.grk.core.event;

public class RequestTeamDetailsEvent {

	private String id;

	public RequestTeamDetailsEvent() {
	}

	public RequestTeamDetailsEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
