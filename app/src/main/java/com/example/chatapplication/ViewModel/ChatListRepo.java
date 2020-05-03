package com.example.chatapplication.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapplication.Model.ChatList;
import com.example.chatapplication.Model.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ChatListRepo {
    public static MutableLiveData<List<ChatList>> requestChatList() {
        final MutableLiveData<List<ChatList>> liveData = new MutableLiveData<>();
        final FirebaseUser currUser;
        final DatabaseReference reference;
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                List<Members> members = new ArrayList<>();
                List<ChatList> chatLists = new ArrayList<>();
                while (iterator.hasNext()){
                    DataSnapshot currentsnapshot = ((DataSnapshot)iterator.next());
                    Log.i("mode",currentsnapshot.child("mode").getValue().toString());
                    if(currentsnapshot.child("mode").getValue().toString().equals("group")){
                        String group_name = "";
                        Iterator person = currentsnapshot.child("member").getChildren().iterator();
                        while (person.hasNext()){
                            DataSnapshot name = ((DataSnapshot)person.next());
                            members.add(new Members(name.getKey(),name.getValue().toString()));
                            if(currUser.getDisplayName().equals(name.getKey())){
                                group_name = currentsnapshot.getKey();
                            }
                        }
                        Log.i("msg12",group_name);
                        chatLists.add(new ChatList(group_name,"group",group_name,members));
                    } else {
                        String name = "";
                        Iterator person = currentsnapshot.child("member").getChildren().iterator();
                        while (person.hasNext()){
                            DataSnapshot personname = ((DataSnapshot)person.next());
                            members.add(new Members(personname.child("uid").getValue().toString(),personname.getKey()));
                            if(currUser.getDisplayName().equals(personname.getKey())){
                                if (!(personname.child("name").getValue() == null)){
                                    name = personname.child("name").getValue().toString();
                                }
                            }
                        }
                        if (!(name.isEmpty())){
                            Log.i("msg1",name + "ac");
                            chatLists.add(new ChatList(currentsnapshot.getKey(), "single","single",name,members));
                        }
                    }
                }
                liveData.setValue(chatLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }
}
