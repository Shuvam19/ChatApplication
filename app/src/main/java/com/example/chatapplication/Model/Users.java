package com.example.chatapplication.Model;

public class Users {
    String username;
    String email;
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Users(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public Users(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
