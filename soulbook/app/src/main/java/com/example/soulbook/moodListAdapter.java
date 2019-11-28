package com.example.soulbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.soulbook.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is a class that shows the mode list 
 */
public class moodListAdapter extends BaseAdapter {
    private Context context;
    ArrayList<mood> moods;
    ArrayList<String> nicknames, moodId;
    private LayoutInflater mLayoutInflater;
    private ImageView listViewAvatar, photos[] = new ImageView[9];
    private TextView listViewNickName, listViewMoodText, listViewTime, listViewLocation, listViewLikeList, listViewEmoji, listViewSocialSit, listViewEmojiText;
    private ImageButton deleteButton, likeButton;
    private String nickname;
    private HomeFragment m;
    private boolean ifshow;
    ArrayList<File> photoFile;

    public moodListAdapter(Context context, ArrayList<mood> moods, ArrayList<String> nicknames, ArrayList<String> moodId, HomeFragment m, boolean show){
        this.m = m;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.moods = moods;
        this.nicknames = nicknames;
        this.moodId = moodId;
        ifshow = show;
    }

    public moodListAdapter(Context context, ArrayList<mood> moods, ArrayList<String> nicknames, ArrayList<String> moodId, boolean show){
        this.m = null;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.moods = moods;
        this.nicknames = nicknames;
        this.moodId = moodId;
        ifshow = show;

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
        listViewSocialSit = convertView.findViewById(R.id.listview_socialSit);
        listViewEmojiText = convertView.findViewById(R.id.listview_emojiText);
        photos[0] = convertView.findViewById(R.id.listview_photo1);
        photos[1] = convertView.findViewById(R.id.listview_photo2);
        photos[2] = convertView.findViewById(R.id.listview_photo3);
        photos[3] = convertView.findViewById(R.id.listview_photo4);
        photos[4] = convertView.findViewById(R.id.listview_photo5);
        photos[5] = convertView.findViewById(R.id.listview_photo6);
        photos[6] = convertView.findViewById(R.id.listview_photo7);
        photos[7] = convertView.findViewById(R.id.listview_photo8);
        photos[8] = convertView.findViewById(R.id.listview_photo9);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(0,0);
        LinearLayout.LayoutParams mParams2 = new LinearLayout.LayoutParams(300,300);
        for (int i = 0 ;i < 9; i++){
            photos[i].setLayoutParams(mParams);
        }
        final mood thismood = moods.get(moods.size() - 1 - position);
        final String Id = moodId.get(moods.size() - 1 - position);
        //Toast.makeText(context,String.valueOf(thismood.getPhotonumber() + ":" + photoFile.size()), Toast.LENGTH_LONG).show();
        likeButton.setVisibility(View.INVISIBLE);
        listViewLikeList.setHeight(0);

        if (thismood.getLocation() !=  "null" && thismood.getLocation() != null){
            listViewLocation.setText(thismood.getLocation());
        }
        else{
            listViewLocation.setText("");
        }

        if (thismood.getSocialSit() != null && thismood.getSocialSit() != "null"){
                listViewSocialSit.setText(thismood.getSocialSit());
            }
        else{
                listViewSocialSit.setText("");
        }

        listViewEmojiText.setText(thismood.getEmotion());
        listViewEmojiText.setBackgroundColor(Color.parseColor(getEmotionColor(thismood.getEmotion())));
        if (thismood.getEmotion() == "Sadness" || thismood.getEmotion() == "Fear" || thismood.getEmotion() == "Disgust"){
            listViewEmojiText.setTextColor(Color.parseColor("#ffffff"));
        }

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
                if (m != null){
                    m.removemood(moods.size() - 1 -position);
                }
            }
        });
        if (ifshow){
            listViewMoodText.setText(thismood.getContent());
            for (int i = 0; i <thismood.getPhotonumber(); i++){
                photos[i].setLayoutParams(mParams2);
                setImageView(Id, thismood, photos[i], i);
            }
        }
        else{
            listViewMoodText.setHeight(0);
        }
        listViewNickName.setText(nicknames.get(moods.size() - 1 -position));
        listViewTime.setText(thismood.getTime().printTime());
        return convertView;
    }
    
    /**
     * this implements different emotion cases
     * @param emotion
         different kinds of emotions
     */

    public String getEmotionColor(String emotion){
        switch (emotion){
            case "Happiness":
                return "#f0f037";
            case "Fear":
                return "#c1de83";
            case "Sadness":
                return "#c9c8c7";
            case "Anger":
                return "#c42b38";
            case "Surprise":
                return "#c4baf7";
            case "Disgust":
                return "#e8cced";
            case "Excitement":
                return "#9fe4ed";
        }
        return "#f0f037";
    }

    public int emotionToEmojiUnicode(String emotion){
        switch (emotion){
            case "Happiness":
                return 0x1F604;
            case "Fear":
                return 0x1F628;
            case "Sadness":
                return 0x1F62D;
            case "Anger":
                return 0x1F620;
            case "Surprise":
                return 0x1F929;
            case "Disgust":
                return 0x1F635;
            case "Excitement":
                return 0x1F606;
        }
        return 0x1F251;
    }

    /**
     * a method to load image into imageview by glide
     * @param moodId
     *   Id of the mood
     * @param m
     *   mood m
     * @param iv
     * ImageView of image
     * @param i
     * index of image position
     * @return
     */
    private void setImageView(String moodId, mood m, final ImageView iv, int i){
        StorageReference s = FirebaseStorage.getInstance().getReference().child("moodphoto").child(moodId).child(i + ".jpg");
        s.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(iv);
            }
        });
    }

}
