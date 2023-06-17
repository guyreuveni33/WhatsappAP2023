package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(Context context, List<Contact> contactList) {
        super(context, 0, contactList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, parent, false);
        }

        TextView contactName = convertView.findViewById(R.id.contactName);
        TextView lastMessage = convertView.findViewById(R.id.lastMessage);
        TextView currentTime = convertView.findViewById(R.id.currentTime);

        Contact contact = getItem(position);

        contactName.setText(contact.getName());
        lastMessage.setText(contact.getLastMessage());
        currentTime.setText(contact.getLastDate());

        return convertView;
    }

    public void setContacts(List<Contact> contactList) {
        clear();
        addAll(contactList);
        notifyDataSetChanged();
    }
}
