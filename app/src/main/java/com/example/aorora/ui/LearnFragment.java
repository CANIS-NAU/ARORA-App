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
import com.example.aorora.MindfullnessBreathing;
import com.example.aorora.MindfullnessMeditation;
import com.example.aorora.MindfullnessWalking;
import com.example.aorora.R;

public class LearnFragment extends Fragment {

    CardView mindfulness_breathing, mindfulness_meditation, mindfulness_walking;

    public LearnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        mindfulness_breathing = (CardView) rootView.findViewById(R.id.mindfulness_breathing);
        mindfulness_meditation = (CardView) rootView.findViewById(R.id.mindfulness_meditation);
        mindfulness_walking = (CardView) rootView.findViewById(R.id.mindfulness_walking);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

// -------------------------------------------------------------------------------------------------
        mindfulness_breathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNavigate = new Intent(getContext(), MindfullnessBreathing.class);
                startActivity(toNavigate);
//                //Uncomment the below code to Set the message and title from the strings.xml file
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

        mindfulness_breathing.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Relieve your stress, Breath In - Breath Out", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
// -------------------------------------------------------------------------------------------------
        mindfulness_meditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNavigate = new Intent(getContext(), MindfullnessMeditation.class);
                startActivity(toNavigate);
//                //Uncomment the below code to Set the message and title from the strings.xml file
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

        mindfulness_meditation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Practice Meditation", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
// -------------------------------------------------------------------------------------------------
        mindfulness_walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNavigate = new Intent(getContext(), MindfullnessWalking.class);
                startActivity(toNavigate);
//                //Uncomment the below code to Set the message and title from the strings.xml file
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

        mindfulness_walking.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Exercise Walking", Toast.LENGTH_SHORT).show();
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

}