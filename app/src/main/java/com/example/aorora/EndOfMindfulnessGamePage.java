package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

public class EndOfMindfulnessGamePage extends AppCompatActivity implements View.OnClickListener {
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    Context endOfMindfulnessGamePage;
    Animation infinite_blink;
    Animation tap_me_animation;
    ImageView mindfulness_imageview;
    ImageView alpha_channel_mindfulness_imageview;
    View pollen_layout;
    View emitter;
    ParticleSystem myParticle;
    ImageButton pollen_button;
    TextView possible_pollens_tv;
    int possible_points;
    int initial_score;
    TextView score_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_mindfulness_game_page);

        endOfMindfulnessGamePage = this;
        emitter = (View)findViewById(R.id.emiter_pollen);

        mindfulness_imageview = (ImageView) findViewById(R.id.mindfulness_end_of_game_iv);
        alpha_channel_mindfulness_imageview = (ImageView) findViewById(R.id.alpha_channel_end_of_game_iv);
        pollen_button = (ImageButton) findViewById(R.id.end_of_game_pollen_image_emitter);
        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        pollen_layout = findViewById(R.id.end_of_game_pollen_layout);

        score_tv = pollen_layout.findViewById(R.id.pollen_score_layout_tv);
        possible_pollens_tv = (TextView) findViewById(R.id.mindfulness_end_of_game_points_earned_tv);

        possible_points = Integer.parseInt(possible_pollens_tv.getText().toString());
        initial_score = Integer.parseInt(score_tv.getText().toString());

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);


        infinite_blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.infinite_blink);
        alpha_channel_mindfulness_imageview.startAnimation(infinite_blink);

        tap_me_animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.tap_me_animation);

        pollen_button.startAnimation(tap_me_animation);
        pollen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollen_button.clearAnimation();
                pollen_button.setImageResource(R.drawable.half_pollen);
                myParticle = new ParticleSystem(EndOfMindfulnessGamePage.this, 10, R.drawable.pollen, 2000)
                        .setAcceleration(0.0018f, -90)
                        .setAcceleration(0.00013f, 180)
                        .setSpeedByComponentsRange(-0.07f, 0f, -0.2f, -0.15f)
                        .setFadeOut(2000);
                myParticle.emitWithGravity(emitter,Gravity.TOP, 4);
                pollen_button.setClickable(false);

                new CountDownTimer(3000, 100) {

                    public void onTick(long millisUntilFinished) {
                        String cur_score = score_tv.getText().toString();
                        int score = Integer.parseInt(cur_score);
                        score += (int) possible_points * 1/30;
                        score_tv.setText("" + score);

                    }

                    public void onFinish() {
                        myParticle.stopEmitting();
                        int total_score = possible_points + initial_score;
                        score_tv.setText("" + total_score);
                    }
                }.start();
            }
        });


    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(endOfMindfulnessGamePage, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(endOfMindfulnessGamePage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(endOfMindfulnessGamePage, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            Toast.makeText(EndOfMindfulnessGamePage.this, "Community page is under maintenance.", Toast.LENGTH_SHORT).show();
            //to_navigate = new Intent(endOfMindfullnessGamePage, CommunityPage.class);
            //startActivity(to_navigate);
        }
    }
}
