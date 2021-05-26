package com.example.aorora;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aorora.adapter.InvitePageAdapter;
import com.example.aorora.model.Superfly;
import com.example.aorora.model.SuperflyInvite;
import com.example.aorora.model.SuperflySession;
import com.example.aorora.network.NetworkCalls;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperflyInvitesPage extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView inviteRecyclerView;
    public InvitePageAdapter inviteAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageButton backButton;
    Button newSessionButton;
    Button refreshButton;
    ArrayList<String> inviteNames;
    ArrayList<Integer> playerCounts;
    ArrayList<SuperflyInvite> currInvites;
    TextView noInvites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_invites);
        //Get button ids
        backButton = findViewById(R.id.back_button_invite);
        newSessionButton = findViewById(R.id.new_session_button);
        refreshButton = findViewById(R.id.refresh_button);
        noInvites = findViewById(R.id.no_invites);
        backButton.setOnClickListener(this);
        newSessionButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        this.currInvites = MainActivity.user_info.getCurrentInvites();
        inviteRecyclerView = findViewById(R.id.invite_recycler);
        inviteRecyclerView.setAdapter(new InvitePageAdapter(this, this.currInvites));
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
        //New session button to create new lobby.
        else if(view_id == newSessionButton.getId()){
            //Init a new session with a network call
            Log.d("CALL FROM INVITE", "Newsessionbutton clicked with id: " + MainActivity.user_info.getUser_id().toString());
            NetworkCalls.createSuperflySession(MainActivity.user_info.getUser_id(), this);
            //Navigate to the created lobby.
            //GET the new lobby back

        }
        else if(view_id == refreshButton.getId()){
            Log.d("INVITEPAGE", "Refreshing list of invites!");
            //Load invites with GET request from network calls.
            //Fix userinfo first.
            NetworkCalls.loadInvites(MainActivity.user_info.getUser_id(), this, false);
            //inviteAdapter.notifyDataSetChanged();
        }

    }
}