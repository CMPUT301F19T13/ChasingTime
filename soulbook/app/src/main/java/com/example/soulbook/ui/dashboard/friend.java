package com.example.soulbook.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.soulbook.MainActivity;
import com.example.soulbook.R;
import com.example.soulbook.datasave;
//import com.google.firebase.auth.FirebaseAuth;
import com.example.soulbook.friend_mood_view;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This is a class that shows the interface of the frined section in dashboard.
 * This class used to search for friends and shows the list of all your friends.
 * This connects the friends section from design to code.
 * This part is not complete.
 */

public class friend extends AppCompatActivity implements searchFriend.OnFragmentInteractionListener, addFriendFragment.OnFragmentInteractionListener{
    //class to handle all friend operations
    private ImageButton searchfriend, backbutton;
    private ListView friendlist;
    private ArrayList<String> friends = new ArrayList<>(), Ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        searchfriend = findViewById(R.id.friendpage_searchfriend);
        backbutton = findViewById(R.id.friendpage_backbutton);
        friendlist = findViewById(R.id.friendpage_friendlist);

        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friends = new ArrayList<>();
                Ids = datasave.thisuser.getFriends();
                    for (int i = 0; i < Ids.size(); i++){
                        friends.add(dataSnapshot.child(Ids.get(i)).child("nickname").getValue().toString());
                    }
                friendlist.setAdapter(new friendListAdapter(friend.this,friends));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new searchFriend().show(getSupportFragmentManager(), "search friend");
            }
        });

        friendlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(friend.this, friend_mood_view.class);
                in.putExtra("userId", Ids.get(position));
                startActivity(in);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(friend.this, MainActivity.class);
                in.putExtra("data", 1);
                startActivity(in);
            }
        });
    }

    @Override
    public void onOkPressed(String email) {
        final String[] Email = email.split("\\.");
        final String[] userId = new String[3];
        if (Email.length != 2){
            Toast.makeText(friend.this,"please enter a correct email adress", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseDatabase.getInstance().getReference().child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Email[1]).child(Email[0]).getValue() == null){
                    Toast.makeText(friend.this,"Cannot find this user, please check the email address", Toast.LENGTH_LONG).show();
                }
                else{
                    userId[0] = dataSnapshot.child(Email[1]).child(Email[0]).getValue().toString();
                    FirebaseDatabase.getInstance().getReference().child("users").child(userId[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            userId[1] = dataSnapshot.child("nickname").getValue().toString();
                            userId[2] = dataSnapshot.child("AvatarPath").getValue().toString();
                            Bundle b = new Bundle();
                            b.putString("userId", userId[0]);
                            b.putString("nickname", userId[1]);
                            b.putString("AvatarPath", userId[2]);
                            addFriendFragment a = new addFriendFragment();
                            a.setArguments(b);
                            a.show(getSupportFragmentManager(), "send add friend requirement");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onYesPressed(String message, String userId) {
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("message").child(datasave.UserId).setValue(message);
    }
}
