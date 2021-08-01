package com.example.android.todo_missions.models;

import com.example.android.todo_missions.RandomIcons;

public class TodoObject {

    private String mName;
    private String mDate;
    private String mDateContent;
    private int mIconId = 0;
    private int mBackgroundId = 0;
    private int mSmallCircleColorId = 0;


    public TodoObject(String name, String date, String dateContent) {

        mName = name;
        mDate = date;
        mDateContent = dateContent;

    }


    public TodoObject(String name, String date, String dateContent, int iconId, int backgroundId, int smallCircleColorId) {

        mName = name;
        mDate = date;
        mDateContent = dateContent;
        mIconId = iconId;
        mBackgroundId = backgroundId;
        mSmallCircleColorId = smallCircleColorId;

    }


    public String getDateContent() {
        return mDateContent;
    }

    public void setDateContent(String dateContent) {
        mDateContent = dateContent;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }


    public int getSmallCircleColorId() {
        return mSmallCircleColorId;
    }

    public void setSmallCircleColorId(int mallCircleColorId) {
        mSmallCircleColorId = mallCircleColorId;
    }

    public int getBackgroundId() {
        return mBackgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        mBackgroundId = backgroundId;
    }



}
