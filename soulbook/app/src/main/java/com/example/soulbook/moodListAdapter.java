package com.example.soulbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class moodListAdapter extends BaseAdapter {
    private Context context;
    ArrayList<mood> moods;
    ArrayList<String> nicknames, hours;
    private LayoutInflater mLayoutInflater;
    private ImageView listViewAvatar;
    private TextView listViewNickName, listViewMoodText, listViewTime, listViewLocation, listViewLikeList;
    private ImageButton deleteButton, likeButton;
    private String nickname;

    public moodListAdapter(Context context, ArrayList<mood> moods, ArrayList<String> nicknames){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.moods = moods;
        this.nicknames = nicknames;
        this.hours = hours;
    }
    @Override
    public int getCount() {
        return moods.size();
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
        convertView = mLayoutInflater.inflate(R.layout.mood_view_content, null);
        listViewAvatar = convertView.findViewById(R.id.listview_avatar);
        listViewNickName = convertView.findViewById(R.id.listview_nickname);
        listViewMoodText = convertView.findViewById(R.id.listview_mood_text);
        listViewTime = convertView.findViewById(R.id.listview_mood_time);
        listViewLocation = convertView.findViewById(R.id.listview_location);
        listViewLikeList = convertView.findViewById(R.id.listview_like_list);
        deleteButton = convertView.findViewById(R.id.listview_delete_mood);
        likeButton = convertView.findViewById(R.id.listview_like_button);
        mood thismood = moods.get(position);
        listViewNickName.setText(nicknames.get(position));
        listViewMoodText.setText(thismood.getContent());
        listViewTime.setText(thismood.getTime().printTime());
        return convertView;

    }
}
