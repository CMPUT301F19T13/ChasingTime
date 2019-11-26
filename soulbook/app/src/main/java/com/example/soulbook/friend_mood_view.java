package com.example.soulbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.soulbook.ui.dashboard.friend;
import com.example.soulbook.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class friend_mood_view extends AppCompatActivity {
    TextView nickname;
    Button showButton;
    ListView moodList;
    Boolean showDetail = false;
    ArrayList<String> moods;
    ArrayList<String> nicknames;
    ArrayList<mood> moodlist;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_mood_view);
        Intent in = getIntent();
        final String userId = in.getStringExtra("userId");
        nickname = findViewById(R.id.friend_mood_view_nickname);
        showButton = findViewById(R.id.friend_mood_view_showbutton);
        moodList = findViewById(R.id.friend_mood_view_moodlist);
        back = findViewById(R.id.friend_mood_view_back);
        showButton.setOnClickListener(new View.OnClickListener() {
            /**
             * after clicked the show details buttons, let each mood shows the details
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (showDetail){
                    showDetail = false;
                    moodList.setAdapter(new moodListAdapter(friend_mood_view.this, moodlist, nicknames, moods, showDetail));
                }
                else{
                    showDetail = true;
                    moodList.setAdapter(new moodListAdapter(friend_mood_view.this, moodlist, nicknames, moods, showDetail));
                }
            }
        });

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moods = new ArrayList<>();
                moodlist = new ArrayList<>();
                nicknames = new ArrayList<>();
                nickname.setText(dataSnapshot.child("users").child(userId).child("nickname").getValue().toString());
                moods = (ArrayList<String>) dataSnapshot.child("users").child(userId).child("moods").getValue();
                if (moods == null){
                    moods = new ArrayList<>();
                }
                String posterId;
                for (int i = 0; i < moods.size(); i++){
                    moodlist.add(new mood((HashMap)dataSnapshot.child("moods").child(moods.get(i)).getValue()));
                    posterId = dataSnapshot.child("moods").child(moods.get(i)).child("poster").getValue().toString();
                    nicknames.add(dataSnapshot.child("users").child(posterId).child("nickname").getValue().toString());
                }
                moodList.setAdapter(new moodListAdapter(friend_mood_view.this, moodlist, nicknames, moods, showDetail));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(friend_mood_view.this, friend.class);
                startActivity(in);
            }
        });
    }
}
