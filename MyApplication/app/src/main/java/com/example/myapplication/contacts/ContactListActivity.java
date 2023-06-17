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
import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactListActivity extends AppCompatActivity {

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
    }
}
