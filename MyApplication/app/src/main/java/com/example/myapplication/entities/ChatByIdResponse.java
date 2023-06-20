package com.example.myapplication.entities;

public class ChatByIdResponse {
    private int id;
    private CurrentUserChat[] users;
    private ChatMessageResponse[] messages;

    public ChatByIdResponse(int id, CurrentUserChat[] users, ChatMessageResponse[] messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(CurrentUserChat[] users) {
        this.users = users;
    }

    public void setMessages(ChatMessageResponse[] messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public CurrentUserChat[] getUsers() {
        return users;
    }

    public ChatMessageResponse[] getMessages() {
        return messages;
    }
}
