package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapters.MessageAdapter;
import com.example.myapplication.contacts.AddContactActivity;
import com.example.myapplication.contacts.ContactListActivity;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessageDao;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private MessageDB messageDB;
    private Message message;
    MessageDao messageDao;
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
    }
}





