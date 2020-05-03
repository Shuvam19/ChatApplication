package com.example.chatapplication.View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Model.ChatList;
import com.example.chatapplication.R;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    List<ChatList> chatLists;
    Context context;

    public ChatListAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list,parent,false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, final int position) {
        Log.i("msg",chatLists.get(position).getName());
        holder.text.setText(chatLists.get(position).getName());
        if (chatLists.get(position).getMode().equals("single")){
            //holder.last_seen.setText("Last Seen : " + chatLists.get(position).getLast_seen());
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("msg",chatLists.get(position).getUid());
                StartChatActivity(chatLists.get(position).getUid());
            }
        });
    }

    private void StartChatActivity(String uid) {
        Intent intent = new Intent(context, MessageActivity.class);
        Log.i("msg",uid);
        intent.putExtra("ChatID",uid);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {
        TextView text,last_seen;
        LinearLayout layout;
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.chat_name);
            layout = itemView.findViewById(R.id.layout_chat);
            //last_seen = itemView.findViewById(R.id.last_seen);
        }
    }
}
