package com.example.thebestchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;


public class Chats extends AppCompatActivity {
    String chatName;
    TextView editText;
    Button sendMessage;
    ImageView backArrow;
    private RecyclerView messages;
    FirebaseAuth mAuth;
    private FirebaseRecyclerOptions<Message> options;
    private FirebaseRecyclerAdapter<Message,MyViewHolder> adapter;
    DatabaseReference ref;
    TextView reciever;
    String recieverUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        reciever = findViewById(R.id.reciever_name);
        backArrow=findViewById(R.id.back_arrow);
        mAuth=FirebaseAuth.getInstance();
        editText=findViewById(R.id.enter_text);
        sendMessage=findViewById(R.id.send_message);
        messages=findViewById(R.id.messages);
        messages.setHasFixedSize(true);
        messages.setLayoutManager(new LinearLayoutManager(this));
        reciever.setText(getIntent().getStringExtra("name"));
        recieverUid = getIntent().getStringExtra("uId");


        //Let's order the users' uid's alphabetically and call that the chat room.
        if (mAuth.getUid().compareTo(recieverUid)>0) {
            chatName=mAuth.getUid().concat(recieverUid);
        } else {
            chatName=recieverUid.concat(mAuth.getUid());
        }

        ref=FirebaseDatabase.getInstance().getReference().child("Messages/" + chatName);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt=editText.getText().toString().trim();
                Message message = new Message(messageTxt,mAuth.getCurrentUser().getEmail(),0);
                FirebaseDatabase.getInstance().getReference().child("Messages/" + chatName).push().setValue(message)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                messages.scrollToPosition(messages.getAdapter().getItemCount()-1);
                            }
                        });
                editText.setText("");
            }
        });

        options=new FirebaseRecyclerOptions.Builder<Message>().setQuery(ref,Message.class).build();
        adapter=new FirebaseRecyclerAdapter<Message,MyViewHolder>(options) {

            @Override
            public int getItemViewType(int position) {
                if (getItem(position).getMessageUser().equals(mAuth.getCurrentUser().getEmail()))
                    return R.layout.message;
                else
                    return R.layout.sender_message;
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if (viewType==R.layout.message) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
                    return new MyViewHolder(v);
                }
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_message, parent, false);
                    return new MyViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Message model) {
                holder.message.setText(model.getMessageText());
            }
        };

        adapter.startListening();
        messages.setAdapter(adapter);
        messages.scrollToPosition(messages.getAdapter().getItemCount()-1);
    }



}