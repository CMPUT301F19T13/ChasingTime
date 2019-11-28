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

import java.util.ArrayList;

/**
 * This activity is the login page
 * user can login using userID and password or sign up a new userID
 */
public class LogInPage extends AppCompatActivity {
    private EditText useremail;
    private EditText password;
    private Button login;
    private Button signup;
    private DatabaseReference DataBase;
    FirebaseAuth mAuth;
    private ArrayList<String> friends = new ArrayList<>();

    /**
     * user can login the user account when input correct email address and password
     * user cannot login user account if email address cannot match with password
     * show login fail if email address cannot match with password
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //after click the login button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        mAuth = FirebaseAuth.getInstance();
        //getting the input information
        useremail = findViewById(R.id.userEmail);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signUpButton);
        //connect to firebase database
        DataBase = FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * go to sign up activity if user touch "sign up" button
             */
            public void onClick(View v) {
                Intent in = new Intent(LogInPage.this, SignUpPage.class);
                startActivity(in);
            }
        });
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * input the email address and password for matching
             * email address and password cannot be empty
             */
            public void onClick(View v) {
                final String userEmail = useremail.getText().toString();
                final String passWord = password.getText().toString();
                //if the input email is empty
                if (userEmail == ""){
                    Toast.makeText(LogInPage.this, "Please enter an email", Toast.LENGTH_LONG).show();
                }

                //if the input password is empty
                else if(passWord == ""){
                    Toast.makeText(LogInPage.this, "Please enter a password", Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(userEmail, passWord).addOnCompleteListener(LogInPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                /**
                                 * email address can match with correct password
                                 * print "log in fail" if input wrong email address or input wrong password
                                 */
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //the input email and password is correct
                                    if (task.isSuccessful()) {
                                        final String UserId = FirebaseAuth.getInstance().getUid();
                                        FirebaseDatabase.getInstance().getReference().child("users").child(UserId).addValueEventListener(new ValueEventListener() {

                                            /**
                                             * save user's info
                                             */
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                datasave.thisuser.setNickname(dataSnapshot.child("nickname").getValue().toString());
                                                datasave.thisuser.setEmail(dataSnapshot.child("email").getValue().toString());
                                                datasave.UserId = FirebaseAuth.getInstance().getUid().toString();
                                                friends = (ArrayList)dataSnapshot.child("friends").getValue();
                                                if(friends == null){
                                                    friends = new ArrayList<>();
                                                }
                                                friends.add(0,UserId);
                                                datasave.thisuser.setFriends(friends);
                                                ArrayList<String> moods = (ArrayList<String>) dataSnapshot.child("moods").getValue();
                                                if (moods == null){
                                                    moods = new ArrayList<>();
                                                }
                                                datasave.thisuser.setMoods(moods);
                                            }


                                            /**
                                             * cancel login activity
                                             */
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
