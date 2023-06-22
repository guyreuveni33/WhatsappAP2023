package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.myapplication.viewModel.ContactViewModel;
import com.example.myapplication.viewModel.MessageViewModel;

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

    private MessageViewModel messageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        currentUserDisplayName = getIntent().getStringExtra("DISPLAY_NAME_EXTRA");
        currentusername = getIntent().getStringExtra("USERNAME_EXTRA");
        selectedUsername = getIntent().getStringExtra("SELECTED_USERNAME");
        profilePicUrl = getIntent().getStringExtra("PROFILE_PIC_EXTRA");
        selectedContactProfilePic = getIntent().getStringExtra("SELECTED_PROFILE_PIC_EXTRA");
        String userId = getIntent().getStringExtra("SELECTED_ID");
        String selectedDisplayName = getIntent().getStringExtra("SELECTED_DISPLAY_NAME");
        token = getIntent().getStringExtra("SELECTED_TOKEN");
        messageAPI = new MessageAPI(token);
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText(selectedDisplayName);
        ImageView tvUserAvatar = findViewById(R.id.userAvatar);
        if (selectedContactProfilePic != null) {
            Glide.with(this)
                    .load(selectedContactProfilePic)
                    .into(tvUserAvatar);
        }
        messageDB = MessageDB.getDatabase(getApplicationContext());
        messageDao = messageDB.messageDao();
        System.out.println(messageDao.index());
        listViewMessages = findViewById(R.id.listMessages);
        etMessageInput = findViewById(R.id.etMessageInput);
        btnSend = findViewById(R.id.btnSend);
        btnGoBack = findViewById(R.id.btnGoBack);
        fetchMessagesFromServer(userId);

        //fetchChatFromServer(userId);
        messageList = new ArrayList<>();

        // Fetch existing messages from the database
        messageList.addAll(messageDao.index());
        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);
        listViewMessages.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        listViewMessages.setSelection(messageAdapter.getCount() - 1); // Scroll to the bottom

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.getContact().observe(this, contact -> {
            messageAdapter.setMessage(messageList);
        });
        btnSend.setOnClickListener(v -> {
            String messageContent = etMessageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                // Create a new message
                Message newMessage = new Message(messageContent, true);
                messageAPI.postMessage(this, userId, messageContent);
                // Insert the new message into the database
                messageDao.insert(newMessage);

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
        messageAPI1.getMessages(this, userId);
    }

    private void fetchChatFromServer(String userId) {
        ChatAPI chatAPI = new ChatAPI(token);
        chatAPI.getChat(this, userId);
    }

    public void onSuccessGetMessage(List<MessagesResponse> messages) {
        List<Message> messageList = mapMessagesResponses(messages);
        updateUI2(messageList);
    }

    private List<Message> mapMessagesResponses(List<MessagesResponse> messages) {
        List<Message> mappedMessages = new ArrayList<>();
        for (MessagesResponse messageResponse : messages) {
            boolean isSent = !messageResponse.getSender().getUsername().equals(selectedUsername);
            Message message = new Message(messageResponse.getContent(), isSent);
            message.setTimestamp(messageResponse.getCreated());
            mappedMessages.add(message);
        }
        Collections.reverse(mappedMessages);
        return mappedMessages;
    }

    private void updateUI2(List<Message> messages) {
        messageList.clear();
        messageList.addAll(messages);
        messageAdapter.notifyDataSetChanged();
        listViewMessages.setSelection(messageAdapter.getCount() - 1);
        addMessagesToDatabase(messages);
    }


    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse) {
        // Extract the necessary data from chatByIdResponse and update your UI accordingly
        List<CurrentUserChat> users = chatByIdResponse.getUsers();
        List<ChatMessageResponse> messages = chatByIdResponse.getMessages();
//        for (ChatMessageResponse messageResponse : messages) {
//            System.out.println(messageResponse.getContent());
//            System.out.println("allllllllllllllllllllllllllllllllllllllaafadvadvavav");
//        }
// Update your user interface with the retrieved data
        //  updateUI(users, messages);
    }

    private void updateUI(List<MessagesResponse> messages) {
        // Update your UI with the retrieved data

        // Convert ChatMessageResponse[] array to List<Message>
        // List<Message> messageList = mapChatMessageResponses(messages);

        // Update the message list
        this.messageList.clear();
        this.messageList.addAll(messageDao.index());
        System.out.println(this.messageList);
        messageAdapter.notifyDataSetChanged();
        listViewMessages.setSelection(messageAdapter.getCount() - 1);
        addMessagesToDatabase(messageList);
    }


    private List<Message> mapChatMessageResponses(List<ChatMessageResponse> messages) {
        List<Message> mappedMessages = new ArrayList<>();
        for (ChatMessageResponse messageResponse : messages) {
            boolean isSent;
            // Determine the value of isSent based on the selectedUsername
            if (messageResponse.getSender().getUsername().equals(selectedUsername)) {
                isSent = false;
            } else {
                isSent = true;
            }
            // Assuming Message has a constructor that takes the necessary parameters
            Message message = new Message(messageResponse.getContent(), isSent);
            message.setTimestamp(messageResponse.getCreated());
            mappedMessages.add(message);
        }
        return mappedMessages;
    }

    private void addMessagesToDatabase(List<Message> messageList) {
        MessageDB messageDB = MessageDB.getDatabase(getApplicationContext());
        MessageDao messageDao = messageDB.messageDao();

        // Clear the existing messages in the database
        messageDao.nukeTable();

        // Insert the new messages into the database in reverse order
        messageDao.insert(messageList.toArray(new Message[0]));
    }

    @Override
    public void onSuccessPostMessage(ChatMessageResponse chatMessageResponse) {
        // Handle the successful response here, e.g., update the UI with the new message
    }

    @Override
    public void onFailureGetMessage(Throwable t) {
        // Handle failure to retrieve messages
        // Display an error message or perform any necessary error handling
    }

    @Override
    public void onFailurePostMessage(Throwable t) {
        // Handle the failure here, e.g., display an error message
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





