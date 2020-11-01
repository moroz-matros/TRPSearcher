package com.example.trpsearcher.ui.board;

public class MainData {
    private String title;
    private String text;
    private Integer id_owner;



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId_owner(){return id_owner;}

    public void setId_owner(int id_owner){this.id_owner = id_owner;};
}
