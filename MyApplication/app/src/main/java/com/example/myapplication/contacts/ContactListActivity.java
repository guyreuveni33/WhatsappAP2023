package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

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
import com.example.myapplication.api.MessageAPI;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.ChatMessageResponse;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.MessagesResponse;
import com.example.myapplication.entities.UserResponse;
import com.example.myapplication.viewModel.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements ChatAPI.ChatCallback, MessageAPI.ChatCallback {

    private String username;
    private ImageButton btnLogout;
    private ImageButton btnSettings;
    private ImageButton btnAdd;
    private ContactDao contactDao;
    private ContactAdapter adapter;
    private String authToken;
    private List<Contact> conversationList;
    private UserResponse user;
    private String profilePicUrl;
    private ContactViewModel contactViewModel;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new UserResponse("", "", "");
        setContentView(R.layout.activity_contact_list);
        username = getIntent().getStringExtra("USERNAME_EXTRA");
        contactDao = ContactDB.getDatabase(getApplicationContext()).contactDao();

        btnLogout = findViewById(R.id.btnLogout);
        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);

        ListView lvConversationList = findViewById(R.id.lvConversationList);

        conversationList = contactDao.index();

        adapter = new ContactAdapter(this, conversationList);

        lvConversationList.setAdapter(adapter);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getContact().observe(this, contact -> {
            System.out.println("HELEKFEJFEJFOEOFNEOFNKEFNLKEFNLKENFLKEFNLK");
            adapter.setContacts(contactList);
        });
        lvConversationList.setOnItemClickListener((parent, view, position, id) -> {
            Contact selectedContact = adapter.getItem(position);
            String selectedUsername = selectedContact.getUsername();
            String selectedId = selectedContact.getId();
            String selectedDisplayName = selectedContact.getName();
            String selectedProfilePic = selectedContact.getProfilePic();
            Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);
            intent.putExtra("SELECTED_USERNAME", selectedUsername);
            intent.putExtra("SELECTED_TOKEN", authToken);
            intent.putExtra("SELECTED_DISPLAY_NAME", selectedDisplayName);
            intent.putExtra("SELECTED_ID", selectedId);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("DISPLAY_NAME_EXTRA", user.getDisplayName());
            intent.putExtra("SELECTED_PROFILE_PIC_EXTRA", selectedProfilePic);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
        });
        String ContactListActivityFlag = "CONTACTLISTACTIVITY";
        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, SettingsActivity.class);
            intent.putExtra("SETTING_EXTRA", ContactListActivityFlag);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("DISPLAY_NAME_EXTRA", user.getDisplayName());
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
            startActivity(intent);
        });
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
            intent.putExtra("TOKEN_EXTRA", authToken);
            intent.putExtra("DISPLAY_NAME_EXTRA", user.getDisplayName());
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
            startActivity(intent);
        });

        fetchContactsAndUserDetailsFromServer();
    }


    @Override
    public void onSuccessGetMessage(List<MessagesResponse> messages, String chatId) {
    }

    @Override
    public void onSuccessPostMessage(ChatMessageResponse chatMessageResponse) {
    }

    @Override
    public void onFailurePostMessage(Throwable t) {
    }

    @Override
    public void onFailureGetMessage(Throwable t) {
    }

    private void fetchContactsAndUserDetailsFromServer() {
        authToken = getIntent().getStringExtra("TOKEN_EXTRA");
        ChatAPI chatAPI = new ChatAPI(authToken);

        chatAPI.get(this);
        chatAPI.getUser(this, username);
    }

    @Override
    public void onUserSuccess(UserResponse userResponse) {
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        user.setDisplayName(userResponse.getDisplayName());
        tvCurrentUser.setText(user.getDisplayName());

        ImageView userAvatar = findViewById(R.id.userAvatar);

        profilePicUrl = userResponse.getProfilePic();
        if (profilePicUrl != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL); // Caching options, if needed

            Glide.with(this)
                    .load(profilePicUrl)
                    .apply(requestOptions)
                    .into(userAvatar);
        } else {
            userAvatar.setImageResource(R.drawable.a31975);
        }
    }

    @Override
    public void onSuccess(List<ContactResponse> contacts) {
        contactList = mapContactResponses(contacts);
        updateContactsInDatabase(contactList);

        adapter.setContacts(contactList);
    }

    private void updateContactsInDatabase(List<Contact> contactList) {
        contactDao.nukeTable();
        contactDao.insert(contactList.toArray(new Contact[0]));
    }

    @Override
    public void onFailure(Throwable t) {
    }

    @Override
    public void onPostChatSuccess(ContactPostResponse contactPostResponse) {

    }

    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse) {

    }

    private List<Contact> mapContactResponses(List<ContactResponse> contactResponses) {
        contactList = new ArrayList<>();
        for (ContactResponse response : contactResponses) {
            String username = response.getUser().getUsername();
            String name = response.getUser().getDisplayName();
            String profilePic = response.getUser().getProfilePic();
            String lastMessage = null, lastDate = null;
            if (response.getLastMessage() != null) {
                lastMessage = response.getLastMessage().getCreated() != null ? response.getLastMessage().getContent() : null;
                lastDate = response.getLastMessage() != null ? response.getLastMessage().getCreated() : null;
            }
            Contact contact = new Contact(response.getId(), username, name, lastMessage, lastDate, profilePic);
            contactList.add(contact);
        }
        return contactList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String updatedAuthToken = getIntent().getStringExtra("TOKEN_EXTRA");
        String updatedDisplayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
        profilePicUrl = getIntent().getStringExtra("PROFILE_PIC_EXTRA");
        if (updatedAuthToken != null) {
            authToken = updatedAuthToken;
        }
        if (updatedDisplayName != null) {
            user.setDisplayName(updatedDisplayName);
        }
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText(getIntent().getStringExtra("DISPLAY_NAME_EXTRA"));
        ImageView tvUserAvatar = findViewById(R.id.userAvatar);
        if (profilePicUrl != null) {
            Glide.with(this)
                    .load(profilePicUrl)
                    .into(tvUserAvatar);
        }
        conversationList.clear();
        conversationList.clear();
        conversationList.addAll(contactDao.index());
        adapter.notifyDataSetChanged();
    }
}