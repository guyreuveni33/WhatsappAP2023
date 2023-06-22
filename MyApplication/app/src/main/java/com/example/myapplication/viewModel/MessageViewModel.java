package com.example.myapplication.viewModel;

import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {
    private MutableLiveData<Message> message;

    public MutableLiveData<Message> getContact() {
        if (message == null) {
            message = new MutableLiveData<Message>();
        }
        return message;
    }

}
