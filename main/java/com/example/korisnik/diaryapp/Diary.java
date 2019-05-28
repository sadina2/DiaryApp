package com.example.korisnik.diaryapp;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;


public class Diary {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mStory;


    public Diary() {
        this(UUID.randomUUID());

    }
    public Diary(UUID  id){
        mId=id;
        mDate = new Date()
;    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }


    public String getStory() {
        return mStory;
    }

    public void setStory(String story) {
        mStory = story;
    }
}
