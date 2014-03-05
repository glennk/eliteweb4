package com.grk.core.event;

/**
 * Created by grk on 2/18/14.
 */
public class DeleteTeamEvent {

    private String key;

    public DeleteTeamEvent(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
