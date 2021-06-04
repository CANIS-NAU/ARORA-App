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
        //animationView = findViewById(R.id.animation_view_walking);

        //walking_loading = findViewById(R.id.loading_walking_image_view);
        //pulse = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tap_me_animation);


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
                    //handleUserActivity(type, confidence);
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

                //NetworkCalls.updateDailyTaskM3(user_info.getUser_id(), 1, walking);
                //NetworkCalls.createQuestReport(3, user_info.getUser_id(),mindfulness_walking);
            }
        });

        /*walking_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_navigate = new Intent(mindfulness_walking, ReceiptPage.class);
                to_navigate.putExtra("NavigatedFrom", 3);
                to_navigate.putExtra("Game Theme", game_theme);
                stopTracking();
                startActivity(to_navigate);
                int user_points = MainActivity.user_info.getUser_pollen();
                user_points += count;
                NetworkCalls.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), user_points, MindfullnessWalkingGame.this);
                NetworkCalls.updateDailyTaskM3(user_info.getUser_id(), 1, walking);
                NetworkCalls.createQuestReport(3, user_info.getUser_id(),mindfulness_walking);
            }
        });*/

//        walking_loading.setClickable(false);

    }
/*
    private void handleUserActivity(int type, int confidence) {

        String label = "";

        switch (type) {
            case DetectedActivity.IN_VEHICLE: {
                label = "IN_VEHICLE";
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                label = "ON_BICYCLE";
                break;
            }
            case DetectedActivity.ON_FOOT: {
                label = "ON_FOOT";
                break;
            }
            case DetectedActivity.RUNNING: {
                label = "RUNNING";
                break;
            }
            case DetectedActivity.STILL: {
                label = "STILL";
                break;
            }
            case DetectedActivity.TILTING: {
                label = "TILTING";
                break;
            }
            case DetectedActivity.WALKING: {
                label = "WALKING";
                break;
            }
            case DetectedActivity.UNKNOWN: {
                label = "UNKNOWN";
                break;
            }
        }

        Log.e("ACTION_DETECTION", "User activity: " + label + ", Confidence: " + confidence);

        MindfullnessWalkingGame.Timer myTimer= new MindfullnessWalkingGame.Timer(10000,1000);

        if (confidence > 70) {
            Log.e("ACTION_DETECTION FINAL", "User activity: " + label + ", Confidence: " + confidence);
            //label == "WALKING" || label == "RUNNING" || label == "ON_FOOT"
            if(label == "WALKING" || label == "RUNNING" || label == "ON_FOOT")
            {
                String title = "TITLE";
                String body;
                String subject = "Mindfulness Walking";
                Notification notify = null;
                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                /* Removing growing flower animation
                hint_walking.setVisibility(View.INVISIBLE);

                if(stage_counter == 0)
                {
                    body = "You just took your first step"; //to grow your flower"; ---Cheap fix because we still may have a flower, but as a desert one
                    notify=new Notification.Builder
                            (getApplicationContext()).setContentTitle(title).setContentText(body).
                            setContentTitle(subject).setSmallIcon(R.drawable.walking_loading_25).build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                    notif.notify(0, notify);
                    /* Commented out until we can get the animated version of the desert flower
                        animationView.setAnimation(R.raw.stage_1);
                        animationView.playAnimation();

                    walking_loading.setImageResource(R.drawable.walking_loading_25);
                }
                else if(stage_counter == 5)
                {
                    body = "Your flower reached the stage 2! Keep walking";
                    notify=new Notification.Builder
                            (getApplicationContext()).setContentTitle(title).setContentText(body).
                            setContentTitle(subject).setSmallIcon(R.drawable.walking_loading_50).build();
                    /* Commented out until we can get the animated version of the desert flower
                        animationView.setAnimation(R.raw.stage_2);
                        animationView.playAnimation();

                    walking_loading.setImageResource(R.drawable.walking_loading_50);
                }
                else if(stage_counter == 10)
                {
                    body = "Your flower reached the stage 3! Keep walking";
                    notify=new Notification.Builder
                            (getApplicationContext()).setContentTitle(title).setContentText(body).
                            setContentTitle(subject).setSmallIcon(R.drawable.walking_loading_75).build();
                    /* Commented out until we can get the animated version of the desert flower
                        animationView.setAnimation(R.raw.stage_3);
                        animationView.playAnimation();

                    walking_loading.setImageResource(R.drawable.walking_loading_75);
                }
                else if(stage_counter == 15)
                {
                    animationView.setAnimation(R.raw.stage_4);
                    animationView.playAnimation();
                    walking_loading.setImageResource(R.drawable.walking_loading_100);
                    new CountDownTimer(3000,100) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            walking_loading.setClickable(true);
                            walking_loading.setImageResource(R.drawable.walking_loading_finish);
                        }
                    }.start();
                }

                //stage counter is how you understand how many times service requested and this requests go off every
                // 10 seconds so you can approximate how far they have walked.

                stage_counter++ ;

                /*
                butterfly.startAnimation(flap);
                if(myParticle != null)
                {
                    myParticle.cancel();
                }
                myParticle = new ParticleSystem(MindfullnessWalkingGame.this, 10, R.drawable.pollen, 8000)
                        .setAcceleration(0.00013f, 90)
                        .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.08f)
                        .setFadeOut(8000, new AccelerateInterpolator())
                        .addModifier(myAlpha);
                myParticle.emitWithGravity(emitter, Gravity.BOTTOM, 1);
                myTimer.start();

            }
            else
            {
                hint_walking.setVisibility(View.VISIBLE);
                walking_loading.startAnimation(pulse);
                myTimer.cancel();
            }
        }
    }*/

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
