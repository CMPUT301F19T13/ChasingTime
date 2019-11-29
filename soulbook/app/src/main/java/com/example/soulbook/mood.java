package com.example.soulbook;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this is mood class, it contains all information of a mood, such as content, time, emotion, and etc.
 */
public class mood {
    private String content;
    private String poster;
    private time Time;
    private ArrayList<comment> comments;
    private ArrayList<String> likes;
    private int photonumber;
    private String emotion, socialSit;
    private String location;
    private String longtitude;
    private String latitude;

    /**
     * a constructor with several useful parameters
     * @param content
     *   main content of a mood
     * @param poster
     *   user  who posts the mood
     * @param time
     *   time when posting the mood
     * @param emotion
     *   emotion of the mood
     * @param n
     *   number of a photo
     */
    public mood(String content, String poster, time time, String emotion, int n, String location, String longtitude, String latitude){
        this.content = content;
        this.poster = poster;
        this.Time = time;
        this.location = location;
        comments = null;
        likes = null;
        this.emotion = emotion;
        photonumber = n;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public mood(String content, String poster, time time, String emotion, int n, String location) {
        this.content = content;
        this.poster = poster;
        this.Time = time;
        this.location = location;
        comments = null;
        likes = null;
        this.emotion = emotion;
        photonumber = n;
    }

    /**
     * a constructor with a hashmap
     * @param a
     *   hashmap a contains string and object
     */
    public mood(HashMap<String, Object> a){
        HashMap<String, Object> b = (HashMap)a.get("time");
        content = String.valueOf(a.get("content"));
        poster = String.valueOf(a.get("poster"));
        Time = new time(b);
        comments = null;
        likes = new ArrayList<>();
        emotion = String.valueOf(a.get("emotion"));
        photonumber =  Integer.parseInt(String.valueOf(a.get("photos")));
        try{
            location = String.valueOf(a.get("location"));
        }catch(Exception e){
            location = null;
        }
        try{
            socialSit = String.valueOf(a.get("social"));
        }catch (Exception e){
            socialSit = null;
        }
        try {
            longtitude = String.valueOf(a.get("longtitude"));
        }catch (Exception e){
            longtitude = null;
        }

        try{latitude = String.valueOf(a.get("latitude"));
        }catch(Exception e){
            latitude = null;
        }
    }
    public String getLongtitude(){
        return longtitude;
    }

    public String getlatitude(){
        return latitude;
    }

    @Nullable
    public void setSocialSit(String socialSit){
        this.socialSit = socialSit;
    }

    @Nullable
    public String getSocialSit() {
        return socialSit;
    }

    public String getLocation() {
        return location;
    }

    /**
     * a method to get photonumber
     * @return
     *   return the photonumber
     */
    public int getPhotonumber() {
        return photonumber;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    //some functionality of mood, such as add, delete comments, record the current time when sumbit the mood

    /**
     * a method to get time
     * @return
     *   return the time
     */
    public time getTime() {
        return Time;
    }
    /**
     * a method to get poster
     * @return
     *   return the poster
     */
    public String getPoster() {
        return poster;
    }
    /**
     * a method to get emtion
     * @return
     *   return the emotion
     */
    public String getEmotion() {
        return emotion;
    }
    /**
     * a method to assign number of a photo n to photonumber
     * @param n
     */
    public void setPhotonumber(int n){
        photonumber = n;
    }
    /**
     * a method to get content of a comment
     * @return
     *   return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * a method to create ArrayList comments
     * @return
     *   return ArrayList comments
     */
    public ArrayList<comment> getComment() {
        return comments;
    }
    /**
     * a method to put a comment to ArrayList
     * @param newComment
     *   comment to be added
     */
    public void addComment(comment newComment){
        comments.add(newComment);
    }

    /**
     * a method to delete an existing comment
     * @param oldComment
     *   comment to be deleted
     */
    public void deleteComment(comment oldComment){
        comments.remove(oldComment);
    }

    /**
     * a method to clean the index of a deleted comment
     * @param index
     *   index of the deleted comment
     */
    public void deleteComment(int index){
        comments.remove(index);
    }

    /**
     * a method to put info into the hashmap
     * @return
     *   return the hashmap
     */
    public HashMap<String, Object> tomap(){
        HashMap<String, Object> result = new HashMap();
        result.put("content", content);
        result.put("poster", poster);
        result.put("time", Time);
        result.put("comments", comments);
        result.put("emotion", emotion);
        result.put("photos", photonumber);
        if(socialSit != null){
            result.put("social", socialSit);
        }
        result.put("location", location);
        if(longtitude != null){
            result.put("longtitude",longtitude);
        }
        if(latitude != null) {
            result.put("latitude", latitude);
        }
        return result;
    }

    public int compareByTime(mood b){
        time t = b.getTime();
        return Time.compareTime(t);
    }

    public int compareTo(mood b){
        time t = b.getTime();
        return Time.compareTime(t);
    }
}

/**
 * this is a class for comment under a mood, we can get comment and commenter with this class
 */
class comment {
    private String content;
    private String commenter;

    /**
     * a constructor with useful parameters
     * @param content
     *   content of a comment
     * @param commenter
     *   user who makes the comment
     */
    public comment(String content, String commenter){
        this.content = content;
        this.commenter = commenter;
    }

    /**
     * a method to get the comment
     * @return
     *   return the comment
     */
    public String getContent() {
        return content;
    }

    /**
     * a method to get the user who makes the comment
     * @return
     *   return the user
     */
    public String getCommenter() {
        return commenter;
    }
}

/**
 * this is a class to get time of a mood
 */
class time{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    /**
     * a constructor of time
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     */
    public time(int year, int month, int day,int hour,int min){
        this.day = day;
        this.hour = hour;
        this.year = year;
        this.month = month;
        this.min = min;
    }

    /**
     * a method to get the time data
     * @param a
     *   hashmap a of time
     */
    public time(HashMap a){
        this.day = Integer.parseInt(String.valueOf(a.get("day")));
        this.month = Integer.parseInt(String.valueOf(a.get("month")));
        this.hour = Integer.parseInt(String.valueOf(a.get("hour")));
        this.year = Integer.parseInt(String.valueOf(a.get("year")));
        this.min = Integer.parseInt(String.valueOf(a.get("min")));
    }

    /**
     * a method to regulate the time format
     * @return
     *   return the time with desired format
     */
    public String printTime() {
        return month + " " + day + ", " +  year + ", " +  hour + ":" + min;
    }

    /**
     * a method to get day of a mood
     * @return
     *   return the day
     */
    public int getDay() {
        return day;
    }
    /**
     * a method to get minute of a mood
     * @return
     *   return the minute
     */
    public int getMin() {
        return min;
    }
    /**
     * a method to get hour of a mood
     * @return
     *   return the hour
     */
    public int getHour() {
        return hour;
    }
    /**
     * a method to get year of a mood
     * @return
     *   return the year
     */
    public int getYear() {
        return year;
    }
    /**
     * a method to get month of a mood
     * @return
     *   return the month
     */
    public int getMonth() {
        return month;
    }

    public int compareTime(time t){
        if (t.getYear() > year){
            return 0;
        }
        else if (t.getYear() < year){
            return 1;
        }
        else{
            if (t.getMonth() > month){
                return 0;
            }
            else if(t.getMonth() < month){
                return 1;
            }
            else{
                if (t.getDay() > day){
                    return 0;
                }
                else if(t.getDay() < day){
                    return 1;
                }
                else{
                    if (t.getHour() > hour){
                        return 0;
                    }
                    else if(t.getHour() < hour){
                        return 1;
                    }
                    else{
                        if (t.getMin() > min){
                            return 0;
                        }
                        else{
                            return 1;
                        }
                    }
                }
            }
        }
    }
}
