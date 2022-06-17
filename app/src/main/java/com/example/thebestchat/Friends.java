package com.example.thebestchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Friends extends AppCompatActivity {

    TextView editText;
    private RecyclerView friends;
    FirebaseAuth mAuth;
    private FirebaseRecyclerOptions<Message> options;
    private FirebaseRecyclerAdapter<Message,MyViewHolder> adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mAuth=FirebaseAuth.getInstance();
        editText=findViewById(R.id.enter_text);
        friends=findViewById(R.id.friends);
        friends.setHasFixedSize(true);
        friends.setLayoutManager(new LinearLayoutManager(this));
        ref= FirebaseDatabase.getInstance().getReference().child("Users");

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt=editText.getText().toString().trim();
                Message message = new Message(messageTxt,mAuth.getCurrentUser().getEmail(),0);
                FirebaseDatabase.getInstance().getReference().child("Messages").push().setValue(message)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                messages.scrollToPosition(messages.getAdapter().getItemCount()-1);
                            }
                        });
                editText.setText("");
            }
        });
    }
}