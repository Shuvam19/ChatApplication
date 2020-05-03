package com.example.chatapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapplication.Model.Users;

import java.util.List;

public class AddFriendViewModel extends ViewModel {
    private AddFriendsRepo addFriendsRepo;
    private MutableLiveData<List<Users>> liveData;

    public AddFriendViewModel() {
        addFriendsRepo = new AddFriendsRepo();
    }

    public LiveData<List<Users>> getFriends(){
        if(liveData==null){
            liveData = AddFriendsRepo.requestFriends();
        }
        return liveData;
    }
}
