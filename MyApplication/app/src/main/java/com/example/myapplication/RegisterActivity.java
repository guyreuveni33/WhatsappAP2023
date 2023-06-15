package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText reenterPassword;
    EditText displayName;
    Button registerButton;
    TextView loginText;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Update the layout file here
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        reenterPassword = findViewById(R.id.ReEnterPassword);
        displayName = findViewById(R.id.displayName);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText); // Initialize TextView
        profilePicture = findViewById(R.id.profilePicture);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);

        loginText.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        uploadImageButton.setOnClickListener(view -> {
            // Open gallery to select an image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Hide the profile picture initially
        profilePicture.setVisibility(View.GONE);

        registerButton.setOnClickListener(view -> {
            // Perform validations
            if (!isUsernameValid() || !isPasswordValid() || !isReenterPasswordValid() || !isDisplayNameValid()) {
                // Show error message if any field is invalid
                Toast.makeText(RegisterActivity.this, "One or more fields are invalid/incorrect", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(RegisterActivity.this, "Successful register", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("profileImageUri", selectedImageUri);
                startActivity(intent);
            }
        });
    }

    private boolean isUsernameValid() {
        String usernameInput = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameInput)) {
            username.setError("Username is required");
            return false;
        }

        Pattern usernamePattern = Pattern.compile("^\\S+$");
        if (!usernamePattern.matcher(usernameInput).matches()) {
            username.setError("Invalid username");
            return false;
        }

        return true;
    }

    private boolean isPasswordValid() {
        String passwordInput = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordInput)) {
            password.setError("Password is required");
            return false;
        }

        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        if (!passwordPattern.matcher(passwordInput).matches()) {
            password.setError("Invalid password");
            return false;
        }

        return true;
    }

    private boolean isReenterPasswordValid() {
        String reenterPasswordInput = reenterPassword.getText().toString().trim();
        if (TextUtils.isEmpty(reenterPasswordInput)) {
            reenterPassword.setError("Re-enter password");
            return false;
        }

        if (!reenterPasswordInput.equals(password.getText().toString().trim())) {
            reenterPassword.setError("Passwords do not match");
            return false;
        }

        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        if (!passwordPattern.matcher(reenterPasswordInput).matches()) {
            reenterPassword.setError("Invalid password");
            return false;
        }

        return true;
    }

    private boolean isDisplayNameValid() {
        String displayNameInput = displayName.getText().toString().trim();
        if (TextUtils.isEmpty(displayNameInput)) {
            displayName.setError("Display name is required");
            return false;
        }

        Pattern displayNamePattern = Pattern.compile("^\\w+$");
        if (!displayNamePattern.matcher(displayNameInput).matches()) {
            displayName.setError("Invalid display name");
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profilePicture.setVisibility(View.VISIBLE);
            profilePicture.setImageURI(selectedImageUri);
        }
    }

}
