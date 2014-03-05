package com.grk.rest.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by grk on 2/27/14.
 */
public class UserTransfer {

    private String username;
    private String token;
    private Map<String, Boolean> roles;

    public Map<String, Boolean> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Boolean> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
