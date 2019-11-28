package com.example.soulbook.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soulbook.MainActivity;
import com.example.soulbook.R;
import com.example.soulbook.ui.dashboard.searchFriend;

public class settingPage extends AppCompatActivity {

    TextView setNickname, setAvatar;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        back = findViewById(R.id.settingpage_backbutton);
        setNickname = findViewById(R.id.settingpage_changeNickname);
        setAvatar = findViewById(R.id.settingpage_changeAvatar);
        setNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new setNicknameFragment().show(getSupportFragmentManager(), "set new nickname");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(settingPage.this, MainActivity.class);
                in.putExtra("data", 2);
                startActivity(in);
            }
        });
    }
}
