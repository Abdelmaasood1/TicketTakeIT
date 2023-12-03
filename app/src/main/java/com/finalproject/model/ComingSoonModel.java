package com.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ComingSoonModel implements Serializable {
    private String id;
    private String title;
    private String image;
    private String coming_date;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getComing_date() {
        return coming_date;
    }

}
