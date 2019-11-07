package com.example.soulbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulbook.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMoodActivity extends AppCompatActivity {
    private TextView addmoodpagenickname, addmoodlocation;
    private ImageView[] photos = new ImageView[9];
    private Uri[] photourl = new Uri[9];
    private EditText addmoodpagewritecontent;
    private ImageButton addmoodpageaddmood;
    private ImageButton addmoodpagebackbutton, addmoodpageaddphoto;
    private Spinner addmoodpageemtion;
    List<String> moods;
    private String emotion = "no feeling";
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
        addmoodpageaddmood = findViewById(R.id.addmoodpage_addmood);
        addmoodpagenickname = findViewById(R.id.addmoodpage_nickname);
        addmoodpagewritecontent = findViewById(R.id.addmoodpage_content);
        addmoodpagebackbutton = findViewById(R.id.addmoodpage_back);
        addmoodlocation = findViewById(R.id.addmoodpage_location);
        addmoodpageemtion = findViewById(R.id.addmoodpage_emotionspinner);
        addmoodpageaddphoto = findViewById(R.id.addphoto);
        photos[0] = findViewById(R.id.photo1);
        photos[1] = findViewById(R.id.photo2);
        photos[2] = findViewById(R.id.photo3);
        photos[3] = findViewById(R.id.photo4);
        photos[4] = findViewById(R.id.photo5);
        photos[5] = findViewById(R.id.photo6);
        photos[6] = findViewById(R.id.photo7);
        photos[7] = findViewById(R.id.photo8);
        photos[8] = findViewById(R.id.photo9);

        addmoodpageaddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadphoto();
            }
        });

        addmoodpagenickname.setText(datasave.thisuser.getNickname());

        addmoodpageemtion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emotion = datasave.emotions[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addmoodpagebackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMoodActivity.this, MainActivity.class));
            }
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moods = (ArrayList<String>) dataSnapshot.child("moods").getValue();
                if (moods == null){
                    moods = new ArrayList<>();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addmoodpageaddmood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addmoodpagewritecontent.getText().toString() == ""){
                    Toast.makeText(AddMoodActivity.this, "post mood fail, please write something", Toast.LENGTH_SHORT).show();
                }
                else{
                    Calendar calendar = Calendar.getInstance();
                    time moodtime = new time(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                    mood newMood = new mood(addmoodpagewritecontent.getText().toString(), datasave.UserId, moodtime, emotion, i);
                    final String moodId = FirebaseDatabase.getInstance().getReference().child("moods").push().getKey();

                    FirebaseDatabase.getInstance().getReference().child("moods").child(moodId).setValue(newMood.tomap());

                    storephotoToFirebase(moodId);
                    moods.add(moodId);
                    FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("moods").setValue(moods);
                }
                startActivity(new Intent(AddMoodActivity.this, MainActivity.class));
            }
        });

    }

    private void storephotoToFirebase(final String moodId) {
        final StorageReference sf = FirebaseStorage.getInstance().getReference().child("moodphoto");
        for (int j = 0; j < i; j++){
            sf.child(moodId).child(String.valueOf(j) + ".jpg").putFile(photourl[j]).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }
        return;
    }

    private void uploadphoto() {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(in, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            if (i < 9){
                photos[i].setImageURI(data.getData());
                photourl[i] = data.getData();
                i++;
            }
            else{
                Toast.makeText(AddMoodActivity.this, "You can only add 9 photoes", Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
