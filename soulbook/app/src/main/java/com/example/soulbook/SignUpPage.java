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

public class SignUpPage extends AppCompatActivity {

    private EditText newemail, newPassword, confirmPassword, newNickname;
    Button loginButton, signUpButton;
    private DatabaseReference DataBase;

    @Override
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
            public void onClick(View v) {
                Intent in = new Intent(SignUpPage.this, LogInPage.class);
                startActivity(in);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String stringPassword = newPassword.getText().toString();
                final String stringConfirmPassword = confirmPassword.getText().toString();
                if (!stringConfirmPassword.equals(stringPassword)){
                    Toast.makeText(SignUpPage.this, "passwords are different, please try again: " + stringPassword + " :"+ stringConfirmPassword, Toast.LENGTH_LONG).show();
                    newPassword.setText("");
                    confirmPassword.setText("");
                }
                else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(newemail.getText().toString(), newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                User newUser = new User(newemail.getText().toString(), newNickname.getText().toString(), stringPassword);
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(newUser.toMap());
                                //FirebaseDatabase.getInstance().getReference().child("emailToId").child(newemail.getText().toString()).setValue(uid);
                                Intent in = new Intent(SignUpPage.this, LogInPage.class);
                                startActivity(in);
                            }
                            else{
                                Toast.makeText(SignUpPage.this, "This email has been used", Toast.LENGTH_LONG).show();
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
