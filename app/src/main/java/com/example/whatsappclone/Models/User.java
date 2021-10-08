package com.example.whatsappclone.Models;

public class User {
    String name,password,email,dp,userID,lastmessage;

    public User(String name, String password, String email, String dp, String userID, String lastmessage) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.dp = dp;
        this.userID = userID;
        this.lastmessage = lastmessage;
    }
    public User(){}
    public User(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
