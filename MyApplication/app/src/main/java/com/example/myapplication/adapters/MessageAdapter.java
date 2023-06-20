package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Message;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    messageList.get(position).isSent() ? R.layout.item_message_sent : R.layout.item_message_received,
                    parent,
                    false
            );
        }

        Message message = messageList.get(position);

        TextView tvMessage = convertView.findViewById(message.isSent() ? R.id.tvMessageSent : R.id.tvMessageReceived);
        if (tvMessage == null) {
            tvMessage = new TextView(parent.getContext());
        }

        tvMessage.setText(message.getContent());

        TextView tvTimestamp = convertView.findViewById(message.isSent() ? R.id.tvTimestampSent : R.id.tvTimestampReceived);
        if (tvTimestamp == null) {
            tvTimestamp = new TextView(parent.getContext());
        }

        tvTimestamp.setText(message.getTimestamp());

        return convertView;
}}
