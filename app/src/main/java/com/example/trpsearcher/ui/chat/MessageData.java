package com.example.trpsearcher.ui.chat;

import com.google.gson.JsonArray;

import java.util.HashMap;

public class MessageData {

    private String login;
    private Integer user_id;
    private String title;
    private String text;
    private HashMap<Integer, Integer> colors;



    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_id(){
        return user_id;
    }




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<Integer, Integer> getColors() {
        return colors;
    }

    public void setColors(HashMap<Integer, Integer> colors) {
        this.colors = colors;
    }
}
