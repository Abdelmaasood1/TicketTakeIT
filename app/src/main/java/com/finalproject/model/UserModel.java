package com.finalproject.model;

import java.io.Serializable;

public class UserModel extends StatusResponse{
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String name;
        private String user_name;
        private String national_id;
        private String gender;
        private String type;
        private String email;
        private String email_verified_at;
        private String created_at;
        private String updated_at;
        private String image;

        public String getImage() {
            return image;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getNational_id() {
            return national_id;
        }

        public String getGender() {
            return gender;
        }

        public String getType() {
            return type;
        }

        public String getEmail() {
            return email;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

}
