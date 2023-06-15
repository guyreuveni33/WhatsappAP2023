package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        TextView tvTimestamp = convertView.findViewById(message.isSent() ? R.id.tvTimestampSent : R.id.tvTimestampReceived);

        tvMessage.setText(message.getContent());
        tvTimestamp.setText(message.getFormattedTimestamp());

        return convertView;
    }
}
