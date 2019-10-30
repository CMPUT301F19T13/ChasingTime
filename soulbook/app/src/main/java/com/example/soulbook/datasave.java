package com.example.soulbook;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class datasave {
    public static User thisuser = new User();
    public static String UserId = "未读取";
    public static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
}
