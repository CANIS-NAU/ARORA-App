package com.example.aorora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aorora.adapter.AtriumAdapter;
import com.example.aorora.adapter.InvitePageAdapter;

public class SuperflyInvites extends AppCompatActivity {

    private RecyclerView inviteRecyclerView;
    InvitePageAdapter inviteAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_invites);
        inviteRecyclerView = findViewById(R.id.atrium_recycler);
        inviteRecyclerView.setAdapter(inviteAdapter);
        layoutManager = new LinearLayoutManager(this);
        inviteRecyclerView.setLayoutManager(layoutManager);
        inviteRecyclerView.setHasFixedSize(true);

    }
}