package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactListActivity extends AppCompatActivity {

    private String[] conversationList = {"User 1", "User 2", "User 3"};
    private ImageButton btnLogout;
    private ImageButton btnSettings;
    private ImageButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        btnLogout = findViewById(R.id.btnLogout);
        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);
        // Set current user name
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvCurrentUser.setText("John Doe"); // Replace with actual user name

        // Set user avatar
        ImageView userAvatar = findViewById(R.id.userAvatar);
        userAvatar.setImageResource(R.drawable.a31975); // Replace with actual avatar image

        // Set up conversation list
        ListView lvConversationList = findViewById(R.id.lvConversationList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_contact, R.id.contactName, conversationList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView contactName = view.findViewById(R.id.contactName);
                TextView lastMessage = view.findViewById(R.id.lastMessage);
                TextView currentTime = view.findViewById(R.id.currentTime);

                // Set data for each item
                contactName.setText(conversationList[position]);
                lastMessage.setText("Last Message");
                currentTime.setText("hh:mm");
                return view;
            }
        };
        lvConversationList.setAdapter(adapter);

        // Handle item click event
        lvConversationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(ContactListActivity.this, "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(ContactListActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(view -> {
            // Start Register activity when "Click here" is clicked
            Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
            startActivity(intent);
        });
    }
}
