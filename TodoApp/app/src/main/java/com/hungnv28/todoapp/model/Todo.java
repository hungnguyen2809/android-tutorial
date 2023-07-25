package com.hungnv28.todoapp.model;

import androidx.annotation.NonNull;

public class Todo {
    private int id;
    private String title;
    private String content;
    private String creDate;
    private String type;

    public Todo(int id, String title, String content, String creDate, String type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creDate = creDate;
        this.type = type;
    }

    public Todo(String title, String content, String creDate, String type) {
        this.title = title;
        this.content = content;
        this.creDate = creDate;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreDate() {
        return creDate;
    }

    public void setCreDate(String creDate) {
        this.creDate = creDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "Todo: " + this.title + "; " + this.content + "; " + this.creDate + "; " + this.type;
    }
}
