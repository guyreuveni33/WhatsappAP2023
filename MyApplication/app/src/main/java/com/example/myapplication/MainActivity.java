package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.LoginApi;
import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.contacts.ContactDao;
import com.example.myapplication.contacts.ContactListActivity;
import com.example.myapplication.entities.UserLogin;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    ImageButton btnSettings;
    TextView signupText; // TextView for "Click here"
    LoginApi loginApi;
    ChatAPI chatAPI;
    ContactDao contactDao;
    MessageDao messageDao;
    MessageDB messageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          messageDB = MessageDB.getDatabase(getApplicationContext());
        messageDao = messageDB.messageDao();

        setContentView(R.layout.activity_main);
        ServerAddressSingleton.getInstance().setServerAddress("http://10.0.2.2:5000");
        contactDao = ContactDB.getDatabase(getApplicationContext()).contactDao();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);
        btnSettings = findViewById(R.id.btnSettings);
        String loginFlag="LOGIN";
        btnSettings.setOnClickListener(view -> {
            // Handle settings button click
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("SETTING_EXTRA", loginFlag);
            startActivity(intent);
        });

        signupText.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        loginApi = new LoginApi(new LoginApi.LoginCallback() {

            @Override
            public void onSuccess(String token) {
                contactDao.nukeTable();
                // Save the token
                String authToken = token;
                Log.d("MainActivity", "Login successful. Token: " + authToken);

                // Get the username entered by the user
                String enteredUsername = username.getText().toString();
                messageDao.nukeTable();
//                 for get all chats
//                        for each chat take chat id
//                            get all messages from chat
                // Proceed to the next activity or perform any other action
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.putExtra("TOKEN_EXTRA", authToken);
                intent.putExtra("USERNAME_EXTRA", enteredUsername);
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle login failure
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(v ->
                loginApi.post(new UserLogin(username.getText().toString(), password.getText().toString())));
    }

}
