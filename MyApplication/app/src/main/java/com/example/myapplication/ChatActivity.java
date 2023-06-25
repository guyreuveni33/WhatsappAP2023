package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
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
import com.example.myapplication.viewModel.MessageViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAPI.ChatCallback, MessageAPI.ChatCallback {
    private MessageDB messageDB;
    private Message message;
    private MessageDao messageDao;
    private String token;
    private String selectedUsername;
    private List<Message> messageList;
    private List<Message> tempList;
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
        listViewMessages = findViewById(R.id.listMessages);
        etMessageInput = findViewById(R.id.etMessageInput);
        btnSend = findViewById(R.id.btnSend);
        etMessageInput = findViewById(R.id.etMessageInput);
        etMessageInput.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                btnSend.performClick();
                return true;
            }
            return false;
        });
        btnGoBack = findViewById(R.id.btnGoBack);
        messageList = new ArrayList<>();
        tempList = new ArrayList<>();
        fetchMessagesFromServer(userId);
        tempList = messageDao.getChatMessages(userId);

        messageList.addAll(tempList);
        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);
        listViewMessages.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        listViewMessages.setSelection(messageAdapter.getCount() - 1);

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.getContact().observe(this, contact -> {
            messageAdapter.setMessage(messageList);
        });
        btnSend.setOnClickListener(v -> {
            String messageContent = etMessageInput.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                messageAPI.postMessage(this, userId, messageContent, messageList);

                messageAdapter.notifyDataSetChanged();
                listViewMessages.setSelection(messageAdapter.getCount() - 1);
                etMessageInput.getText().clear();
            }
        });
        btnGoBack.setOnClickListener(view -> {
            Intent intent = new Intent(ChatActivity.this, ContactListActivity.class);
            intent.putExtra("TOKEN_EXTRA", token);
            intent.putExtra("DISPLAY_NAME_EXTRA", currentUserDisplayName);
            intent.putExtra("USERNAME_EXTRA", currentusername);
            intent.putExtra("PROFILE_PIC_EXTRA", profilePicUrl);
            startActivity(intent);
        });
        fetchMessagesFromServer(userId);
    }

    private void fetchMessagesFromServer(String userId) {
        MessageAPI messageAPI1 = new MessageAPI(token);
        messageAPI1.getMessages(this, userId);
    }

    public void onSuccessGetMessage(List<MessagesResponse> messages, String chatId) {
        List<Message> messageList = mapMessagesResponses(messages, chatId);
        updateUI2(messageList);
    }

    private List<Message> mapMessagesResponses(List<MessagesResponse> messages, String chatId) {
        List<Message> mappedMessages = new ArrayList<>();
        for (MessagesResponse messageResponse : messages) {
            boolean isSent = !messageResponse.getSender().getUsername().equals(selectedUsername);
            Message message = new Message(messageResponse.getId(), messageResponse.getContent(), isSent);
            message.setChatId(chatId);
            message.setTimestamp(messageResponse.getCreated());
            mappedMessages.add(message);
            tempList.add(message);
        }

        Collections.reverse(mappedMessages);
        return mappedMessages;
    }

    private void updateUI2(List<Message> messages) {
        if (messageAdapter != null) {
            messageList.clear();
            messageList.addAll(messages);
            messageAdapter.notifyDataSetChanged();
            listViewMessages.setSelection(messageAdapter.getCount() - 1);
            addMessagesToDatabase(messages);
        }
    }


    @Override
    public void onGetChatSuccess(ChatByIdResponse chatByIdResponse) {
        List<CurrentUserChat> users = chatByIdResponse.getUsers();
        List<ChatMessageResponse> messages = chatByIdResponse.getMessages();
    }

    private void addMessagesToDatabase(List<Message> messageList) {
        MessageDB messageDB = MessageDB.getDatabase(getApplicationContext());
        MessageDao messageDao = messageDB.messageDao();

        for (Message m : messageList) {
            if (MessageDB.getDatabase().messageDao().get(m.getId()) != null)
                continue;
            messageDao.insert(m);
        }
    }

    @Override
    public void onSuccessPostMessage(ChatMessageResponse chatMessageResponse) {
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
}





