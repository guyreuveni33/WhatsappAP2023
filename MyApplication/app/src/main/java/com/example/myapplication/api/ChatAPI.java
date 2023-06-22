// ChatAPI.java
package com.example.myapplication.api;

import com.example.myapplication.ServerAddressSingleton;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.UserResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private String token;
    private String server;

    public interface ChatCallback {
        void onSuccess(List<ContactResponse> chats);
        void onUserSuccess(UserResponse userResponse);
        void onGetChatSuccess(ChatByIdResponse chatByIdResponse);
        void onPostChatSuccess(ContactPostResponse contactPostResponse);

        void onFailure(Throwable t);
    }

    public ChatAPI(String token) {
        this.token = token;
        server = ServerAddressSingleton.getInstance().getServerAddress()+"/api/";

        retrofit = new Retrofit.Builder()
                .baseUrl(server) // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(ChatCallback callback) {
        Call<List<ContactResponse>> call = webServiceAPI.getChats("Bearer " + token);
        call.enqueue(new Callback<List<ContactResponse>>() {
            @Override
            public void onResponse(Call<List<ContactResponse>> call, Response<List<ContactResponse>> response) {
                if (response.isSuccessful()) {
                    List<ContactResponse> contacts = response.body();
                    callback.onSuccess(contacts);
                } else {
                    callback.onFailure(new Exception("Failed to fetch contacts"));
                }
            }

            @Override
            public void onFailure(Call<List<ContactResponse>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public void postChats(ChatCallback callback, String username) {

        Call<ContactPostResponse> call = webServiceAPI.postChats("Bearer " + token,
                Map.of("username", username));
        call.enqueue(new Callback<ContactPostResponse>() {
            @Override
            public void onResponse(Call<ContactPostResponse> call, Response<ContactPostResponse> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    ContactPostResponse contactPostResponse = response.body();
                    callback.onPostChatSuccess(contactPostResponse);
                } else {
                    // Handle failure
                    callback.onFailure(new Exception("Failed to add contact"));
                }
            }

            @Override
            public void onFailure(Call<ContactPostResponse> call, Throwable t) {
                // Handle failure
                callback.onFailure(t);
            }
        });
    }


    public void getUser(ChatCallback callback,String username) {
        Call<UserResponse> call = webServiceAPI.getUserDetails("Bearer " + token, username); // Replace "username" with the actual username value
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    callback.onUserSuccess(userResponse);
                } else {
                    callback.onFailure(new Exception("Failed to fetch user details"));
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getChat(ChatCallback callback,String username) {
        Call<ChatByIdResponse> call = webServiceAPI.getChat("Bearer " + token, username); // Replace "username" with the actual username value
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaa");
        call.enqueue(new Callback<ChatByIdResponse>() {
            @Override
            public void onResponse(Call<ChatByIdResponse> call, Response<ChatByIdResponse> response) {
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                if (response.isSuccessful()) {
                    System.out.println("Successful fetch");
                    ChatByIdResponse chatByIdResponse = response.body();
                    callback.onGetChatSuccess(chatByIdResponse);
                } else {
                    callback.onFailure(new Exception("Failed to fetch chat"));
                }
            }

            @Override
            public void onFailure(Call<ChatByIdResponse> call, Throwable t) {
                callback.onFailure(t);
                // Print the failure exception for debugging
                System.out.println("Get Chat Failure: " + t.getMessage());
            }
        });
    }
}
