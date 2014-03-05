package com.grk.rest.controller.fixture;

import com.grk.core.event.PlayerDetails;
import com.grk.core.event.TeamDetails;
import com.grk.rest.domain.ContactInfo;

import java.util.ArrayList;
import java.util.List;

public class RestDataFixture {

    public static TeamDetails standardTeam() {
        TeamDetails t = new TeamDetails();
        t.setId("ABC");
        t.setAge("15U");
        t.setLevel("Major");
        t.setName("Austin Elite 15U");

        return t;
    }

    public static TeamDetails customTeam(String id) {
        TeamDetails t = standardTeam();
        t.setId(id);
        return t;
    }

    public static List<TeamDetails> allTeams() {
        List<TeamDetails> tl = new ArrayList<>();
        TeamDetails t1 = standardTeam();
        tl.add(t1);
        TeamDetails t2 = customTeam("ABCDEFG");
        tl.add(t2);

        return tl;
    }

    public static String standardTeamJSON() {
        return "{ \"id\": \"ABC-DEF-XYZ\", \"name\": \"Austin Elite 15U\", \"age\": \"15U\", \"level\": \"Major\" }";
    }

    public static String customTeamJSON(String id) {
        return "{ \"id\": \"" + id + "\", \"name\": \"Austin Elite 15U\", \"age\": \"15U\", \"level\": \"Major\" }";
    }

    public static String customTeamJSON(String id, String name) {
        return "{ \"id\": \"" + id + "\", \"name\": \"" + name + "\", \"age\": \"15U\", \"level\": \"Major\" }";
    }

    public static PlayerDetails standardPlayer() {

        TeamDetails t = standardTeam();

        PlayerDetails p = new PlayerDetails();
        p.setId("100");
        p.setFirstname("pFirstname");
        p.setLastname("pLastname");
//		p.setJerseyNum("5");
        p.setTeam_id(t.getId());

        ContactInfo p1 = new ContactInfo();
        p1.setName("Parent_Name");
        p1.setPhone("512-335-3211");
        p1.setEmail("gkron66@gmail.com");
        List<ContactInfo> pl = new ArrayList<>();
        pl.add(p1);
        p.setContacts(pl);

        return p;
    }

    public static PlayerDetails customPlayer(String id) {
        PlayerDetails p = standardPlayer();
        p.setId(id);
        return p;
    }

    public static List<PlayerDetails> allPlayers() {
        List<PlayerDetails> pl = new ArrayList<>();
        PlayerDetails p1 = standardPlayer();
        pl.add(p1);
        PlayerDetails p2 = standardPlayer();
        p2.setId("101");
//		p2.setJerseyNum("7");
        pl.add(p2);

        return pl;
    }

    public static String standardPlayerWithTeanIdJSON() {
        return "{ \"id\": \"109\", \"firstname\": \"rest-data-fixture\", \"lastname\": \"kronschnabl\", \"team_id\": \"ABC-DEF-XYZ\" }";
    }

    public static String standardPlayerJSON() {
        return "{ \"id\": \"109\", \"firstname\": \"rest-data-fixture\", \"lastname\": \"kronschnabl\" }";
    }
}
