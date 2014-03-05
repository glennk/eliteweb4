package com.grk.core.event;

/**
 * Created by grk on 2/19/14.
 */
public class PlayerUpdateEvent {

    private String id;
    private PlayerDetails details;

    public PlayerUpdateEvent(String id, PlayerDetails details) {
        this.id = id;
        this.details = details;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public void setDetails(PlayerDetails details) {
        this.details = details;
    }
}
