package com.finalproject.model;

import java.io.Serializable;

public class TicketTypeModel implements Serializable {
    private String title;

    public TicketTypeModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
