package com.grk.core.event;

/**
 * Created by grk on 2/19/14.
 */
public class TeamUpdatedEvent extends UpdatedEvent {

    private String key;
    private TeamDetails details;
    private boolean updatedCompleted;

    public TeamUpdatedEvent(String key) {
        this.key = key;
    }

    public TeamUpdatedEvent(String key, TeamDetails details) {
        this.key = key;
        this.details = details;
        this.updatedCompleted = true;
    }

    public String getKey() {
        return key;
    }

    public TeamDetails getDetails() {
        return details;
    }

    public boolean isUpdateCompleted() {
        return updatedCompleted;
    }

    public static TeamUpdatedEvent updateForbidden(String key, TeamDetails details) {
        TeamUpdatedEvent ev = new TeamUpdatedEvent(key, details);
        ev.entityFound = true;
        ev.updatedCompleted = false;
        return ev;
    }

    public static TeamUpdatedEvent notFound(String key) {
        TeamUpdatedEvent ev = new TeamUpdatedEvent(key);
        ev.entityFound = false;
        return ev;
    }

}
