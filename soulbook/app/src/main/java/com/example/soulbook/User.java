package com.example.soulbook;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String email;
    private String nickname;
    private String AvatarPath;
    private ArrayList<String> friends;
    private ArrayList<String> moods;
    private String password;

    public User(String email, String nickname, String password){
        this.email = email;
        this.nickname = nickname;
        this.friends = new ArrayList<>();
        this.moods = new ArrayList<>();
        this.AvatarPath = null;
        this.password = password;
    }

    public User(String email, String nickname, ArrayList<String> friends, ArrayList<String> moods, String AvatarPath){
        this.email = email;
        this.nickname = nickname;
        this.friends = friends;
        this.moods = moods;
        this.AvatarPath = AvatarPath;
    }

    public String getPassword() {
        return password;
    }

    public void updateAvatar(String newAvatarPath){
        this.AvatarPath = newAvatarPath;
    }

    public void pushFriends(ArrayList<String> a){
        friends = a;
    }

    public void pushMoods(ArrayList<String> a){
        moods = a;
    }

    public void addMood(String newMoodId){
        moods.add(newMoodId);
    }

    public void addFriends(String newFriend){
        friends.add(newFriend);
    }

    public void resetAvatar(String newAvatar){
        AvatarPath = newAvatar;
    }

    public String getNickname(){
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public String getAvatarPath() {
        return AvatarPath;
    }

    public ArrayList<String> getMoods() {
        return moods;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> result  = new HashMap<>();
        result.put("email", email);
        result.put("password", password);
        result.put("nickname", nickname);
        result.put("AvatarPath", AvatarPath);
        result.put("friendsList", friends);
        result.put("moods", moods);
        return result;
    }
}


