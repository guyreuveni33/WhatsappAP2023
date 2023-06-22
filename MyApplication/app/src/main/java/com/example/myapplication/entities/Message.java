package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private boolean isSent;
    private String timestamp;
    private String chatId;

    public Message(String content, boolean isSent) {
        this.content = content;
        this.isSent = isSent;
        this.timestamp = generateTimestamp();
    }

    public void setChatId(String id) {
        this.chatId = id;
    }

    public int getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date date = inputFormat.parse(timestamp);
            this.timestamp = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String generateTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
}
