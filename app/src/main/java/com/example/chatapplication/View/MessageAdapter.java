package com.example.chatapplication.View;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Model.Message;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    Context context;
    List<Message> messages;
    FirebaseUser currUser;
    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
        currUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.individual_message,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message currMessage = messages.get(position);
        if(currUser.getDisplayName().equals(currMessage.getName())){
            holder.layout.setGravity(Gravity.END);
        }
        holder.message_of_person.setText(currMessage.getMessage());
        holder.name_of_person.setText(currMessage.getName());
        holder.time_of_message.setText(currMessage.getTime());
        holder.day_of_person.setText(currMessage.getDate());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView time_of_message,message_of_person,name_of_person,day_of_person;
        LinearLayout layout;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            time_of_message = itemView.findViewById(R.id.time_of_message);
            message_of_person = itemView.findViewById(R.id.message_of_person);
            name_of_person = itemView.findViewById(R.id.name_of_person);
            day_of_person = itemView.findViewById(R.id.day_of_message);
            layout = itemView.findViewById(R.id.message_layout);
        }
    }
}
