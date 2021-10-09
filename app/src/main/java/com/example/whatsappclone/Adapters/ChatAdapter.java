package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.Models.MessagesModel;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<MessagesModel> list = new ArrayList<>();
    Context context;
    int Sender_View_Type=1;
    int Receiver_View_Type=0;

    public ChatAdapter(ArrayList<MessagesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == Sender_View_Type)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel model=list.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder) holder).senderMessage.setText(model.getMessage());
        }
        else
        {
            ((ReceiverViewHolder)holder).receiverMessage.setText(model.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
            return Sender_View_Type;
        else
            return Receiver_View_Type;
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMessage,receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.reciverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMessage,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage=itemView.findViewById(R.id.senderMessage);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }
}
