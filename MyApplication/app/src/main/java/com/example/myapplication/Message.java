package com.example.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
    private String content;
    private boolean isSent;
    private Date timestamp;

    public Message(String content, boolean isSent) {
        this.content = content;
        this.isSent = isSent;
        this.timestamp = new Date();
    }

    public String getContent() {
        return content;
    }

    public boolean isSent() {
        return isSent;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(timestamp);
    }
}
