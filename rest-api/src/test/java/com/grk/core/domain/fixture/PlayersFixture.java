package com.grk.core.domain.fixture;

import java.util.ArrayList;
import java.util.List;

import com.grk.core.domain.Parent;
import com.grk.core.domain.Player;

public class PlayersFixture {

	public static Player standardPlayer() {
		
		Player p = new Player();
		//p.setId("100");
		p.setFirstname("pFirstname");
		p.setLastname("pLastname");
		p.setEmail("pEmail");
		p.setPhone("555-666-1234");
		p.setJerseyNum("5");
		
		Parent p1 = new Parent();
		p1.setName("Parent_Name1");
		p1.setPhone("phone1");
		p1.setEmail("parent1@gmail.com");

		Parent p2 = new Parent();
		p1.setName("Parent_Name2");
		p1.setPhone("phone2");
		p1.setEmail("parent2@gmail.com");
		List<Parent> pl = new ArrayList<Parent>();
		pl.add(p1);
		pl.add(p2);
		p.setParents(pl);
		
		return p;
	}
	
	public static Player standardPlayerWithTeam() {
		
		Player p = standardPlayer();
		p.setTeam_id(TeamsFixture.standardTeam().getId());
		
		return p;
	}
	
	public static Player standardPlayerNotFound() {
		
		return null;
	}

	public static List<Player> standardListOfPlayers() {
		Player p1 = standardPlayer();
		Player p2 = standardPlayer();
		p2.setJerseyNum("55");
		
		List<Player> pl = new ArrayList<Player>();;
		pl.add(p1);
		pl.add(p2);
		
		return pl;
	}
}
