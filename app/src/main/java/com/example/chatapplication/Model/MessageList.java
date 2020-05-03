package com.example.chatapplication.Model;

import com.example.chatapplication.Model.Message;

import java.util.List;

public class MessageList {
    List<Message> messages;

    public MessageList(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
