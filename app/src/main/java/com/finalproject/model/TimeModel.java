package com.finalproject.model;

import java.io.Serializable;

public class TimeModel implements Serializable {
    public String id;
    public String hour;
    private boolean isSelected;


    public TimeModel() {
    }

    public TimeModel(String hour) {
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public String getHour() {
        return hour;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
