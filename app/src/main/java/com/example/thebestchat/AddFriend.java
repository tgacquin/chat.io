package com.example.thebestchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFriend extends AppCompatActivity {

    TextView enterFriend;
    Button submit;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        submit=findViewById(R.id.submit);
        enterFriend=findViewById(R.id.enter_friend);
        mAuth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterFriendText = enterFriend.getText().toString();
                FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(enterFriendText).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot datas: snapshot.getChildren()) {
                            FirebaseDatabase.getInstance().getReference("Users/" + datas.getKey()).
                                    addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    FirebaseDatabase.getInstance().getReference("Users/" + mAuth.getCurrentUser().getUid()
                                            + "/friends/" + datas.getKey()).setValue(snapshot2.getValue()).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //Deletes friends
                                            FirebaseDatabase.getInstance().getReference("Users/" + mAuth.getCurrentUser().getUid()
                                                    + "/friends/" + datas.getKey() + "/friends").setValue(null);
                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }
        });
    }
}