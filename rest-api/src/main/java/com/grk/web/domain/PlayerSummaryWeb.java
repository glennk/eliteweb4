package com.grk.web.domain;

import org.springframework.beans.BeanUtils;

import com.grk.core.event.PlayerDetails;

public class PlayerSummaryWeb {

	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String jerseyNum;
	
	public PlayerSummaryWeb() { }

	public PlayerSummaryWeb(String id, String firstname, String lastname,
			String email, String phone, String jerseyNum) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.jerseyNum = jerseyNum;
	}

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

	public static PlayerSummaryWeb fromPlayerDetails(PlayerDetails playerDetails) {
		PlayerSummaryWeb psw = new PlayerSummaryWeb();
		BeanUtils.copyProperties(playerDetails, psw);
		return psw;
	}

	@Override
	public String toString() {
		return "PlayerSummaryWeb [id=" + id + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", email=" + email + ", phone="
				+ phone + ", jerseyNum=" + jerseyNum + "]";
	}

	
}
