package com.finalproject.model;

import java.io.Serializable;

public class MovieDetailsDataModel extends StatusResponse implements Serializable {
    private MovieModel data;

    public MovieModel getData() {
        return data;
    }
}
