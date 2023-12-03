package com.finalproject.model;

import java.io.Serializable;
import java.util.List;

public class CinemaDataModel extends StatusResponse implements Serializable {
    private List<CinemaModel> data;

    public List<CinemaModel> getData() {
        return data;
    }
}
