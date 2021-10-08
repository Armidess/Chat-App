package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;
import com.example.whatsappclone.chatActivityDetails;
import com.example.whatsappclone.Models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{

    ArrayList<User> list= new ArrayList<>();
    Context context;

    public UserAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user=list.get(position);
        Picasso.get().load(user.getDp()).placeholder(R.drawable.user_512).into(holder.imgView);
        holder.userName.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, chatActivityDetails.class);
                intent.putExtra("Name",user.getName());
                intent.putExtra("userID",user.getUserID());
                intent.putExtra("DP",user.getDp());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView  userName,lastMessage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgView=itemView.findViewById(R.id.profile_image);
            userName=itemView.findViewById(R.id.userName);
            lastMessage=itemView.findViewById(R.id.lastMessage);
        }
    }
}
