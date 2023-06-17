package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Locale;
@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String lastMessage;
    private String lastDate;

    public Contact(int id, String name, String lastMessage, String lastDate) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
        this.lastDate = lastDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}
