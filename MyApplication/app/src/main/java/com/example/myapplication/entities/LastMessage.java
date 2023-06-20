package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class LastMessage {

    private  String id;
    private  String created;
    private String content;


    public LastMessage(String id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }



}