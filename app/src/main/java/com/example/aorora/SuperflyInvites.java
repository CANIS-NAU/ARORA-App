package com.example.aorora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aorora.adapter.InvitePageAdapter;
import com.example.aorora.network.NetworkCalls;

import java.util.Arrays;

public class SuperflyInvites extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView inviteRecyclerView;
    InvitePageAdapter inviteAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageButton backButton;

    //TODO: Get this from the network/backend.
    int playerCounts[] = {3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_invites);
        backButton = findViewById(R.id.back_button_invite);
        backButton.setOnClickListener(this);
        inviteRecyclerView = findViewById(R.id.invite_recycler);
        inviteRecyclerView.setAdapter(new InvitePageAdapter(this, this.playerCounts));
        layoutManager = new LinearLayoutManager(this);
        inviteRecyclerView.setLayoutManager(layoutManager);
        inviteRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == backButton.getId())
        {
            //Finish this activity and pop backwards
            finish();
        }

    }
}