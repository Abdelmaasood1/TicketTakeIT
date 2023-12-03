package com.finalproject.model;

import java.io.Serializable;

public class SeatsModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable{
        private String id;
        private String chairs_count;
        private String chairs_reserved;
        private String chairs_available;

        public String getId() {
            return id;
        }

        public String getChairs_count() {
            return chairs_count;
        }

        public String getChairs_reserved() {
            return chairs_reserved;
        }

        public String getChairs_available() {
            return chairs_available;
        }
    }
}
