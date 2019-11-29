package com.example.soulbook.ui.dashboard;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.soulbook.R;
import com.example.soulbook.datasave;
import com.example.soulbook.ui.notifications.settingPage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

/**
* This is a class that get the dashboard of the app which includes dashboardview, messages, friends and nickname
*/

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView messages;
    private TextView friends;
    private TextView nickname;
    private ImageView avatar;
    
    /**
     * This connects all of the buttons and texts from app design to code that be written to implement their functions
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the views of dashboard
     */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        messages = root.findViewById(R.id.messagepage_message);
        friends = root.findViewById(R.id.messagepage_friends);
        nickname = root.findViewById(R.id.messagepage_nickname);
        avatar = root.findViewById(R.id.messagefage_avatar);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            
            /**
             * This connects buttons of dashboard from app design to code
             */
            @Override
            public void onChanged(@Nullable String s) {
                nickname.setText(datasave.thisuser.getNickname());
                messages.setOnClickListener(new View.OnClickListener() {
                    
                    /**
                     * This connects message button of dashboard design with code
                     */
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), message.class));
                    }
                });
                friends.setOnClickListener(new View.OnClickListener() {
                    
                    /**
                     * This connects friend button of dashboard design with code
                     */
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), friend.class));
                    }
                });
            }
        });

        if (datasave.avatar != null){
            Glide.with(getContext()).load(datasave.avatar).into(avatar);
        }
        return root;
    }
}
