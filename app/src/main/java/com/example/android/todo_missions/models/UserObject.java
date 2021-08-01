package com.example.android.todo_missions.models;

public class UserObject {

    private String userRealName;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String profilePic;


    public UserObject () {

    }


    public UserObject (String userRealName, String userName, String userEmail, String userPhone) {

        this.userRealName = userRealName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;

    }

    public UserObject (String userRealName, String userName, String userEmail, String userPhone, String profilePic) {

        this.userRealName = userRealName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.profilePic = profilePic;

    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }


}
