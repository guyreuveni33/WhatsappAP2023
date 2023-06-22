package com.example.myapplication.Repository;

import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.contacts.ContactDao;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

import java.util.List;

public class MessageRepository {
    MessageDao messageDao;
    private MessageDB db;
    private ContactRepository.ContactListData contactListData;
    private ChatAPI api;

    String token;

    public MessageRepository(String token) {
        this.token = token;
        this.db = ContactDB.getDatabase();
        this.messageDao = db.messageDao();
        this.MessageListData = new MessageRepository.MessageListData();
        this.api = new ChatAPI(token);
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
                api.get(ContactRepository.this);
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void addContact(String username){
        api.postChats(ContactRepository.this,username);

    }
    public void onReload(){
        new Thread(() -> {
            api.get(ContactRepository.this);
        }).start();
    }

}
