package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContactResponse {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CurrentUserChat getUser() {
        return user;
    }

    public void setUser(CurrentUserChat user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @PrimaryKey @NonNull
    private String id;
    private CurrentUserChat user;
    private LastMessage lastMessage;

    public ContactResponse(String id, CurrentUserChat user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

}