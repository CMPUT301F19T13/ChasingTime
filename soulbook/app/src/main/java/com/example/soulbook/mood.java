package com.example.soulbook;

import java.util.ArrayList;

public class mood {
    private String content;
    private User poster;
    private time Time;
    private ArrayList<comment> comments;
    public mood(String content, User poster, time time){
        this.content = content;
        this.poster = poster;
        this.Time = time;
        comments = null;
    }



    public time getTime() {
        return Time;
    }

    public User getPoster() {
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
}

class comment {
    private String content;
    private User commenter;
    public comment(String content, User commenter){
        this.content = content;
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public User getCommenter() {
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
