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
        this.AvatarPath = "";
    }

    public User(String email, String nickname, ArrayList<String> friends, ArrayList<String> moods, String AvatarPath){
        this.email = email;
        this.nickname = nickname;
        this.friends = friends;
        this.moods = moods;
        this.AvatarPath = AvatarPath;
    }

    public User(){
        this.email = null;
        this.nickname = null;
        this.friends = new ArrayList<>();
        this.moods = new ArrayList<>();
        this.AvatarPath = null;
        this.password = null;
    };

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

    public void setAvatarPath(String avatarPath) {
        AvatarPath = avatarPath;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void setMoods(ArrayList<String> moods) {
        this.moods = moods;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


