package com.example.chatapplication.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Model.Users;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.AddFriendsViewHolder> {
    Context context;
    List<Users> friendsList;
    FirebaseUser currUser;
    String grp_name;
    DatabaseReference reference;

    public AddFriendsAdapter(Context context, List<Users> friendsList, String grp_name) {
        this.context = context;
        this.friendsList = friendsList;
        this.grp_name = grp_name;
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public AddFriendsAdapter(Context context, List<Users> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public AddFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_individual,parent,false);
        return new AddFriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendsViewHolder holder, final int position) {
        holder.text.setText(friendsList.get(position).getUsername());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend(friendsList.get(position).getUsername(),friendsList.get(position).getUid());
            }
        });
    }

    private void addFriend(String name, String uid) {
        DatabaseReference newreference;
        if (!(grp_name == null)){
            newreference = reference.child("chats").child(grp_name);
            newreference.child("mode").setValue("group");
            newreference.child("member").child(name).setValue(uid);
            newreference.child("member").child(currUser.getDisplayName()).setValue(currUser.getUid());
        } else {
            String currname = currUser.getDisplayName();
            newreference = reference.child("chats").push();
            newreference.child("mode").setValue("single");
            newreference.child("member").child(name).child("uid").setValue(currUser.getUid());
            newreference.child("member").child(name).child("name").setValue(currname);
            newreference.child("member").child(currname).child("uid").setValue(uid);
            newreference.child("member").child(currname).child("name").setValue(name);
            ((Activity)context).finish();
        }
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public class AddFriendsViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout layout;
        public AddFriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.friend_name);
            layout = itemView.findViewById(R.id.layout_friend);
        }
    }
}
