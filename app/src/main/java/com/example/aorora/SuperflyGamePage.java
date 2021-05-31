package com.example.aorora;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SuperflyGamePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    TextView[] participantNames;
    ImageView[] participantBubbles;
    ImageView[] participantButterflies;
    ImageView[] stagedButterflies;
    int[] butterflyImages;

    UserInfo[] participants;
    SuperflySession currentSession;
    Integer[] assignedButterflies;
    Integer participantCount;
    Button startButton;
    Button refreshButton;
    Boolean sessionStarted;
    Integer userPosition;

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
        butterflyImages = new int[]{R.drawable.red_1, R.drawable.yellow_1,
                R.drawable.violet_1, R.drawable.green_1,
                R.drawable.blue_2};
        stagedButterflies = new ImageView[]{(ImageView) findViewById(R.id.participant0_staged),
                (ImageView) findViewById(R.id.participant1_staged),
                (ImageView) findViewById(R.id.participant2_staged), (ImageView) findViewById(R.id.participant3_staged),
                (ImageView) findViewById(R.id.participant4_staged)};;

        sessionStarted = currentSession.getSession_started();


        backButton = (ImageButton) findViewById(R.id.back_button);
        startButton = findViewById(R.id.start_button);
        refreshButton = findViewById(R.id.refreshsesh_button);

        //Set all onclicklisteners here
        backButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        for(ImageView currBubble : participantBubbles ){
            currBubble.setOnClickListener(this);
        }


        if(sessionStarted){
            startButton.setText("Contribute butterfly");
        }

        //Call the initialization function to display the current game configuration.
        initParticipants();

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
        //Check what needs to be contributed to the current superfly
        assignedButterflies = currentSession.getAssignedButterflies();
        Log.d("INIT PARTICIPANTS", "Assigned butterflies: " + Arrays.toString(assignedButterflies));

        //Set the current user's bubble image to be the butterfly color they need to contribute.
        userPosition = getUserPosition();
        Log.d("INIT PARTICIPANTS", "Current User's position in session: " + userPosition.toString());

        if(currentSession.getSession_started()){
            displayAssignedButterfly();
        }
    }

    void displayAssignedButterfly(){
        participantButterflies[userPosition].setImageResource(butterflyImages[assignedButterflies[userPosition]]);
    }

    Integer getUserPosition(){
        int index = 0;
        for(UserInfo curr : currentSession.getParticipantsArray()){
            if(MainActivity.user_info.getUser_id() == curr.getUser_id())
                return index;
            index++;
        }
        return -1;
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

        //User clicks their own bubble, ask them to contribute assigned butterfly
        else if(view_id == participantBubbles[userPosition].getId()){
            //Make dialog fragment and show it here, submit button basically.
            //First check if the user has enough to actually contribute
            String assignedButterflyKey = "user_b"  + assignedButterflies[userPosition] + "_count";

            if(MainActivity.user_info.get_local_atrium().get(assignedButterflyKey) == 0){
                Toast.makeText(this, "No butterflies of that color found in atrium", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.d("Contribute butterfly", "Butterfly found of type: " + assignedButterflyKey);

                //Make a new alert dialog fragment to confirm the contribution and staging.
                AlertDialog.Builder builder = new AlertDialog.Builder(SuperflyGamePage.this);
                builder.setTitle("Superfly Contribution");
                builder.setMessage("Would you like to contribute this butterfly?");
                builder.setPositiveButton("Contribute", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Integer assignedButterfly = assignedButterflies[userPosition];
                        String assignedButterflyKey = "user_b"  + assignedButterfly + "_count";
                        //Set the Users staged butterfly
                        Map<String, Integer> localMap = MainActivity.user_info.get_local_atrium();
                        //Decrement by one
                        localMap.put(assignedButterflyKey, localMap.get(assignedButterflyKey) - 1);
                        MainActivity.user_info.update_local_atrium(localMap);
                        NetworkCalls.updateUserAtrium(MainActivity.user_info.getUser_id(), localMap, SuperflyGamePage.this);
                        NetworkCalls.setUserStagedButterfly(MainActivity.user_info.getUser_id(), assignedButterfly, SuperflyGamePage.this, new RetrofitResponseListener() {
                            @Override
                            public void onSuccess() {
                                recreate();
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(SuperflyGamePage.this, "Couldn't contribute butterfly, try again.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Toast.makeText(SuperflyGamePage.this, "Canceling superfly contribution", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog contributionAlert = builder.create();
                contributionAlert.show();
            }
        }

        //Otherwise, check which other bubble it was. We can include the user's bubble as the condition
        //above will handle it.
        //else if(view_id)

    }


    public class SuperflyStagingDialog extends DialogFragment {
        //AlertDialog.Builder

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Would you like to contribute this butterfly?");
            builder.setPositiveButton("Contribute", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String assignedButterflyKey = "user_b"  + assignedButterflies[userPosition] + "_count";
                    //Set the Users staged butterfly
                    Map<String, Integer> localMap = MainActivity.user_info.get_local_atrium();
                    //Decrement by one
                    localMap.put(assignedButterflyKey, localMap.get(assignedButterflyKey) - 1);
                    MainActivity.user_info.update_local_atrium(localMap);
                    NetworkCalls.updateUserAtrium(MainActivity.user_info.getUser_id(), localMap, SuperflyGamePage.this);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(SuperflyGamePage.this, "Canceling superfly contribution", Toast.LENGTH_SHORT).show();
                }
            });

            return builder.create();
        }
    }

}
