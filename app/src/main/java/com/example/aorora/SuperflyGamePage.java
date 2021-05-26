package com.example.aorora;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aorora.model.SuperflySession;
import com.example.aorora.model.UserInfo;

import java.util.List;


public class SuperflyGamePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    TextView[] participantNames;
    UserInfo[] participants;
    SuperflySession currentSession;
    Integer participantCount;
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
        participants = new UserInfo[]{(UserInfo) currentSession.getParticipant_0(),
                (UserInfo) currentSession.getParticipant_1(),
                (UserInfo) currentSession.getParticipant_2(),(UserInfo) currentSession.getParticipant_3(),
                (UserInfo) currentSession.getParticipant_4()};

        backButton = (ImageButton) findViewById(R.id.back_button);

        backButton.setOnClickListener(this);


        //Build the UI based on the number of participants currently in the session.
        initParticipants(currentSession);

    }

    void initParticipants(SuperflySession currentSession) {
        int index = 0;
        UserInfo currentParticipant;
        while(index < participants.length){
            currentParticipant = participants[index];
            //Set the textView if we have a name available for a player.
            if(currentParticipant != null)
                participantNames[index].setText(currentParticipant.getUser_name());
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
            finish();
        }

    }
}
