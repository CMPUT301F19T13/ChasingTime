package com.example.soulbook.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.soulbook.R;
import com.example.soulbook.datasave;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView messages;
    private TextView friends;
    private TextView nickname;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        messages = root.findViewById(R.id.messagepage_message);
        friends = root.findViewById(R.id.messagepage_friends);
        nickname = root.findViewById(R.id.messagepage_nickname);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                nickname.setText(datasave.thisuser.getNickname());
                messages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), message.class));
                    }
                });
                friends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), friend.class));
                    }
                });
            }
        });
        return root;
    }
}