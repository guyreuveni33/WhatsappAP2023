package com.example.myapplication.Repository;

import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.contacts.ContactDao;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.UserResponse;

import java.util.List;

public class ContactRepository implements ChatAPI.ChatCallback{
    ContactDao contactsDao;
    private ContactDB db;
    private ContactListData contactListData;
    private ChatAPI api;

    String token;

    public ContactRepository(String token) {
        this.token = token;
        this.db = ContactDB.getDatabase();
        this.contactsDao = db.contactDao();
        this.contactListData = new ContactListData();
        this.api = new ChatAPI(token);
    }


    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            setValue(contactsDao.index());
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

//     public LiveData<String> getResponseLiveData() {
//        return api.getResponseLiveData();
//    }

    @Override
    public void onSuccess(List<ContactResponse> chats) {

    }

    @Override
    public void onUserSuccess(UserResponse userResponse) {

    }

    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse) {

    }

    @Override
    public void onPostChatSuccess(ContactPostResponse contactPostResponse) {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}