package com.example.myapplication.entities;

public class ChatMessageResponse {
    private String id;
    private String created;
    private CurrentUserChat sender;
    private String content;

    public ChatMessageResponse(String id, String created, CurrentUserChat sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setSender(CurrentUserChat sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public CurrentUserChat getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

}
