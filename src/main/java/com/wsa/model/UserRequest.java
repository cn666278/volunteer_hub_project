package com.wsa.model;

import java.io.Serializable;

public class UserRequest implements Serializable {
    private static final long serialVersionUID = 6710686126803728786L;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}