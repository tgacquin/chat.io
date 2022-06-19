package com.example.thebestchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Friends extends AppCompatActivity {

    private RecyclerView friends;
    FirebaseAuth mAuth;
    private FirebaseRecyclerOptions<User> options;
    private FirebaseRecyclerAdapter<User,MyViewHolderFriends> adapter;
    DatabaseReference ref;
    ImageView addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mAuth=FirebaseAuth.getInstance();
        addFriend=findViewById(R.id.add_friend);
        friends=findViewById(R.id.friends);
        friends.setHasFixedSize(true);
        friends.setLayoutManager(new LinearLayoutManager(this));
        ref= FirebaseDatabase.getInstance().getReference().child("Users/" + mAuth.getUid() + "/friends");

        //OnClick Listener?

        options=new FirebaseRecyclerOptions.Builder<User>().setQuery(ref,User.class).build();
        adapter=new FirebaseRecyclerAdapter<User,MyViewHolderFriends>(options) {

            @NonNull
            @Override
            public MyViewHolderFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_card, parent, false);
                return new MyViewHolderFriends(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderFriends holder, int position, @NonNull User model) {
                holder.friend.setText(model.getUsername());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(view.getContext(),Chats.class);
                        intent.putExtra("name",model.getUsername());
                        intent.putExtra("uId",model.getuId());
                        startActivity(intent);
                    }
                });



            }
        };

        adapter.startListening();
        friends.setAdapter(adapter);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Friends.this, AddFriend.class));
            }
        });



    }
}