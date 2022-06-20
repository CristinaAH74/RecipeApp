package com.recipeapp.recipeapp.profile;

import android.widget.EditText;

public class RegisteredUserInfo {

    private String userEmail, userName;

    public RegisteredUserInfo() {
    }

    public RegisteredUserInfo(String userEmail, String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
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
}
