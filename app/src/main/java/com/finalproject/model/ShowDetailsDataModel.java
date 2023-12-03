package com.finalproject.model;

import java.io.Serializable;

public class ShowDetailsDataModel extends StatusResponse implements Serializable {
    private ShowModel data;

    public ShowModel getData() {
        return data;
    }
}
