package com.example.chatapplication.Model;


import java.util.List;

public class ChatList {
    String uid;
    String last_seen;
    String mode;
    String name;
    List<Members> members;

    public String getMode() {
        return mode;
    }

    public ChatList(String uid, String mode, String name, List<Members> members) {
        this.uid = uid;
        this.mode = mode;
        this.name = name;
        this.members = members;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ChatList(String uid, String last_seen, String mode, String name, List<Members> members) {
        this.uid = uid;
        this.last_seen = last_seen;
        this.mode = mode;
        this.name = name;
        this.members = members;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public List<Members> getMembers() {
        return members;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
