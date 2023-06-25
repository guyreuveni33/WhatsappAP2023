package com.example.myapplication.entities;

import java.util.List;

public class ChatByIdResponse {
    private int id;
    private List<CurrentUserChat> users;
    private List<ChatMessageResponse> messages;

    public ChatByIdResponse(int id, List<CurrentUserChat> users, List<ChatMessageResponse> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<CurrentUserChat> users) {
        this.users = users;
    }

    public void setMessages(List<ChatMessageResponse> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public List<CurrentUserChat> getUsers() {
        return users;
    }

    public List<ChatMessageResponse> getMessages() {
        return messages;
    }
}
