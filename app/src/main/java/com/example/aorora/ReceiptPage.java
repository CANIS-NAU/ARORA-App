package com.example.aorora;

import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.aorora.network.NetworkCalls;

/*
This page is presented after a user completes the M1 - M3 mindfulness activities. The user is
shown the amount of pollen they earned for this activity as well as their total amount of pollen
tied to their account. We pass several extras to this activity. The first, NavigatedFrom,
tells us the previous activity we navigated from in case the user wants to retry. The second,
GAME, is used to reset the breathing activity based on the user's previous selection there.
The third, Game Theme, is used
 */
public class ReceiptPage extends AppCompatActivity implements View.OnClickListener{

    ImageView pollen_view_text_view_holder;
    ImageButton continue_button;
    ImageButton replay_button;
    ImageButton home_button;
    LottieAnimationView jar_button;
    TextView tap_me_text;
    TextView go_to_pollen_tore_text;
    TextView pollen_score_tv;
    TextView receipt_desc_tv;
    TextView receipt_desc_tv_2;
    TextView pollenEarnedScoreTv;
    TextView pollenTotalCountTv;

    Integer userPollen;
    Integer pollenPayout;
    int game_settings;
    int coming_from;
    int game_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_page);

        userPollen = MainActivity.user_info.getUser_pollen();

        pollen_view_text_view_holder = findViewById(R.id.receipt_page_pollen_point_holder);
        continue_button = findViewById(R.id.receipt_page_exit_activity_button);
        replay_button = findViewById(R.id.play_again_button_receipt_page);
        home_button = findViewById(R.id.receipt_page_home_button);
        pollen_score_tv = findViewById(R.id.receipt_page_pollen_points_tv);
        jar_button = findViewById(R.id.receipt_page_jar_button);
        tap_me_text = findViewById(R.id.tap_to_collect_receipt_tv);
        receipt_desc_tv = findViewById(R.id.receipt_desc_text_view);
        receipt_desc_tv_2 = findViewById(R.id.receipt_desc_text_view_2);
        pollenEarnedScoreTv = findViewById(R.id.pollen_earnedcount_tv);
        pollenTotalCountTv = findViewById(R.id.pollen_totalcount_tv);

        continue_button.setOnClickListener(this);
        replay_button.setOnClickListener(this);
        home_button.setOnClickListener(this);
        jar_button.setOnClickListener(this);
        Intent current_intent = this.getIntent();
        if (current_intent.hasExtra("NavigatedFrom")) {
            coming_from = current_intent.getIntExtra("NavigatedFrom", 1);
            Log.e("NAVIGATED FROM", "" + coming_from);
            if (coming_from == 1) {
                if (current_intent.hasExtra("GAME")) {
                    game_settings = current_intent.getIntExtra("GAME", 1);
                }
            } else if (coming_from == 2) {
                if (current_intent.hasExtra("Game Theme")) {
                    game_theme = current_intent.getIntExtra("Game Theme", 1);
                }
            } else {
                if (current_intent.hasExtra("Game Theme")) {
                    game_theme = current_intent.getIntExtra("Game Theme", 1);
                }
            }
        }
        //Init user pollen display values
        //Total pollen in the account
        userPollen = MainActivity.user_info.getUser_pollen();
        //Make sure this is initialized so we don't display a null value without an extra.
        pollenPayout = 0;
        //Grab the actual payout value passed from the previous activity.
        if (current_intent.hasExtra("PollenPayout")) {
            pollenPayout = current_intent.getIntExtra("PollenPayout", 1);
            //Set the amount of pollen earned in the textview
            pollenEarnedScoreTv.setText(pollenPayout.toString());
            pollenTotalCountTv.setText(userPollen.toString());
            pollen_score_tv.setText(userPollen.toString());
            NetworkCalls.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), userPollen, this);
        }
    }


    //Intential override to prevent improper back button navigation.
    @Override
    public void onBackPressed() {
        //Do nothing. Do not pop the stack and go back into the mindfulness activity.
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == continue_button.getId())
        {
            to_navigate = new Intent(ReceiptPage.this, MindfullnessBreathing.class);
            startActivity(to_navigate);
        }
        else if(view_id == replay_button.getId())
        {
            switch (coming_from){
                case 1:
                    to_navigate = new Intent(ReceiptPage.this, MindfullnessBreathingGame.class);
                    to_navigate.putExtra("TimerValue", game_settings);
                    startActivity(to_navigate);
                    break;
                case 2:
                    to_navigate = new Intent(ReceiptPage.this, MindfulnessMeditationGame_R.class);
                    to_navigate.putExtra("Theme",game_theme);
                    startActivity(to_navigate);
                    break;
                case 3:
                    to_navigate = new Intent(ReceiptPage.this, MindfullnessWalkingGame.class);
                    to_navigate.putExtra("Game Theme", game_theme);
                    startActivity(to_navigate);
                    break;
            }
        }
        else if(view_id == home_button.getId())
        {
            to_navigate = new Intent(ReceiptPage.this, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == jar_button.getId())
        {
            jar_button.setClickable(false);
            jar_button.setAnimation(R.raw.jar_pop);
            jar_button.loop(false);
            jar_button.playAnimation();
            new CountDownTimer(3000, 100){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    pollenEarnedScoreTv.setVisibility(View.INVISIBLE);
                    pollenTotalCountTv.setVisibility(View.INVISIBLE);
                    receipt_desc_tv.setVisibility(View.INVISIBLE);
                    receipt_desc_tv_2.setVisibility(View.INVISIBLE);

                    tap_me_text.setVisibility(View.INVISIBLE);
                    pollen_score_tv.setVisibility(View.VISIBLE);
                    continue_button.setVisibility(View.VISIBLE);
                    replay_button.setVisibility(View.VISIBLE);
                    home_button.setVisibility(View.VISIBLE);
                    jar_button.pauseAnimation();
                    pollen_view_text_view_holder.setVisibility(View.VISIBLE);
                }
            }.start();
        }
    }
}
