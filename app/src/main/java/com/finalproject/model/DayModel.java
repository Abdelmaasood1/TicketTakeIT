package com.finalproject.model;

import java.io.Serializable;

public class DayModel implements Serializable {
    private String id;
    private String day;
    private boolean isSelected;

    public DayModel() {
    }

    public DayModel(String day) {
        this.day = day;
    }


    public String getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
