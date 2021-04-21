package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MindfullnessSelection extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {
    Context mindfullnessSelection;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton mindfullness_breathing;
    ImageButton mindfullness_meditation;
    ImageButton mindfullness_walking;
    ImageView butterfly_view;
    GestureDetector gestureDetector;
    View mindfulness_selection_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_selection);
        mindfullnessSelection = this;

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        quest_button_bottombar.setImageResource(R.drawable.quest_button_filled);
        mindfullness_breathing = (ImageButton) findViewById(R.id.mindfulness_breathing_app);
        mindfullness_meditation = (ImageButton) findViewById(R.id.mindfullness_meditation_button);
        mindfullness_walking = (ImageButton) findViewById(R.id.mindfullness_walking_button);
        butterfly_view = (ImageView) findViewById(R.id.user_butterfly_imageView_mindfullness);
        mindfulness_selection_view = findViewById(R.id.mindfulness_selection_view);
        gestureDetector = new GestureDetector(mindfullnessSelection, MindfullnessSelection.this);
        mindfulness_selection_view.setOnTouchListener(touchListener);
        switch (MainActivity.user_info.getUser_current_butterfly()){
            case 0:
                butterfly_view.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                butterfly_view.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                butterfly_view.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                butterfly_view.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                butterfly_view.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                butterfly_view.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                butterfly_view.setImageResource(R.drawable.orange_butterfly_image);
                break;
        }

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        mindfullness_breathing.setOnClickListener(this);
        mindfullness_meditation.setOnClickListener(this);
        mindfullness_walking.setOnClickListener(this);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return gestureDetector.onTouchEvent(event);

        }
    };
    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        boolean to_survey = false;
        int mindfullness_id = 0;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessSelection, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessSelection, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessSelection, CommunityPage.class);
            startActivity(to_navigate);
        }
        else if(view_id == mindfullness_breathing.getId())
        {
            to_navigate = new Intent(mindfullnessSelection, MindfullnessBreathing.class);
            startActivity(to_navigate);
        }
        else if(view_id == mindfullness_meditation.getId())
        {
            to_navigate = new Intent(mindfullnessSelection, MindfullnessMeditation.class);
            startActivity(to_navigate);
        }
        else if(view_id == mindfullness_walking.getId())
        {
            to_navigate = new Intent(mindfullnessSelection, MindfullnessWalking.class);
            startActivity(to_navigate);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > 150) {
            Intent homePage = new Intent(mindfullnessSelection, HomeScreen.class);
            startActivity(homePage);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        else {
            return true;
        }
    }
}
