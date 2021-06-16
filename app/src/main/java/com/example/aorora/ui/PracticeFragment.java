package com.example.aorora.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aorora.ARScreen;
import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.SuperflyGamePage;
import com.example.aorora.SuperflyInvitesPage;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitResponseListener;

public class PracticeFragment extends Fragment {

    CardView catch_butterfly, superfly;
    AlertDialog.Builder builder;
    Integer userPollen;

    public PracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_practice, container, false);
        catch_butterfly = rootView.findViewById(R.id.catch_butterfly);
        superfly = rootView.findViewById(R.id.superfly);

//        builder = new AlertDialog.Builder(getContext());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        catch_butterfly.setOnClickListener(v -> {
            Intent toNavigate = new Intent(getContext(), ARScreen.class);
            startActivity(toNavigate);
            //                android.widget.Toast.makeText(getContext(), "Game under development", Toast.LENGTH_SHORT).show();
//                //TODO: Uncomment the below code to Set the message and title from the strings.xml file
//                builder.setMessage("You are going to spend 10 pollen to catch some butterflies.\n\nPress ok to continue")
//                        .setTitle("Catch Butterflies")
//                        .setCancelable(false)
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
////
//                                Toast.makeText(getContext(),"Ok",Toast.LENGTH_SHORT).show();
//
//                                if (!hasEnoughPollen()) {
//                                    Toast.makeText(getContext(), "Sorry! Not enough pollen! Complete some quests!", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                //Otherwise, we have enough pollen, decrement it and update the backend.
//                                userPollen -= 10;
//                                //Finally do the PUT request with the new pollen value. May need to refresh the UI.
//                                MainActivity.user_info.setUser_pollen(userPollen);
//                                //This will update the backend and set the current pollen to our decremented value.
//                                NetworkCalls.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), userPollen, getContext());
//
//                                //intent to butterfly game
//                                startActivity(new Intent(getContext(), ButterflyGameActivity.class));
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        //  Action for 'NO' Button
//                                        dialog.cancel();
//                                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    ;
//                                });
//                //Creating dialog box
//                AlertDialog alert = builder.create();
//                //Setting the title manually
////                alert.setTitle("AlertDialogExample");
//                alert.show();
        });

        catch_butterfly.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "Play this game to catch more butterflies", Toast.LENGTH_SHORT).show();
            return true;
        });

        superfly.setOnClickListener(v -> {
            Intent to_navigate;
            int session_id = MainActivity.user_info.getUser_superflysession_id();
            //If we aren't in a session right now, go to the invites/creation page.
            if(session_id == -1){
                to_navigate = new Intent(getContext(), SuperflyInvitesPage.class);
                startActivity(to_navigate);
            }
            //Go to our current superfly session instead of the invites page.
            else{
                //Refresh session
                NetworkCalls.getSuperflySession(session_id, getContext(), new RetrofitResponseListener() {
                    @Override
                    public void onSuccess() {
                        //If we completed the game, go to the win screen.
                        if(MainActivity.user_info.getCurrentSession().getSession_ended()){
                            //This takes you to the game page, which then sends you to the finish page.
                            final Intent to_navigate = new Intent(getContext(), SuperflyGamePage.class);
                            startActivity(to_navigate);
                        }
                        //Otherwise move to the game in progress
                        else{
                            NetworkCalls.loadTradeRequests(MainActivity.user_info.getUser_id(), getContext(), new RetrofitResponseListener() {
                                @Override
                                public void onSuccess() {
                                    //Since we loaded our trades, move to the game
                                    final Intent to_navigate = new Intent(getContext(), SuperflyGamePage.class);
                                    startActivity(to_navigate);
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(getContext(), "Couldn't connect to superfly lobby, try again!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "Couldn't connect to superfly sessions.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

//                Intent toNavigate = new Intent(getContext(), SuperflyInvitesPage.class);
//                startActivity(toNavigate);
////                android.widget.Toast.makeText(getContext(), "Game under development", Toast.LENGTH_SHORT).show();
        });

        superfly.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), "Create super butterflies", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), getContext());
    }

    //Check called before launching the game.
    public boolean hasEnoughPollen() {
        return userPollen >= 10;
    }
}