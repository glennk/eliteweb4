package com.grk.core.event;

import java.util.ArrayList;
import java.util.List;

import com.grk.rest.domain.ContactInfo;

public class PlayerDetails {

	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String jerseyNum;
	private List<ContactInfo> contacts;
	private String team_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJerseyNum() {
		return jerseyNum;
	}

	public void setJerseyNum(String jerseyNum) {
		this.jerseyNum = jerseyNum;
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfo> contacts) {
		this.contacts = contacts;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	@Override
	public String toString() {
		return "PlayerDetails [id=" + id + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", email=" + email + ", phone="
				+ phone + ", jerseyNum=" + jerseyNum + ", contacts=" + contacts
				+ ", team_id=" + team_id + "]";
	}
}
