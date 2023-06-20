package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.adapters.MessageAdapter;
import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.contacts.AddContactActivity;
import com.example.myapplication.contacts.ContactListActivity;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.UserResponse;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAPI.ChatCallback{
    private MessageDB messageDB;
    private Message message;
    MessageDao messageDao;
    private String token;
    private List<Message> messageList;
    private ListView listViewMessages;
    private EditText etMessageInput;
    private ImageButton btnSend;
    private ImageButton btnGoBack;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String selectedUsername = getIntent().getStringExtra("SELECTED_USERNAME");
        String selectedDisplayName = getIntent().getStringExtra("SELECTED_DISPLAY_NAME");
        token = getIntent().getStringExtra("SELECTED_TOKEN");

        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText(selectedDisplayName);

        messageDB = MessageDB.getDatabase(getApplicationContext());
        messageDao = messageDB.messageDao();

        listViewMessages = findViewById(R.id.listMessages);
        etMessageInput = findViewById(R.id.etMessageInput);
        btnSend = findViewById(R.id.btnSend);
        btnGoBack = findViewById(R.id.btnGoBack);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        listViewMessages.setAdapter(messageAdapter);

        // Fetch existing messages from the database
        messageList.addAll(messageDao.index());
        messageAdapter.notifyDataSetChanged();
        listViewMessages.smoothScrollToPosition(messageAdapter.getCount() - 1); // Scroll to the bottom

        btnSend.setOnClickListener(v -> {
            String messageContent = etMessageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                // Create a new message
                Message newMessage = new Message(messageContent, true);

                // Insert the new message into the database
                messageDao.insert(newMessage);
                messageDao.index();
                // Add the new message to the list and notify the adapter
                messageList.add(newMessage);
                messageAdapter.notifyDataSetChanged();
                listViewMessages.smoothScrollToPosition(messageAdapter.getCount() - 1); // Scroll to the bottom

                etMessageInput.getText().clear(); // Clear the input field
            }
        });

        btnGoBack.setOnClickListener(view -> {
            // Start ContactListActivity when "Go Back" button is clicked
            Intent intent = new Intent(ChatActivity.this, ContactListActivity.class);
            startActivity(intent);
        });
        fetchChatFromServer(selectedUsername);
    }


    private void fetchChatFromServer(String selectedUsername) {
        ChatAPI chatAPI = new ChatAPI(token);
        chatAPI.getChat(this, selectedUsername);
    }
    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse) {
        // Extract the necessary data from chatByIdResponse and update your UI accordingly
//        List<ContactResponse> users = chatByIdResponse.getUsers();
//        List<Message> messages = chatByIdResponse.getMessages();
//
//        // Update your user interface with the retrieved data
//        updateUI(users, messages);
    }

    private void updateUI(List<ContactResponse> users, List<Message> messages) {
//        // Update your UI with the retrieved data
//        // For example, you can update the user details in the ContactListActivity
//        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
//        tvCurrentUser.setText(users.get(0).getDisplayName());
//
//        ImageView userAvatar = findViewById(R.id.userAvatar);
//        String profilePicUrl = users.get(0).getProfilePic();
//        if (profilePicUrl != null) {
//            RequestOptions requestOptions = new RequestOptions()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL);
//
//            Glide.with(this)
//                    .load(profilePicUrl)
//                    .apply(requestOptions)
//                    .into(userAvatar);
//        } else {
//            userAvatar.setImageResource(R.drawable.a31975);
//        }
//
//        // Update the message list
//        messageList.clear();
//        messageList.addAll(messages);
//        messageAdapter.notifyDataSetChanged();
//        listViewMessages.smoothScrollToPosition(messageAdapter.getCount() - 1);
    }


    @Override
    public void onSuccess(List<ContactResponse> chats) {

    }
    @Override
    public void onUserSuccess(UserResponse userResponse) {

    }
    @Override
    public void onPostChatSuccess(ContactPostResponse contactPostResponse) {

    }
    @Override
    public void onFailure(Throwable t) {

    }
}





