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

import com.example.aorora.adapter.InvitePageAdapter;
import com.example.aorora.network.NetworkCalls;

public class SuperflyInvites extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView inviteRecyclerView;
    InvitePageAdapter inviteAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageButton backButton;
    Button newSessionButton;

    //TODO: Get this from the network/backend. Running counts of players in each invite.
    int playerCounts[] = {3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_invites);
        //Get button ids
        backButton = findViewById(R.id.back_button_invite);
        newSessionButton = findViewById(R.id.new_session_button);
        backButton.setOnClickListener(this);
        newSessionButton.setOnClickListener(this);
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
        //New session button to create new lobby.
        else if(view_id == newSessionButton.getId()){
            //Init a new session with a network call
            Log.d("CALL FROM INVITE", "Newsessionbutton clicked with id: " + MainActivity.user_info.getUser_id().toString());
            NetworkCalls.createSuperflySession(MainActivity.user_info.getUser_id(), this);
            //Navigate to the created lobby.
            //GET the new lobby back

        }

    }
}