package com.example.myapplication.api;


import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.User;
import com.example.myapplication.entities.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @POST("Tokens")
    Call<String> login(@Body UserLogin userLogin);
    @GET("Chats")
    Call <List<ContactResponse>> getChats(@Header("Authorization") String token);

    @POST("Users")
    Call<Void> register(@Body User user);
}