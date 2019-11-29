package com.example.soulbook.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.soulbook.R;

import java.util.ArrayList;

public class friendListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> nicknames;
    TextView nickname;

    public friendListAdapter(Context context, ArrayList<String> nickname){
        mLayoutInflater = LayoutInflater.from(context);
        nicknames = nickname;
    }
    public friendListAdapter(ArrayList<String> nickname){
        nicknames = nickname;
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
        convertView = mLayoutInflater.inflate(R.layout.friend_view_content, null);
        nickname = convertView.findViewById(R.id.friendList_nickname);
        nickname.setText(nicknames.get(position));
        return convertView;
    }
}
