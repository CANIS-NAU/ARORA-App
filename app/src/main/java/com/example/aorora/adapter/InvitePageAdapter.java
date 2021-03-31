package com.example.aorora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aorora.AtriumDetail;
import com.example.aorora.R;
import com.example.aorora.SuperflyLobby;

public class InvitePageAdapter extends RecyclerView.Adapter<InvitePageAdapter.InvitePageViewHolder> {
    Context context;
    //Number of current users out of 5
    int counts[];

    //This will take in the names, descs, and images to be held in our recyclerview.
    public InvitePageAdapter(Context ct, int inCounts[]){
        counts = inCounts;
        context = ct;
    }


    @NonNull
    @Override
    public InvitePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.atrium_row, parent, false);
        InvitePageViewHolder invitePageViewHolder = new InvitePageViewHolder(view);
        return invitePageViewHolder;
    }
    //TODO: Why is this position flag giving so much trouble? I suppressed unnecessary warnings and
    //declared final to use in the onclicklistener.
    @Override
    public void onBindViewHolder(@NonNull InvitePageViewHolder invitePageViewHolder, @SuppressLint("RecyclerView") final int position) {
        invitePageViewHolder.butterflyCount.setText(Integer.toString(counts[position]));
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
        return counts.length;
    }

    public class InvitePageViewHolder extends RecyclerView.ViewHolder{
        ImageView butterflyImage;
        TextView butterflyCount;
        LinearLayout rowLayout;
        public InvitePageViewHolder(@NonNull View itemView) {
            super(itemView);
            butterflyImage = itemView.findViewById(R.id.butterfly_img);
            butterflyCount = itemView.findViewById(R.id.butterfly_count);
            rowLayout = itemView.findViewById(R.id.row_layout);

        }

    }
}