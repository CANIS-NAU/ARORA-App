package com.example.aorora;

import android.content.Intent;
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

import java.util.List;


public class SuperflyGamePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    TextView[] participantNames;
    ImageView[] participantBubbles;
    UserInfo[] participants;
    SuperflySession currentSession;
    Integer participantCount;
    Button startButton;

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
        participants = new UserInfo[]{(UserInfo) currentSession.getParticipant_0(),
                (UserInfo) currentSession.getParticipant_1(),
                (UserInfo) currentSession.getParticipant_2(),(UserInfo) currentSession.getParticipant_3(),
                (UserInfo) currentSession.getParticipant_4()};


        backButton = (ImageButton) findViewById(R.id.back_button);
        startButton = findViewById(R.id.start_button);

        backButton.setOnClickListener(this);
        startButton.setOnClickListener(this);


        //Build the UI based on the number of participants currently in the session.
        initParticipants(currentSession);

    }

    void initParticipants(SuperflySession currentSession) {
        int index = 0;
        UserInfo currentParticipant;
        while(index < participants.length){
            currentParticipant = participants[index];
            //Set the textView if we have a name available for a player.
            if(currentParticipant != null) {
                participantNames[index].setText(currentParticipant.getUser_name());
                participantNames[index].setVisibility(View.VISIBLE);
                participantBubbles[index].setVisibility(View.VISIBLE);
            }
            index++;
        }
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
            //Finish this activity and pop backwards
            if(currentSession.getSession_participant_count() < 2){
                Toast.makeText(this, "Not enough players to start!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Starting game", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
