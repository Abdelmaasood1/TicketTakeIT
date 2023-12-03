package com.finalproject.model;

import java.io.Serializable;
import java.util.List;

public class SliderDataModel extends StatusResponse implements Serializable {
    private List<SliderModel> slider;

    public List<SliderModel> getSlider() {
        return slider;
    }
}
