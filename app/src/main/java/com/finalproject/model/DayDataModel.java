package com.finalproject.model;

import java.io.Serializable;
import java.util.List;

public class DayDataModel extends StatusResponse implements Serializable {
    private List<DayModel> data;

    public List<DayModel> getData() {
        return data;
    }
}
