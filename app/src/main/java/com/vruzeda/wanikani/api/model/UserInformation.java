package com.vruzeda.wanikani.api.model;

import java.io.Serializable;

public class UserInformation implements Serializable {

    private String username;
    private String gravatar;
    private int level;
    private String title;

    public UserInformation(String username, String gravatar, int level, String title) {
        this.username = username;
        this.gravatar = gravatar;
        this.level = level;
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public String getGravatar() {
        return gravatar;
    }

    public int getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }
}
