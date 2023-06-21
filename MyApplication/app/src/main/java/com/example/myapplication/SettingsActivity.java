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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        displayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
        authToken = getIntent().getStringExtra("TOKEN_EXTRA");
        profilePicUrl = getIntent().getStringExtra("PROFILE_PIC_EXTRA");
        System.out.println("profile: "+ profilePicUrl);
        toggleButton = findViewById(R.id.toggleButton);
        serverAddressEditText = findViewById(R.id.serverAddressEditText);
        updateButton = findViewById(R.id.updateButton);
        btnGoBack = findViewById(R.id.btnGoBack);

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                toggleButton.setBackgroundResource(R.drawable.ic_toggleon);
                updateButton.setEnabled(true);
                setNightMode(true);
            } else {
                toggleButton.setBackgroundResource(R.drawable.ic_toggleoff);
                updateButton.setEnabled(false);
                setNightMode(false);
            }
        });

        updateButton.setOnClickListener(v -> {
            String serverAddress = serverAddressEditText.getText().toString().trim();
            if (!serverAddress.isEmpty()) {
                // Perform server update logic here
                Toast.makeText(SettingsActivity.this, "Server address updated: " + serverAddress, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Please enter a server address", Toast.LENGTH_SHORT).show();
            }
        });
        btnGoBack.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(SettingsActivity.this, ContactListActivity.class);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("DISPLAY_NAME_EXTRA", displayName);
            intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
            startActivity(intent);
        });
    }

    private void setNightMode(boolean nightModeEnabled) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int newNightMode = nightModeEnabled ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        if (currentNightMode != newNightMode) {
            AppCompatDelegate.setDefaultNightMode(newNightMode);
            recreate();
        }
    }
}
