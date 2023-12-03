package com.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieModel implements Serializable {

    private String id;
    private String title;
    private String rate;
    private String video;
    private String image;
    private CategoryModel category;
    private String price;
    private String details;
    private String count_hours;
    private List<HeroModel> heroes;
    private String have_or_not;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getRate() {
        return rate;
    }

    public String getVideo() {
        return video;
    }

    public String getImage() {
        return image;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public List<HeroModel> getHeroes() {
        return heroes;
    }

    public String getCount_hours() {
        return count_hours;
    }

    public String getHave_or_not() {
        return have_or_not;
    }

    public void setHave_or_not(String have_or_not) {
        this.have_or_not = have_or_not;
    }
}
