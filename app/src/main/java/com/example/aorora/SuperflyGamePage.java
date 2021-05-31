package com.example.aorora;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.model.SuperflySession;
import com.example.aorora.model.UserInfo;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitResponseListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class SuperflyGamePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    TextView[] participantNames;
    ImageView[] participantBubbles;
    ImageView[] participantButterflies;

    UserInfo[] participants;
    SuperflySession currentSession;
    Integer participantCount;
    Button startButton;
    Button refreshButton;
    Boolean sessionStarted;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnHome = new Intent(this, ProfilePage.class);
        returnHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(returnHome);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_game);
        currentSession = MainActivity.user_info.getCurrentSession();
        participantCount = currentSession.getSession_participant_count();
        participantNames = new TextView[]{(TextView) findViewById(R.id.participant0_tv),
                (TextView) findViewById(R.id.participant1_tv),
                (TextView) findViewById(R.id.participant2_tv), (TextView) findViewById(R.id.participant3_tv),
                (TextView) findViewById(R.id.participant4_tv)};
        participantBubbles = new ImageView[]{(ImageView) findViewById(R.id.participant0_bubble),
                (ImageView) findViewById(R.id.participant1_bubble),
                (ImageView) findViewById(R.id.participant2_bubble), (ImageView) findViewById(R.id.participant3_bubble),
                (ImageView) findViewById(R.id.participant4_bubble)};
        participantButterflies = new ImageView[]{(ImageView) findViewById(R.id.participant0_butterfly),
                (ImageView) findViewById(R.id.participant1_butterfly),
                (ImageView) findViewById(R.id.participant2_butterfly), (ImageView) findViewById(R.id.participant3_butterfly),
                (ImageView) findViewById(R.id.participant4_butterfly)};
        participants = new UserInfo[]{(UserInfo) currentSession.getParticipant_0(),
                (UserInfo) currentSession.getParticipant_1(),
                (UserInfo) currentSession.getParticipant_2(),(UserInfo) currentSession.getParticipant_3(),
                (UserInfo) currentSession.getParticipant_4()};

        sessionStarted = currentSession.getSession_started();


        backButton = (ImageButton) findViewById(R.id.back_button);
        startButton = findViewById(R.id.start_button);
        refreshButton = findViewById(R.id.refreshsesh_button);

        backButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);


        initParticipants();

        if(sessionStarted){
            startButton.setText("Contribute butterfly");
        }

        else{
            //Build the UI based on the number of participants currently in the session.
            initParticipants();
        }


    }

    void initParticipants() {
        int index = 0;
        UserInfo currentParticipant;
        while(index < participantCount){
            currentParticipant = participants[index];
            //Set the textView if we have a name available for a player.
            if(currentParticipant != null) {
                participantNames[index].setText(currentParticipant.getUser_name());
                participantNames[index].setVisibility(View.VISIBLE);
                participantBubbles[index].setVisibility(View.VISIBLE);
                participantButterflies[index].setVisibility(View.VISIBLE);
            }
            index++;
        }
        if(participantCount < 2){
            startButton.setEnabled(false);
        }
        Log.d("INIT PARTICIPANTS", "Assigned butterflies: " + Arrays.toString(currentSession.getAssignedButterflies()));
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == backButton.getId())
        {
            //Finish this activity and pop backwards
            Intent returnHome = new Intent(this, ProfilePage.class);
            returnHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(returnHome);
        }

        else if(view_id == startButton.getId())
        {

            if(currentSession.getSession_participant_count() < 2){
                Toast.makeText(this, "Not enough players to start!", Toast.LENGTH_SHORT).show();
            }
            else{
                if(currentSession.getId_0() == MainActivity.user_info.getUser_id()){
                    Toast.makeText(this, "Starting game", Toast.LENGTH_SHORT).show();
                    NetworkCalls.startSession(currentSession.getSession_id(), this);
                    MainActivity.user_info.getCurrentSession().setSession_started(true);
                    recreate();
                }
                else{
                    Toast.makeText(this, "Only the session owner can start the game, refreshing current session.", Toast.LENGTH_SHORT).show();
                    MainActivity.user_info.getCurrentSession();
                    recreate();
                }
            }
        }

        else if(view_id == refreshButton.getId()){
            //Use StartSession to check current status.
            NetworkCalls.startSession(currentSession.getSession_id(), this, new RetrofitResponseListener() {
                @Override
                public void onSuccess() {
                    //Refresh session
                    currentSession = MainActivity.user_info.getCurrentSession();
                    Log.d("Refresh sesh", Arrays.toString(currentSession.buildParticipantsArray()));
                    Log.d("Refresh sesh assigned buffs", Arrays.toString(currentSession.buildAssignedButterfliesArray()));
                }

                @Override
                public void onFailure() {

                }
            });




        }

    }


}
