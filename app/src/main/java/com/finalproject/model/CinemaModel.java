package com.finalproject.model;

import java.io.Serializable;

public class CinemaModel implements Serializable {
    private String id;
    private String title;
    private String location;
    private String user_id;
    private String chairs_count;
    private String created_at;
    private String updated_at;
    private UserModel owner;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getChairs_count() {
        return chairs_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserModel getOwner() {
        return owner;
    }
}
