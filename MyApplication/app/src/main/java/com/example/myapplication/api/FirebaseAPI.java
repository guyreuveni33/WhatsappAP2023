package com.example.myapplication.api;

import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public static String token;

    public FirebaseAPI(String Ctoken, String server) {
        token = Ctoken;
        if (server.startsWith("localhost")) server = server.replace("localhost", "10.0.2.2");
        server = "http://" + server;
        retrofit = new Retrofit.Builder()
                .baseUrl(server + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }


    public void post(String firebaseToken) {
        Call<Void> call = webServiceAPI.firebaseOnConnect("Bearer " + token, firebaseToken);
        call.enqueue(new Callback<Void>() {
            Boolean returnVal;

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.i("success22", "success post message");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("fail22", "fail post message");
            }
        });
    }
}