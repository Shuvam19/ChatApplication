package com.example.chatapplication.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapplication.Model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageRepo {
    public static LiveData<List<Message>> requestmessages(String chatId) {
        final MutableLiveData<List<Message>> liveData = new MutableLiveData<>();
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("chats").child(chatId).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> listOfMeassage = new ArrayList<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DataSnapshot currentsnapshot = ((DataSnapshot)iterator.next());
                    String message = currentsnapshot.child("message").getValue().toString();
                    String time = currentsnapshot.child("time").getValue().toString();
                    String date = currentsnapshot.child("date").getValue().toString();
                    String name = currentsnapshot.child("name").getValue().toString();
                    listOfMeassage.add(new Message(message,date,time,name));
                }
                liveData.setValue(listOfMeassage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }
}
