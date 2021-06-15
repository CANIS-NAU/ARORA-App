package com.example.aorora.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aorora.ARScreen;
import com.example.aorora.ButterflyGameActivity;
import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.network.NetworkCalls;

import com.example.aorora.ARScreen.*;

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
        catch_butterfly = (CardView) rootView.findViewById(R.id.catch_butterfly);
        superfly = (CardView) rootView.findViewById(R.id.superfly);

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
        catch_butterfly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent toNavigate = new Intent(getContext(), ARScreen.class);
//                startActivity(toNavigate);
                android.widget.Toast.makeText(getContext(), "Game under development", Toast.LENGTH_SHORT).show();
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
            }
        });

        catch_butterfly.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Play this game to catch more butterflies", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        superfly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent toNavigate = new Intent(getContext(), ARScreen.class);
//                startActivity(toNavigate);
                android.widget.Toast.makeText(getContext(), "Game under development", Toast.LENGTH_SHORT).show();
            }
        });

        superfly.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Create super butterflies", Toast.LENGTH_SHORT).show();
                return true;
            }
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