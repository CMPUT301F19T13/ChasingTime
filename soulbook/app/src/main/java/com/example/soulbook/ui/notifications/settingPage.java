package com.example.soulbook.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soulbook.AddMoodActivity;
import com.example.soulbook.MainActivity;
import com.example.soulbook.R;
import com.example.soulbook.datasave;
import com.example.soulbook.mood;
import com.example.soulbook.ui.dashboard.searchFriend;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class settingPage extends AppCompatActivity {

    TextView setNickname, setAvatar;
    ImageButton back;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        back = findViewById(R.id.settingpage_backbutton);
        setNickname = findViewById(R.id.settingpage_changeNickname);
        setAvatar = findViewById(R.id.settingpage_changeAvatar);
        avatar = findViewById(R.id.setting_avatar);
        setNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new setNicknameFragment().show(getSupportFragmentManager(), "set new nickname");
            }
        });

        //Toast.makeText(settingPage.this, datasave.thisuser.getAvatarPath().toString().length() + datasave.thisuser.getAvatarPath().toString() + ":::", Toast.LENGTH_LONG).show();

        if (datasave.thisuser.getAvatarPath().length() != 0){
            FirebaseStorage.getInstance().getReference().child("avatar").child(datasave.UserId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(settingPage.this)
                            .load(uri)
                            .into(avatar);
                }
            });
        }
        setAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadphoto();
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

    private void uploadphoto() {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(in, 10);
    }


    @Override
    /**
     * check the number of photos no more than 9
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            avatar.setImageURI(data.getData());
            FirebaseStorage.getInstance().getReference().child("avatar").child(datasave.UserId).putFile(data.getData()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(settingPage.this, "youwenti", Toast.LENGTH_LONG).show();
                }
            });
            FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("AvatarPath").setValue("1");
            datasave.thisuser.setAvatarPath("1");
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
