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
import com.example.myapplication.api.MessageAPI;
import com.example.myapplication.contacts.ContactListActivity;
import com.example.myapplication.entities.ChatByIdResponse;
import com.example.myapplication.entities.ChatMessageResponse;
import com.example.myapplication.entities.ContactPostResponse;
import com.example.myapplication.entities.ContactResponse;
import com.example.myapplication.entities.CurrentUserChat;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.MessagesResponse;
import com.example.myapplication.entities.UserResponse;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAPI.ChatCallback, MessageAPI.ChatCallback {
    private MessageDB messageDB;
    private Message message;
    MessageDao messageDao;
    private String token;
    private String selectedUsername;
    private List<Message> messageList;
    private ListView listViewMessages;
    private EditText etMessageInput;
    private ImageButton btnSend;
    private ImageButton btnGoBack;
    private MessageAdapter messageAdapter;
    private String currentUserDisplayName;
    private String currentusername;
    private MessageAPI messageAPI;
    private String profilePicUrl;
    private String selectedContactProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageList = new ArrayList<>();
        setContentView(R.layout.activity_chat);
        currentUserDisplayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
        currentusername = getIntent().getStringExtra("USERNAME_EXTRA");
        selectedUsername = getIntent().getStringExtra("SELECTED_USERNAME");
        profilePicUrl = getIntent().getStringExtra("PROFILE_PIC_EXTRA");
        selectedContactProfilePic = getIntent().getStringExtra("SELECTED_PROFILE_PIC_EXTRA");
        String userId = getIntent().getStringExtra("SELECTED_ID");
        String selectedDisplayName = getIntent().getStringExtra("SELECTED_DISPLAY_NAME");
        token = getIntent().getStringExtra("SELECTED_TOKEN");
        messageDB = MessageDB.getDatabase(getApplicationContext());
       // messageDao = messageDB.messageDao();
        messageAPI = new MessageAPI(token);
       // messageList = messageDao.getAll();

        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText(selectedDisplayName);
        ImageView tvUserAvatar = findViewById(R.id.userAvatar);
        if (selectedContactProfilePic != null) {
            Glide.with(this)
                    .load(selectedContactProfilePic)
                    .into(tvUserAvatar);
        }
        fetchMessagesFromServer(userId);
        listViewMessages = findViewById(R.id.listMessages);
        etMessageInput = findViewById(R.id.etMessageInput);
        btnSend = findViewById(R.id.btnSend);
        btnGoBack = findViewById(R.id.btnGoBack);

        //fetchChatFromServer(userId);

        // Fetch existing messages from the database
        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);
        listViewMessages.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        listViewMessages.setSelection(messageAdapter.getCount() - 1); // Scroll to the bottom

        btnSend.setOnClickListener(v -> {
            String messageContent = etMessageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                // Create a new message
                Message newMessage = new Message(messageContent, true);
                new Thread(() -> {
                    messageAPI.postMessage(this, userId, messageContent);
                }).start();                // Insert the new message into the database
                // Add the new message to the list and notify the adapter
                messageList.add(newMessage);
                messageAdapter.notifyDataSetChanged();
                listViewMessages.setSelection(messageAdapter.getCount() - 1); // Scroll to the bottom
                etMessageInput.getText().clear(); // Clear the input field
                // Call the postMessage() method in the MessageAPI class to send the message to the server

                //fetchChatFromServer(userId);
                fetchMessagesFromServer(userId);

            }
        });
        btnGoBack.setOnClickListener(view -> {
            // Start ContactListActivity when "Go Back" button is clicked
            Intent intent = new Intent(ChatActivity.this, ContactListActivity.class);
            intent.putExtra("TOKEN_EXTRA", token);
            intent.putExtra("DISPLAY_NAME_EXTRA", currentUserDisplayName);
            intent.putExtra("USERNAME_EXTRA", currentusername);
            intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
            startActivity(intent);
        });
        //fetchChatFromServer(userId);
        fetchMessagesFromServer(userId);
    }

    private void fetchMessagesFromServer(String userId) {
        MessageAPI messageAPI1 = new MessageAPI(token);
        new Thread(() -> messageAPI1.getMessages(this, userId)).start();
    }

    private void fetchChatFromServer(String userId) {
        ChatAPI chatAPI = new ChatAPI(token);
        chatAPI.getChat(this, userId);
    }

    public void onSuccessGetMessage(List<MessagesResponse> messages) {
        List<Message> messageList = mapMessagesResponses(messages);

        updateUI(messageList);
    }

    private List<Message> mapMessagesResponses(List<MessagesResponse> messages) {
        List<Message> mappedMessages = new ArrayList<>();
        //messageDao.nukeTable();
        for (MessagesResponse messageResponse : messages) {
            boolean isSent = !messageResponse.getSender().getUsername().equals(selectedUsername);
            Message message = new Message(messageResponse.getContent(), isSent);
            message.setTimestamp(messageResponse.getCreated());
            mappedMessages.add(message);
        //    messageDao.insert(message);
        }
        Collections.reverse(mappedMessages);
        return mappedMessages;
    }

    private void updateUI(List<Message> messages) {
        messageList.clear();
        messageList.addAll(messages);
        messageAdapter.notifyDataSetChanged();
        listViewMessages.setSelection(messageAdapter.getCount() - 1);
        //addMessagesToRoomDatabase(messages);
    }

    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse) {
    }


    private void addMessagesToRoomDatabase(List<Message> messageList) {
        messageDao.insert(message);

    }

    @Override
    public void onSuccessPostMessage(ChatMessageResponse chatMessageResponse) {
        // Handle the successful response here, e.g., update the UI with the new message
    }

    @Override
    public void onFailureGetMessage(Throwable t) {

    }

    @Override
    public void onFailurePostMessage(Throwable t) {
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
    private void initializeMessageDB() {
        messageDB = MessageDB.getDatabase(getApplicationContext());
        messageDao = messageDB.messageDao();
    }
}





