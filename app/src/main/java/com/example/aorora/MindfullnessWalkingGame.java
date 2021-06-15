package com.example.aorora;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.aorora.network.NetworkCalls;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;


public class MindfullnessWalkingGame extends AppCompatActivity {
    //Adding a finish button and handler for integration. Handler shows the button after x seconds.
    Button finishButton;
    //Testing variable to make the finish button pop up after this amount of milliseconds.
    Integer timeUntilFinished;
    Handler handler;
    BroadcastReceiver broadcastReceiver;
    ImageButton exit;
    ParticleSystem myParticle;
    View emitter;
    Context walking;
    int count;
    int stage_counter;
    TextView count_tv;
    Animation flap;
    ImageView walking_loading;
    AlphaModifier myAlpha;
    Animation pulse;
    LottieAnimationView animationView;
    TextView hint_walking;
    Context mindfulness_walking;
    ConstraintLayout walking_game_layout;
    int game_theme;
    MediaPlayer walking_music;
    Integer pollen_payout;
    Boolean testMode;

    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_walking_game);
        //DEV MODE FLAG TO END THE ACTIVITY QUICKLY
        testMode = false;
        //Display the finishButton after x seconds
        finishButton = (Button) findViewById(R.id.finish_walk_btn);
        timeUntilFinished = 6000;
        exit = (ImageButton) findViewById(R.id.mindfullness_walking_exit_button);
        emitter = (View) findViewById(R.id.emiter_top);
        walking_game_layout = findViewById(R.id.walking_game_background);

        myAlpha = new AlphaModifier(1000, 10, 0, 8000, new AccelerateInterpolator());
        mindfulness_walking = this;
        pollen_payout = 30;

        walking_music = MediaPlayer.create(MindfullnessWalkingGame.this,R.raw.mindfulnesswalking);

        flap = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.butterfly_flap);
        flap.setRepeatCount(4);

        if(!walking_music.isPlaying())
        {
            walking_music.start();
        }

        //Display a finish button for testing.
        if(testMode){
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishButton.setVisibility(View.VISIBLE);
                }
            },timeUntilFinished);
        }

        Toast.makeText(MindfullnessWalkingGame.this,
                "Listen to the prompt as you walk slowly in a safe place", Toast.LENGTH_SHORT).show();

        walking = this;
        count = 0;
        stage_counter = 0;


        if(this.getIntent().hasExtra("Game Theme"))
        {
            game_theme = this.getIntent().getIntExtra("Game Theme", 1);
            walking_game_layout.setBackgroundResource(R.drawable.orange_mountain_background);
            if(game_theme == 1)
            {
                walking_game_layout.setBackgroundResource(R.drawable.blue_mountain_background);
            }
            else if(game_theme == 2)
            {
                walking_game_layout.setBackgroundResource(R.drawable.green_mountain_background);
            }
            else
            {
                walking_game_layout.setBackgroundResource(R.drawable.orange_mountain_background);
            }
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(walking_music.isPlaying())
                {
                    walking_music.stop();
                }
                Intent to_navigate = new Intent(MindfullnessWalkingGame.this, MindfullnessWalking.class);
                startActivity(to_navigate);
                stopTracking();
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("activity_intent")) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                }
            }
        };
        startTracking();

        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(walking_music.isPlaying()){
                    walking_music.stop();
                }
                Intent to_navigate = new Intent(mindfulness_walking, ReceiptPage.class);
                to_navigate.putExtra("NavigatedFrom", 3);
                to_navigate.putExtra("Game Theme", game_theme);
                to_navigate.putExtra("PollenPayout", pollen_payout);
                stopTracking();
                startActivity(to_navigate);
                int new_user_points = MainActivity.user_info.getUser_pollen() + pollen_payout;;
                MainActivity.user_info.setUser_pollen(new_user_points);
                NetworkCalls.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), new_user_points, MindfullnessWalkingGame.this);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("activity_intent"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void startTracking() {
        Intent intent1 = new Intent( MindfullnessWalkingGame.this, BackgroundDetectedActivitiesService.class);
        startService(intent1);
    }

    private void stopTracking() {
        Intent intent = new Intent(walking, BackgroundDetectedActivitiesService.class);
        stopService(intent);
        if(myParticle != null )
        {
            myParticle.stopEmitting();
        }
    }
    class Timer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            count += 1;
            if(millisUntilFinished < 5000) {
                count_tv.setText("" + count);
            }
            else
            {
                count_tv.setText("" + count);
            }
        }

        @Override
        public void onFinish() {
        }

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            //Allow us to leave the Activity as normal, but we need to stop the recording like the x button does.
            if(walking_music.isPlaying())
            {
                Log.e("MUSIC", " STOPPED");
                walking_music.stop();
            }
//            time.cancel();
            super.onBackPressed();
            finish();

        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
