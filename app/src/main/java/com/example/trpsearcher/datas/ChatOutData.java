package com.example.trpsearcher.datas;

import org.json.JSONArray;

public class ChatOutData {
    private Integer user_id;
    private String user_login;
    private Integer user2_id;
    private String user2_login;
    private JSONArray jsonArray;
    private Boolean has_new;

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_id(){
        return user_id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public void setUser2_id(Integer user2_id) {
        this.user2_id = user2_id;
    }

    public Integer getUser2_id(){
        return user2_id;
    }

    public String getUser2_login() { return user2_login; }

    public void setUser2_login(String user2_login) { this.user2_login = user2_login; }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public Boolean getHas_new() {return has_new;}

    public void setHas_new(Boolean has_new) {
        this.has_new = has_new;
    }
}
