package com.example.soulbook;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class datasave {
    public static User thisuser = new User();
    public static String UserId = "未读取";
    private ArrayList<String> moods;
    public static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public datasave(){
    }

    public void removemood(mood m, String moodId){
        final String UserId = m.getPoster();
        final String Id = moodId;
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moods = (ArrayList<String>) dataSnapshot.child("users").child(UserId).child("moods").getValue();
                moods.remove(Id);
                database.child("users").child(UserId).child("moods").setValue(moods);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        database.child("moods").child(moodId).setValue(null);

    }
}
