package com.example.myapplication;

import androidx.room.TypeConverter;

import com.example.myapplication.entities.CurrentUserChat;
import com.example.myapplication.entities.LastMessage;
import com.example.myapplication.entities.LastMessage;
import com.example.myapplication.entities.CurrentUserChat;
import com.google.gson.Gson;

//TODO CHANGE NAMES!!!!!!!!!!!!!!!!!!!!!!!!
public class Converter {
    @TypeConverter
    public static String fromContactUserDetails(CurrentUserChat userDetails) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(userDetails);
    }
    @TypeConverter
    public static CurrentUserChat toContactUserDetails(String userDetailsJson) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(userDetailsJson, CurrentUserChat.class);
    }
    @TypeConverter
    public static String fromLastMessage(LastMessage msg) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(msg);
    }

    @TypeConverter
    public static LastMessage toLastMessage(String msg) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(msg, LastMessage.class);
    }

}