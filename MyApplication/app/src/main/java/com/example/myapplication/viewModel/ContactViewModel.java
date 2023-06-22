package com.example.myapplication.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.Contact;

public class ContactViewModel extends ViewModel {
    private MutableLiveData<Contact> contacts;

        public MutableLiveData<Contact> getContact() {
         if (contacts == null) {
             contacts = new MutableLiveData<Contact>();
            }
         return contacts;
        }

}
