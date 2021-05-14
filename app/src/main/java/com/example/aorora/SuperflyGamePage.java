package com.example.aorora;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.aorora.model.SuperflySession;


public class SuperflyGamePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_game);

        backButton = (ImageButton) findViewById(R.id.back_button);

        backButton.setOnClickListener(this);
        SuperflySession currentSession = MainActivity.user_info.getCurrentSession();

        //Build the UI based on the number of participants currently in the session.
        initParticipants(currentSession);

    }

    void initParticipants(SuperflySession currentSession) {
        switch(currentSession.getSession_participant_count()){
            case 1:
                Log.d("InitSesh", "One participant!");
                break;
            case 2:
                Log.d("InitSesh", "Two participants!");
                break;
            case 3:
                Log.d("InitSesh", "Three participants!");
                break;
            case 4:
                Log.d("InitSesh", "Four participants!");
                break;
            case 5:
                Log.d("InitSesh", "Five participants!");
                break;
        }
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
