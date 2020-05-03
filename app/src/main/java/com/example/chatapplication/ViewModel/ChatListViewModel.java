package com.example.chatapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapplication.Model.ChatList;

import java.util.List;

public class ChatListViewModel extends ViewModel {
    ChatListRepo chatListRepo;
    LiveData<List<ChatList>> liveData;

    public ChatListViewModel() {
        chatListRepo = new ChatListRepo();
    }

    public LiveData<List<ChatList>> getChatList(){
        if(liveData==null){
            liveData = ChatListRepo.requestChatList();
        }
        return liveData;
    }
}
