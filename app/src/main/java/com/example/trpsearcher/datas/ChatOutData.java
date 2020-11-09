package com.example.trpsearcher.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

public class ChatOutData {
        private String user;
        private String user_login;
        private Boolean has_new;
        private Integer user_id;
        private Integer user2_id;
        private JSONArray jsonArray;

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_id(){
        return user_id;
    }

    public void setUser2_id(Integer user2_id) {
        this.user2_id = user2_id;
    }

    public Integer getUser2_id(){
        return user2_id;
    }


    public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public Boolean getHas_new() {return has_new;}


    public void setHas_new(Boolean has_new) {
        this.has_new = has_new;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }
}
