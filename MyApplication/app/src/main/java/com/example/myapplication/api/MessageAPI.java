package com.example.myapplication.api;

import com.example.myapplication.entities.ChatMessageResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {

    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private String token;

    public interface ChatCallback {
        void onSuccessPostMessage(ChatMessageResponse chatMessageResponse);
        void onFailurePostMessage(Throwable t);
    }

    public MessageAPI(String token) {
        this.token = token;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void postMessage(ChatCallback callback, String id, String message) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("msg", message);

        Call<ChatMessageResponse> call = webServiceAPI.postMessage("Bearer " + token, id, requestBody);
        call.enqueue(new Callback<ChatMessageResponse>() {
            @Override
            public void onResponse(Call<ChatMessageResponse> call, Response<ChatMessageResponse> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    System.out.println("Successful fetch");

                    ChatMessageResponse chatMessageResponse = response.body();
                    callback.onSuccessPostMessage(chatMessageResponse);
                } else {
                    // Handle failure
                    System.out.println("unsuccessful fetch");

                    callback.onFailurePostMessage(new Exception("Failed to send message"));
                }
            }

            @Override
            public void onFailure(Call<ChatMessageResponse> call, Throwable t) {
                // Handle failure
                callback.onFailurePostMessage(t);
            }
        });
    }

}