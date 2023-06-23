package com.example.myapplication.api;

import static com.example.myapplication.Converter.fromContactUserDetails;

import com.example.myapplication.ServerAddressSingleton;
import com.example.myapplication.entities.ChatMessageResponse;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.MessagesResponse;
import com.example.myapplication.messages.MessageDB;

import java.util.HashMap;
import java.util.List;
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
    private String server;

    public interface ChatCallback {
        void onSuccessPostMessage(ChatMessageResponse chatMessageResponse);
        void onSuccessGetMessage(List<MessagesResponse> messages,String chatid);
        void onFailurePostMessage(Throwable t);
        void onFailureGetMessage(Throwable t);
    }

    public MessageAPI(String token) {
        this.token = token;
        server = ServerAddressSingleton.getInstance().getServerAddress()+"/api/";

        retrofit = new Retrofit.Builder()
                .baseUrl(server) // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void postMessage(ChatCallback callback, String id, String message, List<Message> mList) {
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

                    Message newMessage = new Message(chatMessageResponse.getId(), message, true);
                    // Insert the new message into the database

                    //MessageDB.getDatabase().messageDao().insert(newMessage);
                    // Add the new message to the list and notify the adapter
                    mList.add(newMessage);



                    // Call getMessages after successful post
                    getMessages(callback, id); // Assuming getMessages method updates UI with retrieved messages
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

    public void getMessages(ChatCallback callback, String id) {
        Call<List<MessagesResponse>> call = webServiceAPI.getMessages("Bearer " + token, id);
        call.enqueue(new Callback<List<MessagesResponse>>() {
            @Override
            public void onResponse(Call<List<MessagesResponse>> call, Response<List<MessagesResponse>> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    List<MessagesResponse> messages = response.body();
                    callback.onSuccessGetMessage(messages, id);
                } else {
                    // Handle failure
                    int statusCode = response.code();
                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

                    if (statusCode == 400) {
                        // Bad request
                        callback.onFailureGetMessage(new Exception("Bad request"));
                    } else if (statusCode == 404) {
                        // Not found
                        callback.onFailureGetMessage(new Exception("Not found"));
                    } else {
                        // Other error
                        callback.onFailureGetMessage(new Exception("Failed to get messages"));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessagesResponse>> call, Throwable t) {
                // Handle failure

                callback.onFailureGetMessage(t);
            }
        });
    }



}