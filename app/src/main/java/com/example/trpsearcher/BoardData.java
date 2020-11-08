package com.example.trpsearcher;

public class BoardData {
    private String title;
    private String text;
    private Integer owner_id;


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

    public int getOwner_id(){return owner_id;}

    public void setOwner_id(int owner_id){this.owner_id = owner_id;};
}
