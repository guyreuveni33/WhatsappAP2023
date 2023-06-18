package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SettingsActivity;
import com.example.myapplication.adapters.ContactAdapter;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactResponse;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements ChatAPI.ChatCallback {

    private ImageButton btnLogout;
    private ImageButton btnSettings;
    private ImageButton btnAdd;
    private ContactDao contactDao;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Get an instance of the ContactDao for interacting with the database
        contactDao = ContactDB.getDatabase(getApplicationContext()).contactDao();

        // Initialize views
        btnLogout = findViewById(R.id.btnLogout);
        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);

        // Set current user name
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText("John Doe");

        // Set user avatar image
        ImageView userAvatar = findViewById(R.id.userAvatar);
        userAvatar.setImageResource(R.drawable.a31975);

        // Set up the conversation list
        ListView lvConversationList = findViewById(R.id.lvConversationList);

        // Fetch the conversation list from the database
        List<Contact> conversationList = contactDao.index();

        // Create the ContactAdapter
        adapter = new ContactAdapter(this, conversationList);

        // Set the adapter on the ListView
        lvConversationList.setAdapter(adapter);

        // Set item click listener for the ListView
        lvConversationList.setOnItemClickListener((parent, view, position, id) -> {
            // Handle item click event
            Contact selectedContact = adapter.getItem(position);
            Toast.makeText(ContactListActivity.this, "Clicked: " + selectedContact.getName(), Toast.LENGTH_SHORT).show();
        });

        // Set click listeners for the buttons
        btnLogout.setOnClickListener(view -> {
            // Handle logout button click
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(view -> {
            // Handle settings button click
            Intent intent = new Intent(ContactListActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            // Handle add button click
            Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
            startActivity(intent);
        });

        // Fetch contacts from the server in the background
        fetchContactsFromServer();
    }

    private void fetchContactsFromServer() {
        // Retrieve the user token from the Login screen
        String authToken = getIntent().getStringExtra("TOKEN_EXTRA");

        // Create an instance of ChatAPI with the token
        ChatAPI chatAPI = new ChatAPI(authToken);

        // Make an API call to fetch contacts from the server
        chatAPI.get(this);
    }

    @Override
    public void onSuccess(List<ContactResponse> contacts) {
        // Update the conversation list with the retrieved contacts
        List<Contact> contactList = mapContactResponses(contacts);

        // Update the local Room database with the contacts from the server
        updateContactsInDatabase(contactList);

        // Update the adapter with the updated contact list
        adapter.setContacts(contactList);
    }

    private void updateContactsInDatabase(List<Contact> contactList) {
        // Clear the existing contacts in the database
        contactDao.delete();

        // Insert the new contacts into the database
        contactDao.insert(contactList.toArray(new Contact[0]));
    }

    @Override
    public void onFailure(Throwable t) {
        // Handle failure case
        Toast.makeText(ContactListActivity.this, "Failed to fetch contacts", Toast.LENGTH_SHORT).show();
    }

    // Helper method to map ContactResponse objects to Contact objects
    private List<Contact> mapContactResponses(List<ContactResponse> contactResponses) {
        List<Contact> contactList = new ArrayList<>();
        for (ContactResponse response : contactResponses) {

            String name = response.getUser().getDisplayName();
            String lastMessage = response.getLastMessage() != null ? response.getLastMessage().getContent() : null;
            String lastDate = response.getLastMessage() != null ? response.getLastMessage().getCreated() : null;
            // Print the values for debugging

            System.out.println("response:"+response.getId());
            System.out.println("Name: " + name);
            System.out.println("Last Message: " + lastMessage);
            System.out.println("Last Date: " + lastDate);
            Contact contact = new Contact(response.getId(), name, lastMessage, lastDate);
            System.out.println(contact);
            contactList.add(contact);
        }
        return contactList;
    }
}

