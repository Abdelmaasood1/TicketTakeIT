package com.finalproject.model;

import java.io.Serializable;

public class HeroModel implements Serializable {
    private String name;
    private String image;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
