package com.example.trpsearcher.datas;


import java.util.HashMap;

public class GameSelectedData {

    private String login;
    private Integer user_id;
    private String post;
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



    public HashMap<Integer, Integer> getColors() {
        return colors;
    }

    public void setColors(HashMap<Integer, Integer> colors) {
        this.colors = colors;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
