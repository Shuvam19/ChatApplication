package com.example.chatapplication.Model;

public class Message {
    String message;
    String date;
    String time;
    String name;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message(String message, String date, String time, String name) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.name = name;
    }
}
