package com.example.soulbook;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this activity is the information of every users.
 * it holds an email,nickname,path of avatar,friend list, mood list and password
 */
public class User {
    private String email;
    private String nickname;
    private String AvatarPath;
    private ArrayList<String> friends;
    private ArrayList<String> moods;
    private String password;

    /**
     *
     * @param email
     * @param nickname
     * @param password
     */
    public User(String email, String nickname, String password){
        this.email = email;
        this.nickname = nickname;
        this.friends = new ArrayList<>();
        this.moods = new ArrayList<>();
        this.AvatarPath = "";
    }

    /**
     *
     * @param email
     * @param nickname
     * @param friends
     * @param moods
     * @param AvatarPath
     */
    public User(String email, String nickname, ArrayList<String> friends, ArrayList<String> moods, String AvatarPath){
        this.email = email;
        this.nickname = nickname;
        this.friends = friends;
        this.moods = moods;
        this.AvatarPath = AvatarPath;
    }

    /**
     * user include unique email address,nick name,friend list,mood list, avatar and password
     * they are empty before set and get this information
     */
    public User(){
        this.email = null;
        this.nickname = null;
        this.friends = new ArrayList<>();
        this.moods = new ArrayList<>();
        this.AvatarPath = null;
        this.password = null;
    };

    /**
     * get the password of user
     * @return
     * return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * update a new avatar for user
     * @param newAvatarPath
     */
    public void updateAvatar(String newAvatarPath){
        this.AvatarPath = newAvatarPath;
    }

    /**
     * push a friend into friend list
     * @param a
     */
    public void pushFriends(ArrayList<String> a){
        friends = a;
    }

    /**
     * push the mood into mood list
     * @param a
     */
    public void pushMoods(ArrayList<String> a){
        moods = a;
    }

    /**
     * add a new mood into moodlist
     * @param newMoodId
     */
    public void addMood(String newMoodId){
        moods.add(newMoodId);
    }

    /**
     * add a new friend
     * @param newFriend
     */
    public void addFriends(String newFriend){
        friends.add(newFriend);
    }

    /**
     * change a avatar
     * user can reset a new different avatar
     * @param newAvatar
     */
    public void resetAvatar(String newAvatar){
        AvatarPath = newAvatar;
    }

    /**
     * get a nickname for user account
     * @return
     * return the nickname
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * get a unique email address for user account
     * @return
     * return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * find the new friend
     * @return
     * return the friend
     */
    public ArrayList<String> getFriends() {
        return friends;
    }

    /**
     * get the path of avatar
     * @return
     * return the path of avatar
     */
    public String getAvatarPath() {
        return AvatarPath;
    }

    /**
     * get a mood
     * @return
     * return mood
     */
    public ArrayList<String> getMoods() {
        return moods;
    }

    /**
     *
     * @return
     * return the result
     */
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

    /**
     *set the path of avatar
     * @param avatarPath
     */
    public void setAvatarPath(String avatarPath) {
        AvatarPath = avatarPath;
    }

    /**
     * set the email address of user account
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set the friend for user
     * @param friends
     */
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    /**
     * set the mood
     * @param moods
     */
    public void setMoods(ArrayList<String> moods) {
        this.moods = moods;
    }

    /**
     * set the nick name for user account
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * set the password of user account
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}


