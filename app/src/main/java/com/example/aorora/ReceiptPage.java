package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aorora.network.NetworkCalls;
import com.example.aorora.ui.LearnFragment;

import org.w3c.dom.Text;

import org.w3c.dom.Text;

/*
This page is presented after a user completes the M1 - M3 mindfulness activities. The user is
shown the amount of pollen they earned for this activity as well as their total amount of pollen
tied to their account. We pass several extras to this activity. The first, NavigatedFrom,
tells us the previous activity we navigated from in case the user wants to retry. The second,
GAME, is used to reset the breathing activity based on the user's previous selection there.
The third, Game Theme, is used
 */
public class ReceiptPage extends AppCompatActivity{
    Context mContext;

    ImageView pollenImage;
    AnimationDrawable pollenAnimation;
    TextView promptText;
    CountDownTimer countDownTimer;

    TextView pollenEarned;
    TextView pollenEarnedCount;
    TextView totalPollen;
    TextView totalPollenCount;

    ImageView closedPouch;

    ImageButton homeButton;
    TextView homeButtonText;

    Integer userPollen;
    Integer pollenPayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_page);
        mContext = this;

        userPollen = MainActivity.user_info.getUser_pollen();
        pollenImage = findViewById(R.id.pollenImage);
        pollenImage.setBackgroundResource(R.drawable.pollen_collection_animation);
        pollenAnimation = (AnimationDrawable) pollenImage.getBackground();
        promptText = findViewById(R.id.press_text);
        pollenEarned = findViewById(R.id.earnedTextView);
        pollenEarnedCount = findViewById(R.id.pollenEarnedCount);
        totalPollen = findViewById(R.id.totalTextView);
        totalPollenCount = findViewById(R.id.totalPollenCount);
        closedPouch = findViewById(R.id.closedPouch);
        homeButton = findViewById(R.id.homeButton);
        homeButtonText = findViewById(R.id.homeButtonText);


        pollenImage.setOnClickListener(v -> {
            pollenImage.setClickable(false);
            pollenAnimation.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showReceiptInfo();
                }
            }, 3000);

        });

        //Home button listener will send user to home screen and destroy this one.
        homeButton.setOnClickListener(v -> {
            finish();
        });


        //Init user pollen display values
        //Total pollen in the account
        userPollen = MainActivity.user_info.getUser_pollen();
        //Make sure this is initialized so we don't display a null value without an extra.
        pollenPayout = 0;

        //Grab the actual payout value passed from the previous activity.
        Intent current_intent = this.getIntent();
        if (current_intent.hasExtra("PollenPayout")) {
            pollenPayout = current_intent.getIntExtra("PollenPayout", 1);
            NetworkCalls.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), userPollen, this);
        }
    }

    private void showReceiptInfo() {
        pollenImage.setVisibility(View.GONE);
        promptText.setVisibility(View.GONE);
        pollenEarned.setVisibility(View.VISIBLE);
        pollenEarnedCount.setVisibility(View.VISIBLE);
        totalPollen.setVisibility(View.VISIBLE);
        totalPollenCount.setVisibility(View.VISIBLE);
        closedPouch.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
        homeButtonText.setVisibility(View.VISIBLE);
      
        pollenEarnedCount.setText(Integer.toString(pollenPayout));
        totalPollenCount.setText(Integer.toString(userPollen));
    }
}
