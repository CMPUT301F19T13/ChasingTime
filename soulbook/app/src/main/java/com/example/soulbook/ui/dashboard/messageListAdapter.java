package com.example.soulbook.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.soulbook.R;
import com.example.soulbook.mood;
import com.example.soulbook.ui.home.HomeFragment;

import java.util.ArrayList;

public class messageListAdapter extends BaseAdapter{
    private ArrayList<String> nicknames, messages;
    private LayoutInflater mLayoutInflater;
    private TextView nickname, message;

    public messageListAdapter(Context context, ArrayList<String> nicknames, ArrayList<String> messages){
        mLayoutInflater = LayoutInflater.from(context);
        this.nicknames = nicknames;
        this.messages = messages;
    }

    public messageListAdapter(ArrayList<String> nicknames, ArrayList<String> messages){
        this.nicknames = nicknames;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return nicknames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.message_view_content, null);
        nickname = convertView.findViewById(R.id.messagelist_nickname);
        message = convertView.findViewById(R.id.messagelist_message);
        nickname.setText(nicknames.get(position));
        message.setText(messages.get(position));
        return convertView;
    }
}
