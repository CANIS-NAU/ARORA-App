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

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.model.Superfly;
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
    String[] butterflyColors;

    UserInfo[] participants;
    SuperflySession currentSession;
    Integer[] assignedButterflies;
    Integer participantCount;
    Button startButton;
    Button refreshButton;
    Boolean sessionStarted;
    Boolean sessionEnded;
    Integer userPosition;
    Boolean resetSucceeded;

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
        butterflyColors = getResources().getStringArray(R.array.butterfly_colors);
        stagedButterflies = new ImageView[]{(ImageView) findViewById(R.id.participant0_staged),
                (ImageView) findViewById(R.id.participant1_staged),
                (ImageView) findViewById(R.id.participant2_staged), (ImageView) findViewById(R.id.participant3_staged),
                (ImageView) findViewById(R.id.participant4_staged)};;

        sessionStarted = currentSession.getSession_started();
        sessionEnded = currentSession.getSession_ended();


        backButton = (ImageButton) findViewById(R.id.back_button);
        startButton = findViewById(R.id.start_button);
        refreshButton = findViewById(R.id.refreshsesh_button);

        //Set all onclicklisteners here
        backButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        for(ImageView currBubble : participantBubbles){
            currBubble.setOnClickListener(this);
        }

        if(sessionEnded){
            Intent to_navigate = new Intent(this, SuperflyFinishPage.class);
            startActivity(to_navigate);
        }
        else if(sessionStarted){
            startButton.setText("Contribute butterfly");
            if(!allUsersStaged()){
                startButton.setEnabled(false);
            }
            else{
                startButton.setEnabled(true);
            }
        }

        //Call the initialization function to display the current game configuration.
        initParticipants();

    }

    //Sets up the UI to display participants and their assigned butterflies.
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
        //Check what needs to be contributed to the current superfly if the session is started.
        if(sessionStarted){
            assignedButterflies = currentSession.getAssignedButterflies();
            Log.d("INIT PARTICIPANTS", "Assigned butterflies: " + Arrays.toString(assignedButterflies));

            //Set the current user's bubble image to be the butterfly color they need to contribute.
            userPosition = getUserPosition();
            Log.d("INIT PARTICIPANTS", "Current User's position in session: " + userPosition.toString());
            //Display the needed butterfly
            displayAssignedButterfly();
        }

    }

    void displayAssignedButterfly(){
        participantButterflies[userPosition].setImageResource(butterflyImages[assignedButterflies[userPosition]]);
    }

    //Loop through all participants, return false if one of them does not have a butterfly staged.
    boolean allUsersStaged(){
        boolean staged = true;
        for(int index = 0; index < participantCount; index++){
            UserInfo currUser = participants[index];
            Log.d("AllusersStaged", currUser.toString());
            if(currUser.getUser_staged_butterfly() == -1){
                staged = false;
                break;
            }
        }
        return staged;
    }

    Integer getUserPosition(){
        int index = 0;
        Log.d("Get User Position", Arrays.toString(currentSession.getParticipantsArray()));
        for(UserInfo curr : currentSession.getParticipantsArray()){
            if(MainActivity.user_info.getUser_id() == curr.getUser_id())
                return index;
            index++;
        }
        return -1;
    }

    //Checks to see if the user hasn't staged a butterfly yet and they have enough butterflies to
    //stage a new one for the round.
    private boolean canStageButterfly(String assignedButterflyKey){
        if(MainActivity.user_info.getUser_staged_butterfly() > -1){
            Toast.makeText(this, "Already staged a butterfly this round", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(MainActivity.user_info.get_local_atrium().get(assignedButterflyKey) < 1){
            Toast.makeText(this, "Not enough butterflies, please trade with a friend!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    void addRoundToSession(){
        UserInfo currentParticipant;
        Map<String, Integer> round = new HashMap<String, Integer>();
        String currentButterflyKey;
        Integer currentButterflyType;

        for(int index = 0; index < participantCount; index++){
            currentButterflyKey = "current_b"  + assignedButterflies[index] + "_count";
            //Add a new entry to the map if there is not a current butterfly of that type inserted.
            if(!round.containsKey(currentButterflyKey)){
                round.put(currentButterflyKey, 1);
            }
            //Update the current entry if we have already logged the current type
            else{
                round.put(currentButterflyKey, round.get(currentButterflyKey) + 1);
            }
        }

        Log.d("ROUND TEST", round.toString());

        //Push this to the network
        NetworkCalls.updateSuperflyProgress(currentSession.getSession_id(), round, this, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                //Update local session?
                Log.d("Updated Superfly progress", currentSession.toString());
                //Assume the reset does not have any problems
                resetSucceeded = true;
                //ResetStagedButterfly will set the class member boolean, resetSucceeded, to false
                //if a network error occurs.
                for(int participantIndex = 0; participantIndex < participantCount; participantIndex++){
                    resetStagedButterfly(participantIndex);
                }
                //After everything suceeds, refresh the UI.
                refreshSession();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    void resetStagedButterfly(Integer participantIndex){
        UserInfo currParticipant = participants[participantIndex];
        //Resets the staged butterfly to the empty value -1 for this user.
        NetworkCalls.setUserStagedButterfly(currParticipant.getUser_id(), -1, this, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                Log.d("ResetButterfly", "User: " + currParticipant.getUser_name() + " reset staged.");
                //Now set their staged butterfly locally
                currParticipant.setUser_staged_butterfly(-1);
                Log.d("CurrparticipantRESET", "CurrParticipant staged: "
                        + currParticipant.getUser_staged_butterfly().toString()
                        + "Array indexed participant butterfly: "
                        + participants[participantIndex].getUser_staged_butterfly().toString());
                if(currParticipant.getUser_id() == MainActivity.user_info.getUser_id()){
                    MainActivity.user_info.setUser_staged_butterfly(-1);
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    void refreshSession() {
        //Call the GET request again and then recreate()
        NetworkCalls.getSuperflySession(currentSession.getSession_id(), this, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SuperflyGamePage.this, "Refresh successful", Toast.LENGTH_SHORT).show();
                NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), SuperflyGamePage.this, new RetrofitResponseListener() {
                    @Override
                    public void onSuccess() {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(SuperflyGamePage.this, "All refresh checks done", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure() {

            }
        });

    }

    void sendTradeRequest(UserInfo recipient){
        if(recipient==null)
            return;
        HashMap<String, Integer> requestedButterflies = new HashMap<>();
        requestedButterflies.put("b0_requested", 2);
        requestedButterflies.put("b1_requested", 2);
        NetworkCalls.sendTradeRequest(MainActivity.user_info.getUser_id(), recipient.getUser_id(), requestedButterflies,this, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SuperflyGamePage.this, "Sent trade request!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(SuperflyGamePage.this, "Trade request failed!", Toast.LENGTH_SHORT).show();
            }
        });
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
                if(currentSession.getId_0() != MainActivity.user_info.getUser_id()){
                    Toast.makeText(this, "You must be the session owner to do this.", Toast.LENGTH_SHORT).show();
                }
                //If the current user is participant_0 (the owner) and the session is NOT started.
                else if(!MainActivity.user_info.getCurrentSession().getSession_started()){
                    Toast.makeText(this, "Starting game", Toast.LENGTH_SHORT).show();
                    NetworkCalls.startSession(currentSession.getSession_id(), this, new RetrofitResponseListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(SuperflyGamePage.this, "Starting session!", Toast.LENGTH_SHORT).show();
                            refreshSession();
                        }

                        @Override
                        public void onFailure() {

                        }
                    });

                }
                //Otherwise we are the owner and the session is STARTED, so contribute this round.
                else{
                    Toast.makeText(this, "Contributing butterflies for this round", Toast.LENGTH_SHORT).show();
                    addRoundToSession();
                }


            }
        }

        else if(view_id == refreshButton.getId()){
            //Use StartSession to check current status.
            refreshSession();

        }

        //User clicks their own bubble, ask them to contribute assigned butterfly
        else if(view_id == participantBubbles[userPosition].getId()){
            //Make dialog fragment and show it here, submit button basically.
            //First check if the user has enough to actually contribute
            String assignedButterflyKey = "user_b"  + assignedButterflies[userPosition] + "_count";
            Integer assignedButterflyType = assignedButterflies[userPosition];

            if(canStageButterfly(assignedButterflyKey)) {
                //Set the Users staged butterfly
                Log.d("Contribute butterfly", "Butterfly found of type: " + butterflyColors[assignedButterflyType]);

                //Make a new alert dialog fragment to confirm the contribution and staging.
                AlertDialog.Builder builder = new AlertDialog.Builder(SuperflyGamePage.this);
                builder.setTitle("Superfly Contribution");
                builder.setMessage(Html.fromHtml("Would you like to contribute one <b>" + butterflyColors[assignedButterflyType] + "</b> butterfly?"));
                builder.setPositiveButton("Contribute", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Integer assignedButterfly = assignedButterflies[userPosition];
                        String assignedButterflyKey = "user_b" + assignedButterfly + "_count";
                        //Set the Users staged butterfly
                        Map<String, Integer> localAtrium = MainActivity.user_info.get_local_atrium();
                        Map<String, Integer> atriumUpdates = new HashMap<>();
                        //Decrement by one butterfly in the users atrium
                        atriumUpdates.put(assignedButterflyKey, localAtrium.get(assignedButterflyKey) - 1);
                        MainActivity.user_info.update_local_atrium(atriumUpdates);

                        NetworkCalls.setUserStagedButterfly(MainActivity.user_info.getUser_id(), assignedButterfly, SuperflyGamePage.this, new RetrofitResponseListener() {
                            @Override
                            public void onSuccess() {
                                NetworkCalls.updateUserAtrium(MainActivity.user_info.getUser_id(), atriumUpdates, SuperflyGamePage.this, new RetrofitResponseListener() {
                                    @Override
                                    public void onSuccess() {
                                        //Refresh the user info
                                        NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), SuperflyGamePage.this, new RetrofitResponseListener() {
                                            @Override
                                            public void onSuccess() {
                                                Toast.makeText(SuperflyGamePage.this, "All staging checks passed", Toast.LENGTH_SHORT).show();
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());
                                                overridePendingTransition(0, 0);
                                            }

                                            @Override
                                            public void onFailure() {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });


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
        else {
            UserInfo otherUser = null;

            if(participants[0] != null && view_id == participantBubbles[0].getId()){
                otherUser = participants[0];
            }
            else if(participants[1] != null && view_id == participantBubbles[1].getId()){
                otherUser = participants[1];
            }
            else if(participants[2] != null && view_id == participantBubbles[2].getId()){
                otherUser = participants[2];

            }
            else if(participants[3] != null && participantCount > 3 && view_id == participantBubbles[3].getId()){
                otherUser = participants[3];
            }
            else if(participants[4] != null && view_id == participantBubbles[0].getId()){
                otherUser = participants[4];
            }

            if(sessionStarted)
                Toast.makeText(this, "Sending request to " + otherUser.getUser_name(), Toast.LENGTH_SHORT).show();
                sendTradeRequest(otherUser);
        }
    }


}
