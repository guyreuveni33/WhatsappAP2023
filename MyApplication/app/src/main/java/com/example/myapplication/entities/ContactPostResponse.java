package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContactPostResponse {
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



    @PrimaryKey @NonNull
    private String id;
    private CurrentUserChat user;

    public ContactPostResponse(String id, CurrentUserChat user) {
        this.id = id;
        this.user = user;
    }

}