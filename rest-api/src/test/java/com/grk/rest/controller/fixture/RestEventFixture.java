package com.grk.rest.controller.fixture;

import com.grk.core.event.*;

import static com.grk.rest.controller.fixture.RestDataFixture.*;

public class RestEventFixture {

    public static AllTeamsEvent allTeamsEvent() {
        return new AllTeamsEvent(allTeams());
    }

    public static TeamCreatedEvent teamCreated(String id) {
        TeamCreatedEvent evt = new TeamCreatedEvent(id, customTeam(id));

        return evt;
    }

    public static TeamDetailsEvent teamDetailsEvent(String id) {
        return new TeamDetailsEvent(id, customTeam(id));
    }

    public static TeamDetailsEvent teamDetailsNotFound(String id) {
        return TeamDetailsEvent.notFound(id);
    }

    public static TeamDeletedEvent teamDeleted(String key) {
        return new TeamDeletedEvent(key, standardTeam());
    }

    public static TeamDeletedEvent teamDeletedFailed(String key) {
        return TeamDeletedEvent.deletionForbidden(key, standardTeam());
    }

    public static TeamDeletedEvent teamDeletedNotFound(String key) {
        return TeamDeletedEvent.notFound(key);
    }

    public static TeamUpdatedEvent teamUpdated(String key) {
        return new TeamUpdatedEvent(key, standardTeam());
    }

    public static TeamUpdatedEvent teamUpdatedFailed(String key) {
        return TeamUpdatedEvent.updateForbidden(key, standardTeam());
    }

    public static TeamUpdatedEvent teamUpdatedNotFound(String key) {
        return TeamUpdatedEvent.notFound(key);
    }


    // Player Events

    public static AllPlayersEvent allPlayersEvent() {
        return new AllPlayersEvent(allPlayers());
    }

    public static PlayerCreatedEvent playerCreated(String id) {
        PlayerCreatedEvent evt = new PlayerCreatedEvent(id, customPlayer(id));

        return evt;
    }

    public static PlayerDetailsEvent playerDetailsEvent(String id) {
        return new PlayerDetailsEvent(id, customPlayer(id));
    }

    public static PlayerDetailsEvent playerDetailsNotFound(String id) {
        return PlayerDetailsEvent.notFound(id);
    }

    public static PlayerDeletedEvent playerDeleted(String key) {
        return new PlayerDeletedEvent(key, standardPlayer());
    }

    public static PlayerDeletedEvent playerDeletedFailed(String key) {
        return PlayerDeletedEvent.deletionForbidden(key, standardPlayer());
    }

    public static PlayerDeletedEvent playerDeletedNotFound(String key) {
        return PlayerDeletedEvent.notFound(key);
    }

    public static PlayerUpdatedEvent playerUpdated(String key) {
        return new PlayerUpdatedEvent(key, standardPlayer());
    }

    public static PlayerUpdatedEvent playerUpdatedFailed(String key) {
        return PlayerUpdatedEvent.updateForbidden(key, standardPlayer());
    }

    public static PlayerUpdatedEvent playerUpdatedNotFound(String key) {
        return PlayerUpdatedEvent.notFound(key);
    }

}
