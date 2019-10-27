package com.example.soulbook.ui.home;


import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

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
    ImageButton homepageAddmood;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                homepageNickname = root.findViewById(R.id.homepage_nickname);
                test = root.findViewById(R.id.homepage_test);
                homepageAddmood = root.findViewById(R.id.homepage_addmood);
                final String UserId = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase.getInstance().getReference().child("users").child(UserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        homepageNickname.setText(dataSnapshot.child("nickname").getValue().toString());

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