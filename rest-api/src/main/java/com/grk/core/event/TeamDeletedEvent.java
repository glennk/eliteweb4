package com.grk.core.event;

/**
 * Created by grk on 2/18/14.
 */

public class TeamDeletedEvent extends DeletedEvent {

    private String key;
    private TeamDetails details;
    private boolean deletionCompleted;

    private TeamDeletedEvent(String key) {
        this.key = key;
    }

    public TeamDeletedEvent(String key, TeamDetails details) {
        this.key = key;
        this.details = details;
        this.deletionCompleted = true;
    }

    public String getKey() {
        return key;
    }

    public TeamDetails getDetails() {
        return details;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static TeamDeletedEvent deletionForbidden(String key, TeamDetails details) {
        TeamDeletedEvent ev = new TeamDeletedEvent(key, details);
        ev.entityFound = true;
        ev.deletionCompleted = false;
        return ev;
    }

    public static TeamDeletedEvent notFound(String key) {
        TeamDeletedEvent ev = new TeamDeletedEvent(key);
        ev.entityFound = false;
        return ev;
    }
}
