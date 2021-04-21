package com.example.aorora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aorora.R;
import com.example.aorora.SuperflyLobby;

public class InvitePageAdapter extends RecyclerView.Adapter<InvitePageAdapter.InvitePageViewHolder> {
    Context context;
    //Number of current users out of 5
    int playerCounts[];

    //This will take in the names, descs, and images to be held in our recyclerview.
    public InvitePageAdapter(Context ct, int inCounts[]){
        playerCounts = inCounts;
        context = ct;
    }


    @NonNull
    @Override
    public InvitePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.invite_row, parent, false);
        InvitePageViewHolder invitePageViewHolder = new InvitePageViewHolder(view);
        return invitePageViewHolder;
    }
    //TODO: Why is this position flag giving so much trouble? I suppressed unnecessary warnings and
    //declared final to use in the onclicklistener.
    @Override
    public void onBindViewHolder(@NonNull InvitePageViewHolder invitePageViewHolder, @SuppressLint("RecyclerView") final int position) {
        invitePageViewHolder.playerCount.setText(Integer.toString(playerCounts[position]));
        invitePageViewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuperflyLobby.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerCounts.length;
    }

    public class InvitePageViewHolder extends RecyclerView.ViewHolder{
        TextView playerCount;
        LinearLayout rowLayout;
        public InvitePageViewHolder(@NonNull View itemView) {
            super(itemView);
            playerCount = itemView.findViewById(R.id.participants_tv);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }

    }
}