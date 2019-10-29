package com.example.soulbook.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.soulbook.AddMoodActivity;
import com.example.soulbook.LogInPage;
import com.example.soulbook.MainActivity;
import com.example.soulbook.R;
import com.example.soulbook.User;
import com.example.soulbook.datasave;
import com.example.soulbook.mood;
import com.example.soulbook.moodListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    TextView homepageNickname, test;
    ListView homepagemoodlist;
    ImageButton homepageAddmood;
    ArrayList<String> moods;
    ArrayList<String> nicknames, hours;
    ArrayList<mood> moodlist;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moods = new ArrayList<>();
        nicknames = new ArrayList<>();
        moodlist = new ArrayList<>();
        hours = new ArrayList<>();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String s) {
                homepageNickname = root.findViewById(R.id.homepage_nickname);
                homepagemoodlist = root.findViewById(R.id.homepage_moodlist);
                test = root.findViewById(R.id.homepage_test);
                homepageAddmood = root.findViewById(R.id.homepage_addmood);
                final String UserId = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        homepageNickname.setText(dataSnapshot.child("users").child(UserId).child("nickname").getValue().toString());
                        moods = (ArrayList<String>) dataSnapshot.child("users").child(UserId).child("moods").getValue();
                        if (moods == null){
                            moods = new ArrayList<>();
                        }
                        String posterId;
                        for (int i = 0; i < moods.size(); i++){
                            moodlist.add(new mood((HashMap)dataSnapshot.child("moods").child(moods.get(i)).getValue()));
                            hours.add(dataSnapshot.child("moods").child(moods.get(i)).child("time").child("hour").getValue().toString());
                            posterId = dataSnapshot.child("moods").child(moods.get(i)).child("poster").getValue().toString();
                            nicknames.add(dataSnapshot.child("users").child(posterId).child("nickname").getValue().toString());
                        }
                        homepagemoodlist.setAdapter(new moodListAdapter(getContext(), moodlist, nicknames, hours));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                homepageAddmood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), AddMoodActivity.class));

                    }
                });
            }
        });
        return root;
    }

}