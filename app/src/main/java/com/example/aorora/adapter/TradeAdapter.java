package com.example.aorora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.SuperflyGamePage;
import com.example.aorora.model.SuperflyInvite;
import com.example.aorora.model.SuperflySession;
import com.example.aorora.network.NetworkCalls;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeAdapterViewHolder> {
    Context context;
    //Names of inviters
    ArrayList<String> inviteNames;
    //Number of current users out of 5
    ArrayList<Integer> playerCounts;
    //List of displayed superfly sessions
    ArrayList<SuperflyInvite> currInvites;

    //This will take in the names, descs, and images to be held in our recyclerview.
    public TradeAdapter(Context ct, ArrayList<SuperflyInvite> inputInvites){
        this.context = ct;
        this.currInvites = inputInvites;
    }


    @NonNull
    @Override
    public TradeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.invite_row, parent, false);
        TradeAdapterViewHolder tradeViewHolder = new TradeAdapterViewHolder(view);
        return tradeViewHolder;
    }
    //TODO: Why is this position flag giving so much trouble? I suppressed unnecessary warnings and
    //declared final to use in the onclicklistener.
    @Override
    public void onBindViewHolder(@NonNull TradeAdapterViewHolder invitePageViewHolder, @SuppressLint("RecyclerView") final int position) {
        invitePageViewHolder.inviter.setText(currInvites.get(position).getSession().getParticipant_0().getUser_name());
        Log.d("PARTICIPANT COUNT", currInvites.get(position).getSession().getSession_participant_count().toString());
        invitePageViewHolder.playerCount.setText(currInvites.get(position).getSession().getSession_participant_count().toString() + "/5 participants");
        invitePageViewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Joining session", "Joining session created by " + currInvites.get(position).getSession().getParticipant_0().getUser_name());
                NetworkCalls.joinSession(currInvites.get(position).getSession(), MainActivity.user_info, context);
                //Intent intent = new Intent(context, SuperflyGamePage.class);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currInvites.size();
    }

    public class TradeAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView inviter;
        TextView playerCount;
        LinearLayout rowLayout;
        public TradeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            inviter = itemView.findViewById(R.id.inviter_tv);
            playerCount = itemView.findViewById(R.id.participants_tv);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }

    }
}