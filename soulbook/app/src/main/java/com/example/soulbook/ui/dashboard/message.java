package com.example.soulbook.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.soulbook.R;
import com.example.soulbook.datasave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This is a class that shows the interface of message in dashboard
 * It's not complete
 */

public class message extends AppCompatActivity implements passAddFriendFragment.OnFragmentInteractionListener{
    ImageButton message_back_button;
    ListView message_list;
    ArrayList<String> Ids;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> nicknames;
    ArrayList<String> friends, anotherFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        message_back_button = findViewById(R.id.message_back);
        message_list = findViewById(R.id.message_listview);
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Ids = new ArrayList<>();
                nicknames = new ArrayList<>();
                HashMap a = (HashMap)dataSnapshot.child(datasave.UserId).child("message").getValue();
                if(a != null){
                    Set<String> set= a.keySet();
                    Iterator<String> It = set.iterator();
                    while (It.hasNext()){
                        Ids.add(It.next());
                    }

                    for(int i = 0; i < Ids.size(); i++){
                        messages.add(a.get(Ids.get(i)).toString());
                        nicknames.add(dataSnapshot.child(Ids.get(i)).child("nickname").getValue().toString());
                    }
                    message_list.setAdapter(new messageListAdapter(message.this,nicknames, messages));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passAddFriendFragment a = new passAddFriendFragment();
                Bundle b = new Bundle();
                b.putInt("pos", position);
                a.setArguments(b);
                a.show(getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    public void onYesPressed(final int position) {
        friends = new ArrayList<>();
         anotherFriends = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(datasave.UserId).child("friends").getValue() == null){
                    friends.add(Ids.get(position));
                }
                else{
                    friends = (ArrayList)dataSnapshot.child(datasave.UserId).child("friends").getValue();
                    friends.add(Ids.get(position));
                }

                if (dataSnapshot.child(Ids.get(position)).child("friends").getValue() == null){
                    anotherFriends.add(datasave.UserId);
                }
                else{
                    anotherFriends = (ArrayList)dataSnapshot.child(datasave.UserId).child("friends").getValue();
                    anotherFriends.add(datasave.UserId);
                }
                FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("friends").setValue(friends);
                FirebaseDatabase.getInstance().getReference().child("users").child(Ids.get(position)).child("friends").setValue(anotherFriends);
                FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("message").child(Ids.get(position)).setValue(null);
                Ids.remove(position);
                messages.remove(position);
                nicknames.remove(position);
                message_list.setAdapter(new messageListAdapter(message.this,nicknames, messages));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNoPressed(int position) {
        FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("message").child(Ids.get(position)).setValue(null);
        Ids.remove(position);
        messages.remove(position);
        nicknames.remove(position);
        message_list.setAdapter(new messageListAdapter(message.this,nicknames, messages));
    }
}
