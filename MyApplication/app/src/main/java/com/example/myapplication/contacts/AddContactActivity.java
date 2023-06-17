package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;

public class AddContactActivity extends AppCompatActivity {

    private ImageButton btnGoBack;
    private Button btnAddContact;
    private ContactDao contactDao;
    private ContactDB contactDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        this.contactDB = ContactDB.getDatabase(getApplicationContext());
        contactDao = contactDB.contactDao();
        btnGoBack = findViewById(R.id.btnGoBack);
        btnAddContact = findViewById(R.id.addContactButton);
        btnGoBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddContactActivity.this, ContactListActivity.class);
            startActivity(intent);
        });
        btnAddContact.setOnClickListener(view -> {
            String contactName = ((EditText) findViewById(R.id.contactInput)).getText().toString();
            if (!contactName.isEmpty()) {
                int contactId = (int) (Math.random() * 9000 + 1000);
                String lastMessage = Integer.toString((int) (Math.random() * 100 + 1));
                String lastDate = Integer.toString((int) (Math.random() * 9000000 + 1000000));

                Contact contact = new Contact(contactId, contactName, lastMessage, lastDate);
                contactDao.insert(contact);

                Intent intent = new Intent(AddContactActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
    }
}