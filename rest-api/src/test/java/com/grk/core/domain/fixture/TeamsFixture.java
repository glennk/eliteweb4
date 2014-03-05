package com.grk.core.domain.fixture;

import com.grk.core.domain.Team;

public class TeamsFixture {

	public static Team standardTeam() {
		Team t = new Team();
		//t.setId("ABC");
		t.setAge("15U");
		t.setLevel("Major");
		t.setName("Austin Elite 15U");

		return t;
	}
}
