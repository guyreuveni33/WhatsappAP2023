package com.example.myapplication.Repository;

import com.example.myapplication.entities.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.MessageAPI;
import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.contacts.ContactDao;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.ChatMessageResponse;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.MessagesResponse;
import com.example.myapplication.entities.UserResponse;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

import java.util.List;

public class MessageRepository implements MessageAPI.ChatCallback{
    MessageDao messageDao;
    private MessageDB db;
    private MessageListData messageListData;
    private MessageAPI api;
    private String id;

    String token;

    public MessageRepository(String token, String id) {
        this.token = token;
        this.id = id;
        this.db = MessageDB.getDatabase();
        this.messageDao = db.messageDao();
        this.messageListData = new MessageRepository.MessageListData();
        this.api = new MessageAPI(token);
    }



    class MessageListData extends MutableLiveData<List<Message>> {

        public MessageListData() {
            super();
            setValue(messageDao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                api.getMessages(MessageRepository.this, id);
            }).start();
        }
    }

    public LiveData<List<Message>> getAll() {
        return messageListData;
    }

    public void addMessage(String message){ //message == content
        //api.postMessage(MessageRepository.this,id, message);

    }
    public void onReload(){
        new Thread(() -> {
            api.getMessages(MessageRepository.this, id);
        }).start();
    }
    @Override
    public void onSuccessPostMessage(ChatMessageResponse chatMessageResponse) {

    }

    @Override
    public void onSuccessGetMessage(List<MessagesResponse> messages,String chatId) {

    }

    @Override
    public void onFailurePostMessage(Throwable t) {

    }

    @Override
    public void onFailureGetMessage(Throwable t) {

    }
}