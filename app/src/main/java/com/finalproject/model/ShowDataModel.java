package com.finalproject.model;

import java.io.Serializable;
import java.util.List;

public class ShowDataModel extends StatusResponse implements Serializable {

    private List<ShowModel> data;

    public List<ShowModel> getData() {
        return data;
    }
}
