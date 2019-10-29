package com.example.soulbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInPage extends AppCompatActivity {
    private EditText useremail;
    private EditText password;
    private Button login;
    private Button signup;
    private DatabaseReference DataBase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        mAuth = FirebaseAuth.getInstance();

        useremail = findViewById(R.id.userEmail);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signUpButton);

        DataBase = FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LogInPage.this, SignUpPage.class);
                startActivity(in);
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = useremail.getText().toString();
                final String passWord = password.getText().toString();
                if (userEmail == ""){
                    Toast.makeText(LogInPage.this, "Please enter an email", Toast.LENGTH_LONG).show();
                }
                else if(passWord == ""){
                    Toast.makeText(LogInPage.this, "Please enter a password", Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(userEmail, passWord).addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String UserId = FirebaseAuth.getInstance().getUid();
                                        FirebaseDatabase.getInstance().getReference().child("users").child(UserId).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                datasave.thisuser.setNickname(dataSnapshot.child("nickname").getValue().toString());
                                                datasave.thisuser.setEmail(dataSnapshot.child("email").getValue().toString());
                                                datasave.UserId = FirebaseAuth.getInstance().getUid().toString();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        startActivity(new Intent(LogInPage.this, MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(LogInPage.this, "Log in fail", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
