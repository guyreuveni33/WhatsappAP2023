package com.example.myapplication.api;

import android.util.Log;

import com.example.myapplication.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterApi {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private RegisterCallback registerCallback;

    public RegisterApi(RegisterCallback callback) {
        this.registerCallback = callback;
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void post(User user) {
        Call<Void> call = webServiceAPI.register(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("RegisterApi", "response: " + response.body());
                if (response.isSuccessful()) {
                    registerCallback.onSuccess();
                } else {
                    registerCallback.onFailure(new Throwable("Registration failed"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerCallback.onFailure(t);
            }
        });
    }

    public interface RegisterCallback {
        void onSuccess();

        void onFailure(Throwable t);
    }
}
