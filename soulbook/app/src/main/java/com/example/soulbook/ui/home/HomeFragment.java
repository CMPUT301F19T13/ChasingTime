package com.example.soulbook.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * This is a class that get the mainpage view of the app, and how the fragment react to oncreate
 */
public class HomeFragment extends Fragment{
    private HomeViewModel homeViewModel;
    TextView homepageNickname, test;
    ListView homepagemoodlist;
    ArrayList<String> moods;
    ArrayList<String> nicknames;
    ArrayList<mood> moodlist;
    FloatingActionButton homepageShowButton;
    boolean showDetail = false;
    FloatingActionButton homepageAddmood;

    /**
     * This method shows the nicknames and historical mood of current user
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nicknames = new ArrayList<>();
        moodlist = new ArrayList<>();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            /**
             * This method show the four buttons on the top of the main page
             * nickname, addmood, personal icon, and show details of each mood
             * @param s
             */
            @Override
            public void onChanged(@Nullable final String s) {
                homepageNickname = root.findViewById(R.id.homepage_nickname);
                homepagemoodlist = root.findViewById(R.id.homepage_moodlist);
                homepageShowButton = root.findViewById(R.id.homepage_showbutton);
                homepageAddmood = root.findViewById(R.id.homepage_addmood);
                final String UserId = FirebaseAuth.getInstance().getUid();
                homepageShowButton.setOnClickListener(new View.OnClickListener() {
                    /**
                     * after clicked the show details buttons, let each mood shows the details
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        if (showDetail){
                            showDetail = false;
                            homepagemoodlist.setAdapter(new moodListAdapter(getContext(), moodlist, nicknames, moods, HomeFragment.this, showDetail));
                        }
                        else{
                            showDetail = true;
                            homepagemoodlist.setAdapter(new moodListAdapter(getContext(), moodlist, nicknames, moods, HomeFragment.this, showDetail));
                        }
                    }
                });
                Toast.makeText(getContext(), String.valueOf(datasave.thisuser.getMoods().size()), Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        moods = new ArrayList<>();
                        homepageNickname.setText(dataSnapshot.child("users").child(UserId).child("nickname").getValue().toString());
                        ArrayList<String> theMoods = new ArrayList<>();
                        for (int i = 0; i < datasave.thisuser.getFriends().size(); i++){
                            theMoods = new ArrayList<>();
                            theMoods = (ArrayList<String>) dataSnapshot.child("users").child(datasave.thisuser.getFriends().get(i)).child("moods").getValue();
                            if (theMoods == null){
                                theMoods = new ArrayList<>();
                            }
                            moods.addAll(theMoods);
                        }
                        //moods = (ArrayList<String>) dataSnapshot.child("users").child(UserId).child("moods").getValue();
                        //if (moods == null){
                        //    moods = new ArrayList<>();
                        //}
                        Collections.sort(moods);
                        String posterId;
                        for (int i = 0; i < moods.size(); i++){
                            moodlist.add(new mood((HashMap)dataSnapshot.child("moods").child(moods.get(i)).getValue()));
                            posterId = dataSnapshot.child("moods").child(moods.get(i)).child("poster").getValue().toString();
                            nicknames.add(dataSnapshot.child("users").child(posterId).child("nickname").getValue().toString());
                        }
                        homepagemoodlist.setAdapter(new moodListAdapter(getContext(), moodlist, nicknames, moods, HomeFragment.this, showDetail));
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

    /**
     * delete a moood
     * @param postition
     */
    public void removemood(int postition){
        moodlist.remove(postition);
        nicknames.remove(postition);
        FirebaseDatabase.getInstance().getReference().child("moods").child(moods.get(postition)).setValue(null);
        ArrayList<String> userMood = datasave.thisuser.getMoods();
        userMood.remove(moods.get(postition));
        moods.remove(postition);
        datasave.thisuser.setMoods(userMood);
        FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("moods").setValue(userMood);
        homepagemoodlist.setAdapter(new moodListAdapter(getContext(), moodlist, nicknames, moods, HomeFragment.this, showDetail));
    }
}
