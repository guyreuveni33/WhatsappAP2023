package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.LoginApi;
import com.example.myapplication.contacts.ContactListActivity;
import com.example.myapplication.entities.UserLogin;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    TextView signupText; // TextView for "Click here"
    LoginApi loginApi;
    ChatAPI chatAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        signupText.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        loginApi = new LoginApi(new LoginApi.LoginCallback() {

            @Override
            public void onSuccess(String token) {
                // Save the token
                String authToken = token;
                Log.d("MainActivity", "Login successful. Token: " + authToken);

                // Proceed to the next activity or perform any other action
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.putExtra("TOKEN_EXTRA", authToken);
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle login failure
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginApi.post(new UserLogin(username.getText().toString(), password.getText().toString()));
            }
        });
    }
}
