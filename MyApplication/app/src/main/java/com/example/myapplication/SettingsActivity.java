package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myapplication.contacts.ContactListActivity;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat toggleButton;
    private EditText serverAddressEditText;
    private Button updateButton;
    private ImageButton btnGoBack;
    private String displayName;
    private String authToken;
    private String profilePicUrl;
    private String serverAddress;
    private String updateServerRefactor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String settingFlag = getIntent().getStringExtra("SETTING_EXTRA");
        if (settingFlag.equals("CONTACTLISTACTIVITY")) {
            displayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
            authToken = getIntent().getStringExtra("TOKEN_EXTRA");
            profilePicUrl = getIntent().getStringExtra("PROFILE_PIC_EXTRA");
        }
        toggleButton = findViewById(R.id.toggleButton);
        serverAddressEditText = findViewById(R.id.serverAddressEditText);
        updateButton = findViewById(R.id.updateButton);
        btnGoBack = findViewById(R.id.btnGoBack);
        toggleButton.setChecked(isNightModeEnabled());
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleNightMode(isChecked);
            updateButton.setEnabled(isChecked);
        });
        updateServerRefactor="";
        updateButton.setOnClickListener(v -> {
            serverAddress = serverAddressEditText.getText().toString().trim();
            if (!serverAddress.isEmpty()) {
                ServerAddressSingleton.getInstance().setServerAddress(serverAddress);
                Toast.makeText(SettingsActivity.this, "Server address updated: " + serverAddress, Toast.LENGTH_SHORT).show();
                updateServerRefactor = "done";
            } else {
                Toast.makeText(SettingsActivity.this, "Please enter a server address", Toast.LENGTH_SHORT).show();
            }
        });
        btnGoBack.setOnClickListener(view -> {
            if (settingFlag.equals("LOGIN")) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                if (updateServerRefactor.equals("done")) {
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SettingsActivity.this, ContactListActivity.class);
                    intent.putExtra("TOKEN_EXTRA", authToken);
                    intent.putExtra("DISPLAY_NAME_EXTRA", displayName);
                    intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
                    startActivity(intent);
                }
            }
        });
    }


    private void toggleNightMode(boolean nightModeEnabled) {
        int newNightMode = nightModeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(newNightMode);
        recreate();
    }


    private boolean isNightModeEnabled() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}