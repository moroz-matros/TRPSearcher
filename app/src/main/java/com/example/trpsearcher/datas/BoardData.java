package com.example.trpsearcher.datas;

public class BoardData {
    private Integer id;
    private String title;
    private String text;
    private Integer user2_id;

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

    public Integer getUser2_id() { return user2_id; }

    public void setUser2_id(Integer user2_id) { this.user2_id = user2_id; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}
