package com.example.soulbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMoodActivity extends AppCompatActivity {
    private TextView addmoodpagenickname, addmoodlocation;
    private EditText addmoodpagewritecontent;
    private ImageButton addmoodpageaddmood;
    private ImageButton addmoodpagebackbutton;
    private Spinner addmoodpageemtion;
    List<String> moods;
    private String emotion = "no feeling";

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
                    mood newMood = new mood(addmoodpagewritecontent.getText().toString(), datasave.UserId, moodtime, emotion);
                    final String moodId = FirebaseDatabase.getInstance().getReference().child("moods").push().getKey();

                    FirebaseDatabase.getInstance().getReference().child("moods").child(moodId).setValue(newMood.tomap());
                    moods.add(moodId);
                    FirebaseDatabase.getInstance().getReference().child("users").child(datasave.UserId).child("moods").setValue(moods);
                }
                startActivity(new Intent(AddMoodActivity.this, MainActivity.class));
            }
        });

    }
}
