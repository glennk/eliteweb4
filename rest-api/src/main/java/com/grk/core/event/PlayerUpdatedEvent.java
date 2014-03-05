package com.grk.core.event;

/**
 * Created by grk on 2/19/14.
 */
public class PlayerUpdatedEvent extends UpdatedEvent {

    private String key;
    private PlayerDetails details;
    private boolean updatedCompleted;

    public PlayerUpdatedEvent(String key) {
        this.key = key;
    }

    public PlayerUpdatedEvent(String key, PlayerDetails details) {
        this.key = key;
        this.details = details;
        this.updatedCompleted = true;
    }

    public String getKey() {
        return key;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public boolean isUpdateCompleted() {
        return updatedCompleted;
    }

    public static PlayerUpdatedEvent updateForbidden(String key, PlayerDetails details) {
        PlayerUpdatedEvent ev = new PlayerUpdatedEvent(key, details);
        ev.entityFound = true;
        ev.updatedCompleted = false;
        return ev;
    }

    public static PlayerUpdatedEvent notFound(String key) {
        PlayerUpdatedEvent ev = new PlayerUpdatedEvent(key);
        ev.entityFound = false;
        return ev;
    }

}
