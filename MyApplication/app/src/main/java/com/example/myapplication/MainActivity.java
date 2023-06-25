package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView signupText;
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("SETTING_EXTRA", loginFlag);
            startActivity(intent);
        });

        signupText.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        loginApi = new LoginApi(new LoginApi.LoginCallback() {

            @Override
            public void onSuccess(String token) {
                contactDao.nukeTable();
                String authToken = token;

                String enteredUsername = username.getText().toString();
                messageDao.nukeTable();

                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.putExtra("TOKEN_EXTRA", authToken);
                intent.putExtra("USERNAME_EXTRA", enteredUsername);
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(v ->
                loginApi.post(new UserLogin(username.getText().toString(), password.getText().toString())));
    }

}
