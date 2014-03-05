package com.grk.core.event;

/**
 * Created by grk on 2/19/14.
 */
public class TeamUpdateEvent {

    private String id;
    private TeamDetails details;

    public TeamUpdateEvent(String id, TeamDetails details) {
        this.id = id;
        this.details = details;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TeamDetails getDetails() {
        return details;
    }

    public void setDetails(TeamDetails details) {
        this.details = details;
    }
}
