package com.example.thebestchat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderFriends extends RecyclerView.ViewHolder {

    TextView friend;
    public MyViewHolderFriends(@NonNull View itemView) {
        super(itemView);
        friend=itemView.findViewById(R.id.friend_name);
    }
}
