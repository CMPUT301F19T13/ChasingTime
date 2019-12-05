package com.example.soulbook;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This is for data uploading to firebase database
 */
public class datasave {
    //save new username
    public static User thisuser = new User();
    public static String UserId = "未读取";
    private ArrayList<String> moods;
    public static String[] emotions = {"Happiness", "Fear", "Sadness", "Anger", "Surprise", "Disgust", "Excitement"};
    public static int[] emoji = {0x1F603, 0x1F628, 0x1F62D ,0x1F620, 0x1F603, 0x1F635, 0x1F606};
    public static String[] socialSit = {"Alone", "With families", "With friends", "With strangers"};
    public static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    public static Uri avatar = null;
    /**
     * constructor
     */
    public datasave(){
    }

    /**
     * a method to delete a current existing mood
     * @param m
     *   mood m
     * @param moodId
     *   moodId of this mood
     */
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

    /**
     * a method to get user's nickname given ID
     * @param Id
     *   user's ID
     * @return
     *   Return to the desired nickname
     */
    public String getNicknameById(String Id){
        final String[] result = new String[1];
        database.child("users").child(Id).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                result[0] = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                result[0] = null;

            }
        });
        return result[0];
    }

    /**
     * a method to get user's ID by email address
     * @param email
     *   user's email address
     * @param context
     *  a useful context for Toast
     * @return
     *   return the user's ID
     */
    public String getIdByEmail(final String email, final Context context){
        final String[] result = new String[1];
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("emailToId").child(email).getValue().toString() == null){
                    Toast.makeText(context, "Unable to find user by email", Toast.LENGTH_LONG).show();
                }
                else{
                    result[0] = dataSnapshot.child("emailToId").child(email).getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return result[0];

    }
}
