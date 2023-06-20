package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ChatActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SettingsActivity;
import com.example.myapplication.adapters.ContactAdapter;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.UserResponse;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements ChatAPI.ChatCallback {

    private String username;
    private ImageButton btnLogout;
    private ImageButton btnSettings;
    private ImageButton btnAdd;
    private ContactDao contactDao;
    private ContactAdapter adapter;
    private String authToken;
    private List<Contact> conversationList;
    private UserResponse user;
    private MessageDao messageDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        user = new UserResponse("", "", "");
        username = getIntent().getStringExtra("USERNAME_EXTRA");

        // Get an instance of the ContactDao for interacting with the database
        contactDao = ContactDB.getDatabase(getApplicationContext()).contactDao();
        messageDao = MessageDB.getDatabase(getApplicationContext()).messageDao();

        // Initialize views
        btnLogout = findViewById(R.id.btnLogout);
        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);

        // Set current user name
        // TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        //tvCurrentUser.setText("John Doe");

        // Set user avatar image
        //ImageView userAvatar = findViewById(R.id.userAvatar);
        //userAvatar.setImageResource(R.drawable.a31975);

        // Set up the conversation list
        ListView lvConversationList = findViewById(R.id.lvConversationList);

        // Fetch the conversation list from the database
        conversationList = contactDao.index();

        // Create the ContactAdapter
        adapter = new ContactAdapter(this, conversationList);

        // Set the adapter on the ListView
        lvConversationList.setAdapter(adapter);

        // Set item click listener for the ListView
        lvConversationList.setOnItemClickListener((parent, view, position, id) -> {
            // Handle item click event
            messageDao.nukeTable();
            Contact selectedContact = adapter.getItem(position);
            String selectedUsername = selectedContact.getUsername();
            String selectedId = selectedContact.getId();
            String selectedDisplayName = selectedContact.getName();
            Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);
            intent.putExtra("SELECTED_USERNAME", selectedUsername);
            intent.putExtra("SELECTED_TOKEN", authToken);
            intent.putExtra("SELECTED_DISPLAY_NAME", selectedDisplayName);
            intent.putExtra("SELECTED_ID", selectedId);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("DISPLAY_NAME_EXTRA", user.getDisplayName());
            intent.putExtra("TOKEN_EXTRA", authToken);
            startActivity(intent);
        });

        // Set click listeners for the buttons
        btnLogout.setOnClickListener(view -> {
            //contactDao.nukeTable();
            // Handle logout button click
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(view -> {
            // Handle settings button click
            Intent intent = new Intent(ContactListActivity.this, SettingsActivity.class);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("DISPLAY_NAME_EXTRA", user.getDisplayName());
            intent.putExtra("USERNAME_EXTRA", username);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            // Handle add button click
            Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("DISPLAY_NAME_EXTRA", user.getDisplayName());
            intent.putExtra("USERNAME_EXTRA", username);

            startActivity(intent);
        });

        // Fetch contacts from the server in the background
        fetchContactsFromServer();
        // Fetch contacts and user details from the server in the background
        fetchContactsAndUserDetailsFromServer();
    }

    private void fetchContactsAndUserDetailsFromServer() {
        // Retrieve the user token and username from the Login screen
        authToken = getIntent().getStringExtra("TOKEN_EXTRA");
        // Create an instance of ChatAPI with the token
        ChatAPI chatAPI = new ChatAPI(authToken);

        // Make API calls to fetch contacts and user details from the server
        chatAPI.get(this);
        chatAPI.getUser(this, username);
    }

    @Override
    public void onUserSuccess(UserResponse userResponse) {
        // Update the user details in the ContactListActivity
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        user.setDisplayName(userResponse.getDisplayName());
        tvCurrentUser.setText(user.getDisplayName());

        ImageView userAvatar = findViewById(R.id.userAvatar);

        // Load and set the user's profile picture using the profilePic URL
        String profilePicUrl = userResponse.getProfilePic();
        if (profilePicUrl != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL); // Caching options, if needed

            Glide.with(this)
                    .load(profilePicUrl)
                    .apply(requestOptions)
                    .into(userAvatar);
        } else {
            // Set a default avatar if the profile picture URL is null
            userAvatar.setImageResource(R.drawable.a31975);
        }
    }

    private void fetchContactsFromServer() {
        // Retrieve the user token from the Login screen
        String authToken = getIntent().getStringExtra("TOKEN_EXTRA");

        // Create an instance of ChatAPI with the token
        ChatAPI chatAPI = new ChatAPI(authToken);

        // Make an API call to fetch contacts from the server
        chatAPI.get(this);
        chatAPI.getUser(this, username);
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
        contactDao.nukeTable();
        // Insert the new contacts into the database
        contactDao.insert(contactList.toArray(new Contact[0]));
    }

    @Override
    public void onFailure(Throwable t) {
        // Handle failure case
        Toast.makeText(ContactListActivity.this, "Failed to fetch contacts", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPostChatSuccess(ContactPostResponse contactPostResponse){

    }
    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse){

    }
    // Helper method to map ContactResponse objects to Contact objects
    private List<Contact> mapContactResponses(List<ContactResponse> contactResponses) {
        List<Contact> contactList = new ArrayList<>();
        for (ContactResponse response : contactResponses) {
            String username = response.getUser().getUsername();
            String name = response.getUser().getDisplayName();
            String lastMessage = response.getLastMessage() != null ? response.getLastMessage().getContent() : null;
            String lastDate = response.getLastMessage() != null ? response.getLastMessage().getCreated() : null;

            Contact contact = new Contact(response.getId(), username, name, lastMessage, lastDate);
            contactList.add(contact);
        }
        return contactList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if there are updated values for authToken and displayName
        String updatedAuthToken = getIntent().getStringExtra("TOKEN_EXTRA");
        String updatedDisplayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
        // Update the member variables if new values are available
        if (updatedAuthToken != null) {
            authToken = updatedAuthToken;
        }
        if (updatedDisplayName != null) {
            user.setDisplayName(updatedDisplayName);;
        }
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText(getIntent().getStringExtra("DISPLAY_NAME_EXTRA"));
        conversationList.clear();
      //  List<Contact> list = contactDao.index();
       // int a = 5;
        conversationList.clear();
        conversationList.addAll(contactDao.index());
        adapter.notifyDataSetChanged();

    }

}