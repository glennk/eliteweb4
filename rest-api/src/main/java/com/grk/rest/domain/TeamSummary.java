package com.grk.rest.domain;

import org.springframework.beans.BeanUtils;

import com.grk.core.event.TeamDetails;

public class TeamSummary {

	private String id;
	private String name;
	private String age;
	private String level;

	public TeamSummary() {
	}

	public TeamSummary(String id, String name, String age, String level) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "TeamSummary [id=" + id + ", name=" + name + ", age=" + age
				+ ", level=" + level + "]";
	}

	public static TeamSummary fromTeamDetails(TeamDetails teamDetails) {
		TeamSummary summary = new TeamSummary();
		BeanUtils.copyProperties(teamDetails, summary);
		return summary;
	}

	public TeamDetails toTeamDetails() {
		TeamDetails details = new TeamDetails();
		BeanUtils.copyProperties(this, details);
		return details;
	}
}
