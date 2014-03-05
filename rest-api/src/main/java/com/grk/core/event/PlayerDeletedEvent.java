package com.grk.core.event;

/**
 * Created by grk on 2/18/14.
 */

public class PlayerDeletedEvent extends DeletedEvent {

    private String key;
    private PlayerDetails details;
    private boolean deletionCompleted;

    private PlayerDeletedEvent(String key) {
        this.key = key;
    }

    public PlayerDeletedEvent(String key, PlayerDetails details) {
        this.key = key;
        this.details = details;
        this.deletionCompleted = true;
    }

    public String getKey() {
        return key;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static PlayerDeletedEvent deletionForbidden(String key, PlayerDetails details) {
        PlayerDeletedEvent ev = new PlayerDeletedEvent(key, details);
        ev.entityFound = true;
        ev.deletionCompleted = false;
        return ev;
    }

    public static PlayerDeletedEvent notFound(String key) {
        PlayerDeletedEvent ev = new PlayerDeletedEvent(key);
        ev.entityFound = false;
        return ev;
    }
}
