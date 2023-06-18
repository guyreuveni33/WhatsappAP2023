package com.example.myapplication.api;

import com.example.myapplication.entities.UserLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginApi {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private LoginCallback loginCallback;

    public LoginApi(LoginCallback callback) {
        this.loginCallback = callback;
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void post(UserLogin user) {
        Call<String> call = webServiceAPI.login(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    loginCallback.onSuccess(token);
                } else {
                    loginCallback.onFailure(new Throwable("Login failed"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loginCallback.onFailure(t);
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(String token);

        void onFailure(Throwable t);
    }
}
