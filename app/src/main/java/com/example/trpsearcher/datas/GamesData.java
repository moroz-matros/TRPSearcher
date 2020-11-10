package com.example.trpsearcher.datas;

import org.json.JSONArray;

public class GamesData {

    private Integer id;

    private Integer user_id;
    private String user_login;

    private Integer user2_id;
    private String user2_login;

    private String title;
    private String description;
    private JSONArray jsonArray;

    private Boolean closed;

    public Integer getId() { return id;  }

    public void setId(Integer id) {  this.id = id;  }

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

    public String getUser2_login() {
        return user2_login;
    }

    public void setUser2_login(String user2_login) {
        this.user2_login = user2_login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
