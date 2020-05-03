package com.example.chatapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapplication.Model.Message;

import java.util.List;

public class MessageViewModel extends ViewModel {
    MessageRepo messageRepo;
    LiveData<List<Message>> liveData;
    String chatId;

    public MessageViewModel(String chatId) {
        this.chatId = chatId;
        messageRepo = new MessageRepo();
    }

    public LiveData<List<Message>> getmessages(){
        if(liveData==null){
            liveData = MessageRepo.requestmessages(chatId);
        }
        return liveData;
    }
}
