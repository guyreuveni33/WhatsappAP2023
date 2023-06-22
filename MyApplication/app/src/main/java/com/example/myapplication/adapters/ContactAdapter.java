package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        ImageView profilePic = convertView.findViewById(R.id.profilePicture);

        Contact contact = getItem(position);

        contactName.setText(contact.getName());
        lastMessage.setText(contact.getLastMessage());

        // Format the date and set it to the currentTime TextView
        String formattedDate = formatDate(contact.getLastDate());
        currentTime.setText(formattedDate);

        Glide.with(getContext())
                .load(contact.getProfilePic())
                .into(profilePic);

        return convertView;
    }

    public void setContacts(List<Contact> contactList) {
        clear();
        if (contactList != null) {
            addAll(contactList);
        }
        notifyDataSetChanged();
    }
    private String formatDate(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
