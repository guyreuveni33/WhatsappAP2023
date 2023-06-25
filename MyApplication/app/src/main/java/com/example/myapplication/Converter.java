package com.example.myapplication;

import androidx.room.TypeConverter;

import com.example.myapplication.entities.CurrentUserChat;
import com.example.myapplication.entities.LastMessage;
import com.example.myapplication.entities.Sender;
import com.google.gson.Gson;

public class Converter {
    @TypeConverter
    public static String fromContactUserDetails(Sender userDetails) {
        Gson gson = new Gson();
        return gson.toJson(userDetails);
    }
    @TypeConverter
    public static CurrentUserChat toContactUserDetails(String userDetailsJson) {
        Gson gson = new Gson();
        return gson.fromJson(userDetailsJson, CurrentUserChat.class);
    }
    @TypeConverter
    public static String fromLastMessage(LastMessage msg) {
        Gson gson = new Gson();
        return gson.toJson(msg);
    }

    @TypeConverter
    public static LastMessage toLastMessage(String msg) {
        Gson gson = new Gson();
        return gson.fromJson(msg, LastMessage.class);
    }

}