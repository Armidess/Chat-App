package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.Models.MessagesModel;
import com.example.whatsappclone.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatActivityDetails extends AppCompatActivity {
    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final String senderID = auth.getUid();
        String receiverid = getIntent().getStringExtra("userID");
        String name = getIntent().getStringExtra("Name");
        Picasso.get().load(getIntent().getStringExtra("DP")).placeholder(R.drawable.user_512).into(binding.profileImage);
        final String SenderRoom = senderID+ receiverid;
        final String ReceiverRoom = receiverid+senderID;
        final ArrayList<MessagesModel> list = new ArrayList<>();
        final ChatAdapter chatAdapter= new ChatAdapter(list,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);
        database.getReference().child("Chats").child(SenderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    MessagesModel model= snapshot1.getValue(MessagesModel.class);
                    list.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.UserName.setText(name);
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chatActivityDetails.this,Homescreen.class));
                finish();
            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= binding.chatMessage.getText().toString();
                if(message.length()>0) {
                    final MessagesModel model = new MessagesModel(senderID, message);
                    model.setTimestamp(new Date().getTime());
                    binding.chatMessage.setText("");
                    database.getReference().child("Chats").child(SenderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference().child("Chats").child(ReceiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(chatActivityDetails.this, "Enter A Message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chatActivityDetails.this, "Call Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        binding.videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chatActivityDetails.this, "Video Call Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        binding.chatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chatActivityDetails.this, "Setting Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
    }
}