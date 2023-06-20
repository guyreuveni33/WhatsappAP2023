package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.User;
import com.example.myapplication.entities.UserResponse;

import java.util.List;
import java.util.Random;

public class AddContactActivity extends AppCompatActivity  {

    private ImageButton btnGoBack;
    private Button btnAddContact;
    private ContactDao contactDao;
    private ContactDB contactDB;
    private String displayName;
    private String username;
    private String authToken;
    private List<Contact> conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
        authToken = getIntent().getStringExtra("TOKEN_EXTRA");
        username = getIntent().getStringExtra("USERNAME_EXTRA");
        setContentView(R.layout.activity_add_contact);
        this.contactDB = ContactDB.getDatabase(getApplicationContext());
        contactDao = contactDB.contactDao();
        btnGoBack = findViewById(R.id.btnGoBack);
        btnAddContact = findViewById(R.id.addContactButton);
        btnGoBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddContactActivity.this, ContactListActivity.class);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("DISPLAY_NAME_EXTRA", displayName);
            startActivity(intent);
        });
        btnAddContact.setOnClickListener(view -> {
            String contactName = ((EditText) findViewById(R.id.contactInput)).getText().toString();
            if (!contactName.isEmpty()) {
                //String contactId = generateRandomId();
                //String lastMessage = Integer.toString((int) (Math.random() * 100 + 1));
                //String lastDate = Integer.toString((int) (Math.random() * 9000000 + 1000000));
                //Contact contact = new Contact(contactId, contactName, lastMessage, lastDate);

                // Create a user object with the contact name as the username

                // Call the API to add the contact
                ChatAPI chatAPI = new ChatAPI(authToken);
                chatAPI.postChats(new ChatAPI.ChatCallback() {
                    @Override
                    public void onPostChatSuccess(ContactPostResponse contactPostResponse) {
                        // Handle success
                        Intent intent = new Intent(AddContactActivity.this, ContactListActivity.class);
                        intent.putExtra("TOKEN_EXTRA", authToken);
                        intent.putExtra("DISPLAY_NAME_EXTRA", displayName);
                        intent.putExtra("USERNAME_EXTRA", username);
                        startActivity(intent);
                        System.out.println("Contact added successfully");
                        // Refresh the chat list or perform any other necessary actions
                    }
                    @Override
                    public void onUserSuccess(UserResponse userResponse) {

                    }
                    @Override
                    public void onSuccess(List<ContactResponse> chats){
                    System.out.println("");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Handle failure
                        Intent intent = new Intent(AddContactActivity.this, ContactListActivity.class);
                        intent.putExtra("TOKEN_EXTRA", authToken);
                        intent.putExtra("DISPLAY_NAME_EXTRA", displayName);
                        intent.putExtra("USERNAME_EXTRA", username);
                        startActivity(intent);
                        System.out.println("Failed to add contact: " + t.getMessage());
                    }
                }, contactName);

                // ... Rest of the code
            }
        });
    }
    private String generateRandomId() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int idLength = 10;
        StringBuilder randomId = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < idLength; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            randomId.append(randomChar);
        }

        return randomId.toString();
    }
}
