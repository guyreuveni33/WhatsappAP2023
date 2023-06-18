// ChatAPI.java
package com.example.myapplication.api;

import com.example.myapplication.entities.ContactResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private String token;

    public interface ChatCallback {
        void onSuccess(List<ContactResponse> chats);

        void onFailure(Throwable t);
    }

    public ChatAPI(String token) {
        this.token = token;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/") // Replace with your API base URL
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
}
