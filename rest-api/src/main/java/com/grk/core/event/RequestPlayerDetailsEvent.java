package com.grk.core.event;

public class RequestPlayerDetailsEvent {

	private String id;

	public RequestPlayerDetailsEvent() {
	}

	public RequestPlayerDetailsEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
