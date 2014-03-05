package com.grk.core.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;

import com.grk.core.event.PlayerDetails;
import com.grk.rest.domain.ContactInfo;

public class Player {

	@Id
	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String jerseyNum;
	private List<Parent> parents;
	// @DBRef // http://docs.mongodb.org/manual/reference/database-references/
	// private Team team;
	private String team_id;

	public Player() {
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

    public boolean canBeDeleted() {
        return true;
    }

    public boolean canBeUpdated() {
        return true;
    }

    // Relationships
    public List<Parent> getParents() {
		return parents;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

	// public Team getTeam() {
	// return team;
	// }
	//
	// public void setTeam(Team team) {
	// this.team = team;
	// }

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

    public static Player fromPlayerDetails(PlayerDetails playerDetails) {
		Player player = new Player();
		BeanUtils.copyProperties(playerDetails, player);
		// convert contacts to parents
		if (playerDetails.getContacts() != null && !playerDetails.getContacts().isEmpty()) {
			List<Parent> lp = new ArrayList<Parent>();
			for (ContactInfo c : playerDetails.getContacts()) {
				Parent p = new Parent();
				p.setName(c.getName());
				p.setEmail(c.getEmail());
				p.setPhone(c.getPhone());
				lp.add(p);
			}
			player.setParents(lp);
		}
		return player;
	}

	public PlayerDetails toPlayerDetails() {
		PlayerDetails details = new PlayerDetails();
		BeanUtils.copyProperties(this, details);
		// convert parents to contacts
		if (this.getParents() != null && !this.getParents().isEmpty()) {
			List<ContactInfo> lc = new ArrayList<ContactInfo>();
			for (Parent p : this.getParents()) {
				ContactInfo c = new ContactInfo();
				c.setName(p.getName());
				c.setEmail(p.getEmail());
				c.setPhone(p.getPhone());
				lc.add(c);
			}
			details.setContacts(lc);
		}
		return details;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", phone=" + phone
				+ ", jerseyNum=" + jerseyNum + ", parents=" + parents
				+ ", team_id=" + team_id + "]";
	}

    public void updateFromDetails(PlayerDetails details) {
        BeanUtils.copyProperties(details, this);
    }
}
