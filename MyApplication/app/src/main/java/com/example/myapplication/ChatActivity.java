package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<Message> messageList;
    private ListView listViewMessages;
    private EditText etMessageInput;
    private ImageButton btnSend;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listViewMessages = findViewById(R.id.listMessages);
        etMessageInput = findViewById(R.id.etMessageInput);
        btnSend = findViewById(R.id.btnSend);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        listViewMessages.setAdapter(messageAdapter);

        // Example conversation
        messageList.add(new Message("Hello", false)); // Received message
        messageList.add(new Message("Hi, how are you?", true)); // Sent message
        messageList.add(new Message("I'm good, thanks!", false)); // Received message
        messageAdapter.notifyDataSetChanged();

        btnSend.setOnClickListener(v -> {
            String message = etMessageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                messageList.add(new Message(message, true)); // Add sent message
                etMessageInput.getText().clear(); // Clear the input field
                messageAdapter.notifyDataSetChanged();
                listViewMessages.smoothScrollToPosition(messageAdapter.getCount() - 1); // Scroll to the bottom
            }
        });
    }
}
