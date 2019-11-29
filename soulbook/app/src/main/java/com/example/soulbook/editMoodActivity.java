package com.example.soulbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soulbook.ui.dashboard.deletePhotoDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class editMoodActivity extends AppCompatActivity implements deletePhotoDialog.OnFragmentInteractionListener{
    private TextView nickname;
    private ImageView[] photos = new ImageView[9];
    private Uri[] photourl = new Uri[9];
    private EditText content;
    private ImageButton editmood;
    private ImageButton backbutton, addphoto, deletephoto;
    private Spinner emotionSpinner, socialSituation;
    ArrayList<String> moods;
    private String emotion = "no feeling";
    private String social = null;
    private int photonumber = 0;
    String moodId;
    int intentTo;
    mood thismood;
    LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(0,0);
    LinearLayout.LayoutParams mParams2 = new LinearLayout.LayoutParams(300,300);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);
        nickname = findViewById(R.id.editmood_nickname);
        photos[0] = findViewById(R.id.photo1);
        photos[1] = findViewById(R.id.photo2);
        photos[2] = findViewById(R.id.photo3);
        photos[3] = findViewById(R.id.photo4);
        photos[4] = findViewById(R.id.photo5);
        photos[5] = findViewById(R.id.photo6);
        photos[6] = findViewById(R.id.photo7);
        photos[7] = findViewById(R.id.photo8);
        photos[8] = findViewById(R.id.photo9);
        content = findViewById(R.id.editmood_content);
        editmood = findViewById(R.id.editmood_editmood);
        backbutton = findViewById(R.id.editmood_back);
        addphoto = findViewById(R.id.editmood_addphoto);
        emotionSpinner = findViewById(R.id.editmood_emotionspinner);
        socialSituation = findViewById(R.id.editmood_socialsit);
        Intent in = getIntent();
        moodId = in.getStringExtra("moodId");
        intentTo = in.getIntExtra("from", 0);
        nickname.setText(datasave.thisuser.getNickname());
        for (int i = photonumber ;i < 9; i++){
            photos[i].setLayoutParams(mParams);
        }

        for (int i = 0; i < photonumber; i++){
            photos[i].setLayoutParams(mParams2);
        }

        FirebaseDatabase.getInstance().getReference().child("moods").child(moodId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                thismood = new mood((HashMap)dataSnapshot.getValue());
                photonumber = thismood.getPhotonumber();
                content.setText(thismood.getContent());
                emotionSpinner.setSelection(findEmotionPosition(thismood.getEmotion()));
                emotion = thismood.getEmotion();
                if (thismood.getSocialSit() != null){
                    socialSituation.setSelection(findSocialSitPos(thismood.getSocialSit()));
                    social = thismood.getSocialSit();
                }


                for (int i = 0; i < photonumber; i++){
                    setImageView(moodId, thismood, photos[i], i);
                    //photourl[i] = FirebaseStorage.getInstance().getReference().child("moodphoto").child(moodId).child(String.valueOf(i)).getDownloadUrl().getResult();
                    //FirebaseStorage.getInstance().getReference().child("moodphoto").child(moodId).child(String.valueOf(i) + ".jpg").getFile(photourl[i]);
                }
                for (int i = photonumber ;i < 9; i++){
                    photos[i].setLayoutParams(mParams);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentTo == 1){
                    startActivity(new Intent(editMoodActivity.this, friend_mood_view.class));
                }
                else{
                    startActivity(new Intent(editMoodActivity.this, MainActivity.class));
                }
            }
        });

        emotionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emotion = datasave.emotions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        socialSituation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    social = datasave.socialSit[position-1];
                }
                else{
                    social = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



                    addphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadphoto();
                }
            });

            for (int i = 0; i < 9; i++){
                final int finalI = i;
                photos[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePhotoDialog d = new deletePhotoDialog();
                    Bundle b = new Bundle();
                    b.putInt("position", finalI);
                    d.setArguments(b);
                    d.show(getSupportFragmentManager(), "if delete photo");
                }
            });
        }

        editmood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.getText().toString().length() == 0){
                    //Toast.makeText(editMoodActivity.this, " youwenti", Toast.LENGTH_LONG).show();

                }
                else{
                    thismood.setEmotion(emotion);
                    thismood.setSocialSit(social);
                    thismood.setPhotonumber(photonumber);
                    FirebaseDatabase.getInstance().getReference().child("moods").child(moodId).setValue(thismood.tomap());
                    storephotoToFirebase(moodId);
                }

                if (intentTo == 1){
                    startActivity(new Intent(editMoodActivity.this, friend_mood_view.class));
                }
                else{
                    startActivity(new Intent(editMoodActivity.this, MainActivity.class));
                }
            }
        });
    }

    public int findEmotionPosition(String emotion){
        switch (emotion){
            case "Happiness":
                return 0;
            case "Fear":
                return 1;
            case "Sadness":
                return 2;
            case "Anger":
                return 3;
            case "Surprise":
                return 4;
            case "Disgust":
                return 5;
            case "Excitement":
                return 6;
        }
        return 6;
    }

    public int findSocialSitPos(String socialSituation){
        switch (socialSituation){
            case "Alone":
                return 1;
            case "With families":
                return 2;
            case "With friends":
                return 3;
            case "With stranger":
                return 4;
        }
        return 0;
    }

    private void setImageView(String moodId, mood m, final ImageView iv, int i){
        StorageReference s = FirebaseStorage.getInstance().getReference().child("moodphoto").child(moodId).child(i + ".jpg");
        s.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(editMoodActivity.this)
                        .load(uri)
                        .into(iv);
            }
        });
    }

    private void uploadphoto() {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(in, 10);
    }

    private void storephotoToFirebase(final String moodId) {
        final StorageReference sf = FirebaseStorage.getInstance().getReference().child("moodphoto");
        for (int j = 0; j < photonumber; j++){
            sf.child(moodId).child(String.valueOf(j) + ".jpg").putFile(photourl[j]).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }
        return;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            if (photonumber < 9){
                photos[photonumber].setImageURI(data.getData());
                photourl[photonumber] = data.getData();
                photos[photonumber].setLayoutParams(mParams2);
                photonumber++;
            }
            else{
                Toast.makeText(editMoodActivity.this, "You can only add 9 photoes", Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onYesPressed(int position) {
        Uri[] newPhotourl = new Uri[9];
        for (int i = 0; i < position; i++){
            newPhotourl[i] = photourl[i];
        }

        for (int i = position + 1; i < photonumber; i++){
            newPhotourl[i-1] = photourl[i];
        }

        photourl = newPhotourl;
        photonumber--;
        for (int i = 0; i < photonumber; i++){
            photos[i].setImageURI(photourl[i]);
        }
        for (int i = photonumber ;i < 9; i++){
            photos[i].setLayoutParams(mParams);
        }

        for (int i = 0; i < photonumber; i++){
            photos[i].setLayoutParams(mParams2);
        }

    }
}
