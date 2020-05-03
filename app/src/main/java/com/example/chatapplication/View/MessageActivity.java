package com.example.chatapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapplication.Model.Message;
import com.example.chatapplication.ViewModel.MessageViewModel;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    String chatId,currtime,currDate;
    Button send_button;
    RecyclerView message_list;
    EditText message;
    FirebaseUser currUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        chatId = intent.getStringExtra("ChatID");
        Log.i("msg",chatId);
        send_button = findViewById(R.id.send_button);
        message = findViewById(R.id.message);
        message_list = findViewById(R.id.message_list);
        message_list.setHasFixedSize(true);
        message_list.setLayoutManager(new LinearLayoutManager(this));
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        getMessages();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currDate();
                if(!message.getText().toString().isEmpty()){
                    Message current = new Message(message.getText().toString(),currDate,currtime,currUser.getDisplayName());
                    Log.i("msg",current.getMessage());
                    Log.i("msg",current.getTime());
                    Log.i("msg",current.getName());
                    Log.i("msg",current.getDate());
                    reference.child("chats").child(chatId).child("messages").push().setValue(current);
                }
                message.setText("");
            }
        });

    }

    private void getMessages() {
        MessageViewModel messageViewModel = new MessageViewModel(chatId);
        messageViewModel.getmessages().observe(MessageActivity.this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                MessageAdapter messageAdapter = new MessageAdapter(MessageActivity.this,messages);
                message_list.setAdapter(messageAdapter);
                message_list.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    private void currDate() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM , dd , yyyy");
        currDate = dateFormat.format(date.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        currtime = timeFormat.format(date.getTime());
    }
}
