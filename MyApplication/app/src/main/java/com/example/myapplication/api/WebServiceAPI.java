package com.example.myapplication.api;


import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.ChatMessageResponse;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.User;
import com.example.myapplication.entities.UserLogin;
import com.example.myapplication.entities.UserResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("Tokens")
    Call<String> login(@Body UserLogin userLogin);
    @GET("Chats")
    Call <List<ContactResponse>> getChats(@Header("Authorization") String token);
    @POST("Chats")
    Call<ContactPostResponse> postChats(
            @Header("Authorization") String token,
            @Body Map<String, String> requestBody);

    @GET("Chats/{username}")
    Call<ChatByIdResponse> getChat(
            @Header("Authorization") String token,
            @Path("username") String username);

    @GET("Users/{username}")
    Call<UserResponse> getUserDetails(
            @Header("Authorization") String token,
            @Path("username") String username);

    @POST("Users")
    Call<Void> register(@Body User user);

    @POST("Chats/{id}/Messages")
    Call<ChatMessageResponse> postMessage(
            @Header("Authorization") String token,
            @Path("id") String username,@Body Map<String, String> requestBody);
}