package com.example.chatapplication.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapplication.Model.Users;
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

public class AddFriendsRepo {
    public static MutableLiveData<List<Users>> requestFriends() {
        final FirebaseUser currUser;
        DatabaseReference reference;
        final MutableLiveData<List<Users>> liveData = new MutableLiveData<>();
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator iterator = dataSnapshot.getChildren().iterator();
                List<Users> usernames = new ArrayList<>();
                String name = currUser.getDisplayName();
                String s,s1,s2;
                while (iterator.hasNext()) {
                    DataSnapshot currentsnapshot =((DataSnapshot)iterator.next());
                    s = currentsnapshot.child("username").getValue().toString();
                    s1 = currentsnapshot.getKey();
                    s2 = currentsnapshot.child("email").getValue().toString();
                    if (!name.equals(s)){
                        usernames.add(new Users(s,s2,s1));
                    }
                }
                Log.i("msg",name);
                liveData.setValue(usernames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

     return liveData;
    }
}
