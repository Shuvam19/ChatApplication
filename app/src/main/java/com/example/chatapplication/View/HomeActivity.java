package com.example.chatapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatapplication.Model.ChatList;
import com.example.chatapplication.ViewModel.ChatListViewModel;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currUser;
    DatabaseReference reference;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showChatList();
    }

    private void showChatList() {
        ChatListViewModel chatListViewModel = new ChatListViewModel();
        chatListViewModel.getChatList().observe(HomeActivity.this, new Observer<List<ChatList>>() {
            @Override
            public void onChanged(List<ChatList> chatLists) {
                ChatListAdapter chatListAdapter = new ChatListAdapter(chatLists,HomeActivity.this);
                recyclerView.setAdapter(chatListAdapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){
            firebaseAuth.signOut();
            Intent intent = new Intent(HomeActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.create_chat){
            Intent intent = new Intent(HomeActivity.this,AddFriendActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.create_grp){
            createGroup();
        }
        return true;
    }

    private void createGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Enetr Group Name");
        final TextView grp_name = new EditText(HomeActivity.this);
        builder.setView(grp_name);
        builder.setPositiveButton("create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!grp_name.getText().toString().isEmpty()){
                    Intent intent = new Intent(HomeActivity.this,AddFriendActivity.class);
                    intent.putExtra("name",grp_name.getText().toString());
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
