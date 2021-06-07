package com.example.aorora;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

/**
 * TODO: remove useless code
 * TODO: rename some vars
 * TODO: comments
 */

public class MindfullnessBreathingGame extends AppCompatActivity {

    ImageButton exit_button;
    ImageView inhale_button;
    ImageView butterfly_image;
    TextView remaining_breaths;
    Animation enlarge;
    Animation shrink;
    Vibrator myVibrate;
    boolean isRun;
    boolean clickable;
    static int count;
    static boolean cont;
    Context mindfullness_breathing_game;
    MediaPlayer breathing_music;
    String inhale_text = "Press and hold as \n you breath in";
    String exhale_text = "Let go of the button and exhale \n and leave the button";
    TextView desc_tv;
    Dialog myDialog;
    int tempBreathCount;
    int pollen_payout;
    int possible_points;
    Animation tap_me_animation;
    boolean is_button_still_clicked;
    boolean performed_click;
    final Timer myTimer= new Timer(3000,100);
    int initial_game_count;
    private long pressedTime;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_breathing_game);

        // checks if inside the run because as soon as it finishes run it goes to cancel
        isRun = false;
        clickable = true;
        cont = false;
        mindfullness_breathing_game = this;
        possible_points = 100;
        is_button_still_clicked = false;
        performed_click = false;
        pollen_payout = 10;

        //TODO: for dev purposes remove
        tempBreathCount = 5;

        tutorialPopUp();

        LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.setSpeed(1f);
        animationView.playAnimation();

        myDialog = new Dialog(this);


        tap_me_animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.tap_me_animation);
        desc_tv = (TextView) findViewById(R.id.desc_to_breathinggame_tv);
        exit_button = (ImageButton) findViewById(R.id.exit_button_walking);
        inhale_button = (ImageView) findViewById(R.id.breathing_inhale_button);
        butterfly_image = (ImageView) findViewById(R.id.butterfly_image_breathinggame);
        remaining_breaths = (TextView) findViewById(R.id.breath_count_tv);

        myVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        breathing_music = MediaPlayer.create(MindfullnessBreathingGame.this,R.raw.readingbreathing1);

        if(getIntent().hasExtra("TimerValue"))
        {
            //Changing default value from 1 to 2
            int text = getIntent().getIntExtra("TimerValue", 1);

            //Disabling all breath options to dev temp count
          
            if(text == 1)
            {
                initial_game_count = text;
                possible_points = tempBreathCount;
                text = tempBreathCount;
            }
            else if( text == 2)
            {
                initial_game_count = text;
                possible_points = tempBreathCount;
                text = tempBreathCount;
            }
            else{
                initial_game_count = text;
                possible_points = tempBreathCount;
                text = tempBreathCount;
            }
            remaining_breaths.setText(text + " Breaths");
        }
        enlarge = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.butterfly_opening);
        shrink = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.butterfly_closing);

        final Handler handler = new Handler();
        //This runs in a Runnable thread
        final Runnable mLongPressed = new Runnable() {
            public void run() {
                Log.d("VERBOSE", "run: INSIDE RUN ");
                butterfly_image.startAnimation(shrink);
                isRun = true;
                //Causes the phone to vibrate using the vibrate function of the myVibrate object.
                myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

                String temp = remaining_breaths.getText().toString();
                String counted = "";
                int index = 0;
                while (index<2)
                {
                    char current = temp.charAt(index);
                    if(current != ' ') {
                        counted = counted + current;
                    }
                    index++;
                }
                count = Integer.parseInt(counted);
                if(cont)
                {
                    //MainActivity.user_points += 1;
                    count = count + 1;
                }
                else
                {
                    count = count -1;
                }
                remaining_breaths.setText(count + " Breaths");
            }
        };

        inhale_button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && clickable) {
                    butterfly_image.startAnimation(enlarge);
                    handler.postDelayed(mLongPressed, 3000);
                    myTimer.start();

                    is_button_still_clicked = true;
                    return true;
                }
                if((event.getAction() == MotionEvent.ACTION_UP)) {
                    handler.removeCallbacks(mLongPressed);
                    Log.d("VERBOSE", "run: INSIDE CANCEL");
                    is_button_still_clicked = false;
                    if(!isRun){
                        butterfly_image.clearAnimation();
                        myTimer.cancel();
                    }
                    //breathing_music.pause();
                    return false;
                }

                if(!breathing_music.isPlaying())
                {
                    breathing_music.start();
                }
                return false;
            }
        });


        shrink.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("VERBOSE", "run: INSIDE 2nd ANIM");
                clickable = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (is_button_still_clicked)
                {
                    butterfly_image.startAnimation(enlarge);
                    handler.postDelayed(mLongPressed, 3000);
                    myTimer.start();
                }
                //The game is over when count reaches 0, navigate to the reciept page.
                if(count == 0)
                {
                    //DialogFragment newFragment = new BreathDialog();
                    //newFragment.show(getSupportFragmentManager(), "CONTINUE");
                    if(breathing_music.isPlaying())
                    {
                        breathing_music.stop();
                    }

                    int new_user_points = MainActivity.user_info.getUser_pollen() + pollen_payout;
                    Log.e("NEW USER POINTS ", new_user_points + " ");

                    MainActivity.user_info.setUser_pollen(new_user_points);

                    Intent to_navigate = new Intent(mindfullness_breathing_game, ReceiptPage.class);

                    to_navigate.putExtra("NavigatedFrom", 1);

                    //Ship the new pollen values to the Recieptpage.
                    to_navigate.putExtra("PollenPayout", pollen_payout);

                    to_navigate.putExtra("GAME", initial_game_count);
                    startActivity(to_navigate);
                }
                else
                {
                    desc_tv.setText("" + inhale_text);
                    myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                isRun = false;
                clickable = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //User clicks the x button to end the game early
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!cont && count > 0)
                {
                    possible_points = 0;
                }
                if(breathing_music.isPlaying())
                {
                    breathing_music.stop();
                }
                //kill activity with no pollen payout
                //TODO: put a warning popup to close activity
                finish();
            }
        });
    }

    class Timer extends CountDownTimer{

        public Timer(long ms, long countdownInterval)
        {
            super(ms,countdownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            if(millisUntilFinished < 2000 ){
                myVibrate.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            if(millisUntilFinished < 1000 ){
                myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else
            {
                myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
        @Override
        public void onFinish() {
            desc_tv.setText("" + exhale_text);
        }
    }


    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            //Allow us to leave the Activity as normal, but we need to stop the recording like the x button does.
            if(breathing_music.isPlaying())
            {
                Log.e("MUSIC", " STOPPED");
                breathing_music.stop();
            }
            myTimer.cancel();
            super.onBackPressed();
            finish();

        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


    @Override
    protected void onPause() {
        super.onPause();
        breathing_music.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        breathing_music.stop();

    /**Tutorial pop-up that overlays the game's view*/
    public void tutorialPopUp()
    {
        final AlertDialog myDialog = new AlertDialog.Builder(this).create();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //This allows us to use custom views instead of the basic AlertDialog
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.custom_dialog_breathing_tutorial, null);

        Button cont_button = dialogView.findViewById(R.id.continue_button);
        cont_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.setView(dialogView);
        myDialog.show();
    }
}
