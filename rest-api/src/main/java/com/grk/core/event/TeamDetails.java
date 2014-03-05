package com.grk.core.event;

public class TeamDetails {

	private String id;
	private String name;
	private String age;
	private String level;

	public TeamDetails() {
	}

	public TeamDetails(String name, String age, String level) {
		super();
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
		return "TeamDetails [id=" + id + ", name=" + name + ", age=" + age
				+ ", level=" + level + "]";
	}

}
