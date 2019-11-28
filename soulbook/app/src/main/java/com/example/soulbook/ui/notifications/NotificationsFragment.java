package com.example.soulbook.ui.notifications;

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

import com.example.soulbook.LogInPage;
import com.example.soulbook.R;
import com.example.soulbook.User;
import com.example.soulbook.datasave;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class shows the fragments in notification page if exist
 */
public class NotificationsFragment extends Fragment {

    TextView account, signOut, nickname;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                account = root.findViewById(R.id.settingpage_account);
                nickname = root.findViewById(R.id.settingpage_nickname);
                signOut = root.findViewById(R.id.settingpage_SignOut);

                nickname.setText(datasave.thisuser.getNickname());

                account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), settingPage.class));
                    }
                });
                signOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datasave.thisuser = new User();
                        datasave.UserId = "null";
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LogInPage.class));
                    }
                });
            }
        });
        return root;
    }
}