package com.example.chatapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatapplication.ViewModel.AddFriendViewModel;
import com.example.chatapplication.R;
import com.example.chatapplication.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddFriendActivity extends AppCompatActivity {
    String grp_name;
    FirebaseUser currUser;
    DatabaseReference reference;
    RecyclerView add_member;
    Button button_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Intent intent = getIntent();
        grp_name = intent.getStringExtra("name");
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        add_member = findViewById(R.id.add_member);
        add_member.setHasFixedSize(true);
        add_member.setLayoutManager(new LinearLayoutManager(this));
        button_done = findViewById(R.id.button_done);
        if(!(grp_name == null)){
            button_done.setVisibility(View.VISIBLE);
        }

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AddFriendViewModel addFriendViewModel = new AddFriendViewModel();

        addFriendViewModel.getFriends().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                AddFriendsAdapter friendsAdapter;
                if (!(grp_name == null)){
                    friendsAdapter = new AddFriendsAdapter(AddFriendActivity.this,users,grp_name);
                } else {
                    friendsAdapter = new AddFriendsAdapter(AddFriendActivity.this,users);
                }
                add_member.setAdapter(friendsAdapter);
            }
        });
    }
}
