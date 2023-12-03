package com.finalproject.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String title;
    private String created_at;
    private String updated_at;
    private boolean isSelected = false;

    public CategoryModel(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
