package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Message;

import org.w3c.dom.Text;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private List<Message> messageList;
    LayoutInflater inflater;

    public MessageAdapter(Context context, List<Message> messageList) {
        super(context, 0, messageList);
        this.inflater = LayoutInflater.from(context);
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Message getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvMessage = null, tvTimestamp = null;
            if (messageList.get(position).isSent()) {
                convertView = inflater.inflate(R.layout.item_message_sent, parent,
                        false);
                tvMessage = convertView.findViewById(R.id.tvMessageSent);
                tvTimestamp = convertView.findViewById(R.id.tvTimestampSent);
            } else {
                convertView = inflater.inflate(R.layout.item_message_received, parent,
                        false);
                tvMessage = convertView.findViewById(R.id.tvMessageReceived);
                tvTimestamp = convertView.findViewById(R.id.tvTimestampReceived);
            }


        if (messageList.get(position) == null)
            return null;
        if (messageList.get(position).getTimestamp() != null)
            tvTimestamp.setText(messageList.get(position).getTimestamp());

        if (messageList.get(position).getContent() != null)
            tvMessage.setText(messageList.get(position).getContent());

        return convertView;
    }
}
