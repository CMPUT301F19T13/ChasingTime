package com.example.soulbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.soulbook.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class moodListAdapter extends BaseAdapter {
    private Context context;
    ArrayList<mood> moods;
    ArrayList<String> nicknames, moodId;
    private LayoutInflater mLayoutInflater;
    private ImageView listViewAvatar;
    private TextView listViewNickName, listViewMoodText, listViewTime, listViewLocation, listViewLikeList, listViewEmoji;
    private ImageButton deleteButton, likeButton;
    private String nickname;
    private HomeFragment m;

    public moodListAdapter(Context context, ArrayList<mood> moods, ArrayList<String> nicknames, ArrayList<String> moodId, HomeFragment m){
        this.m = m;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.moods = moods;
        this.nicknames = nicknames;
        this.moodId = moodId;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.mood_view_content, null);
        listViewEmoji = convertView.findViewById(R.id.listview_emoji);
        listViewAvatar = convertView.findViewById(R.id.listview_avatar);
        listViewNickName = convertView.findViewById(R.id.listview_nickname);
        listViewMoodText = convertView.findViewById(R.id.listview_mood_text);
        listViewTime = convertView.findViewById(R.id.listview_mood_time);
        listViewLocation = convertView.findViewById(R.id.listview_location);
        listViewLikeList = convertView.findViewById(R.id.listview_like_list);
        deleteButton = convertView.findViewById(R.id.listview_delete_mood);
        likeButton = convertView.findViewById(R.id.listview_like_button);
        final mood thismood = moods.get(position);
        final String Id = moodId.get(position);

        likeButton.setVisibility(View.INVISIBLE);

        listViewEmoji.setText(new String(Character.toChars(emotionToEmojiUnicode(thismood.getEmotion()))));
        if (thismood.getPoster().equals(datasave.UserId)){
            deleteButton.setVisibility(View.VISIBLE);
        }
        else{
            deleteButton.setVisibility(View.INVISIBLE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasave a = new datasave();
                m.removemood(position);
            }
        });
        listViewNickName.setText(nicknames.get(position));
        listViewMoodText.setText(thismood.getContent());
        listViewTime.setText(thismood.getTime().printTime());
        return convertView;
    }

    public int emotionToEmojiUnicode(String emotion){
        switch (emotion){
            case "Happiness":
                return 0x1F603;
            case "Fear":
                return 0x1F628;
            case "Sadness":
                return 0x1F62D;
            case "Anger":
                return 0x1F620;
            case "Surprise":
                return 0x1F603;
            case "Disgust":
                return 0x1F635;
            case "Excitement":
                return 0x1F606;
        }
        return 0x1F251;
    }

}
