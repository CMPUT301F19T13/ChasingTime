package com.example.soulbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * this activity can sign up a new user account for user
 */
public class SignUpPage extends AppCompatActivity {

    private EditText newemail, newPassword, confirmPassword, newNickname;
    Button loginButton, signUpButton;
    private DatabaseReference DataBase;

    @Override
    /**
     * user can sign up a new user account when user input a email address that not use yet,input a password,confirm the password again and set a nickname
     * print sign up fail if the email address has been used
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        newemail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        newNickname = findViewById(R.id.newNickname);
        loginButton = findViewById(R.id.LoGinButton);
        signUpButton = findViewById(R.id.SIGNUPButton);
        DataBase = FirebaseDatabase.getInstance().getReference();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * go into log in activity from sign up activity
             */
            public void onClick(View v) {
                Intent in = new Intent(SignUpPage.this, LogInPage.class);
                startActivity(in);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * set the password for new user account
             * password set fail if two times input different password
             */
            public void onClick(View v) {
                //promote user input password when create an account
                final String stringPassword = newPassword.getText().toString();
                final String stringConfirmPassword = confirmPassword.getText().toString();
                //check and confirm the password when user first time create the account
                if (!stringConfirmPassword.equals(stringPassword)){
                    Toast.makeText(SignUpPage.this, "passwords are different, please try again: " + stringPassword + " :"+ stringConfirmPassword, Toast.LENGTH_LONG).show();
                    newPassword.setText("");
                    confirmPassword.setText("");
                }
                //user create an account successful so that save the username and password into firesbase database
                else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(newemail.getText().toString(), newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        /**
                         * finish create a new user account
                         * user account create fail if email address has been used.
                         */
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                User newUser = new User(newemail.getText().toString(), newNickname.getText().toString(), stringPassword);
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(newUser.toMap());
                                //FirebaseDatabase.getInstance().getReference().child("emailToId").child(newemail.getText().toString()).setValue(uid);
                                Intent in = new Intent(SignUpPage.this, LogInPage.class);
                                startActivity(in);
                            }
                            //handle the case that existed duplicate account
                            else{
                                Toast.makeText(SignUpPage.this, "This email has been used", Toast.LENGTH_LONG).show();
                                //promote user input a new email address and password
                                newemail.setText("");
                                newPassword.setText("");
                                confirmPassword.setText("");
                            }
                        }
                    });
                }

            }
        });

    }
}
