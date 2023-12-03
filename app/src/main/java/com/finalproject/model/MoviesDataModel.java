package com.finalproject.model;

import java.io.Serializable;
import java.util.List;

public class MoviesDataModel extends StatusResponse implements Serializable {
    private List<MovieModel> data;

    public List<MovieModel> getData() {
        return data;
    }
}

