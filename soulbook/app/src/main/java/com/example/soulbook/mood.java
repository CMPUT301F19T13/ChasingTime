package com.example.soulbook;

import java.util.ArrayList;
import java.util.HashMap;

public class mood {
    private String content;
    private String poster;
    private time Time;
    private ArrayList<comment> comments;
    private ArrayList<String> likes;
    public mood(String content, String poster, time time){
        this.content = content;
        this.poster = poster;
        this.Time = time;
        comments = null;
        likes = null;
    }

    public mood(HashMap<String, Object> a){
        HashMap<String, Object> b = (HashMap)a.get("time");
        content = String.valueOf(a.get("content"));
        poster = String.valueOf(a.get("poster"));
        Time = new time(b);
        comments = null;
        likes = new ArrayList<>();
    }


    //some functionality of mood, such as add, delete comments, record the current time when sumbit the mood
    public time getTime() {
        return Time;
    }

    public String getPoster() {
        return poster;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<comment> getComment() {
        return comments;
    }

    public void addComment(comment newComment){
        comments.add(newComment);
    }

    public void deleteComment(comment oldComment){
        comments.remove(oldComment);
    }

    public void deleteComment(int index){
        comments.remove(index);
    }

    public HashMap<String, Object> tomap(){
        HashMap<String, Object> result = new HashMap();
        result.put("content", content);
        result.put("poster", poster);
        result.put("time", Time);
        result.put("comments", comments);
        return result;
    }
}

class comment {
    private String content;
    private String commenter;
    public comment(String content, String commenter){
        this.content = content;
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public String getCommenter() {
        return commenter;
    }
}

class time{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    public time(int year, int month, int day,int hour,int min){
        this.day = day;
        this.hour = hour;
        this.year = year;
        this.month = month;
        this.min = min;
    }
    //get the time data
    public time(HashMap a){
        this.day = Integer.parseInt(String.valueOf(a.get("day")));
        this.month = Integer.parseInt(String.valueOf(a.get("month")));
        this.hour = Integer.parseInt(String.valueOf(a.get("hour")));
        this.year = Integer.parseInt(String.valueOf(a.get("year")));
        this.min = Integer.parseInt(String.valueOf(a.get("min")));
    }
    //regulate the time format
    public String printTime() {
        return month + " " + day + ", " +  year + ", " +  hour + ":" + min;
    }

    public int getDay() {
        return day;
    }

    public int getMin() {
        return min;
    }

    public int getHour() {
        return hour;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
