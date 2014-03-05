package com.grk.core.event;

/**
 * Created by grk on 2/18/14.
 */
public class DeletePlayerEvent {

    private String key;

    public DeletePlayerEvent(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
