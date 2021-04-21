package com.example.aorora;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MindfullnessBreathingGame extends AppCompatActivity {

    static ImageButton exit_button;
    ImageView inhale_button;
    ImageView butterfly_image;
    TextView remaining_breaths;
    TextView remaining_sec;
    Animation enlarge;
    Animation shrink;
    Vibrator myVibrate;
    boolean isRun;
    boolean clickable;
    boolean isTwoDigit;
    static int count;
    static boolean cont;
    Context mindfullness_breathing_game;
    MediaPlayer breathing_music;
    String inhale_text = "Press and hold as \n you breath in";
    String exhale_text = "Let go of the button and exhale \n and leave the button";
    TextView desc_tv;
    Dialog myDialog;
    TextView score_tv;
    TextView pollen_points_desc_tv;
    ImageButton pollen_button;
    int points_to_collect;
    int initial_score;
    int tempBreathCount;
    int pollen_payout;
    View pollen_layout;
    View emitter;
    int possible_points;
    Button pop_up_exit;
    Animation tap_me_animation;
    boolean is_button_still_clicked;
    boolean performed_click;
    final Timer myTimer= new Timer(3000,100);
    int initial_game_count;
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
        tempBreathCount = 2;
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
        remaining_sec = (TextView) findViewById(R.id.remaining_time_breathing_tv);

        myVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        breathing_music = MediaPlayer.create(MindfullnessBreathingGame.this,R.raw.readingbreathing1);

        remaining_sec.setText("3 Seconds");
        if(getIntent().hasExtra("TimerValue"))
        {
            //Changing default value from 1 to 2
            int text = getIntent().getIntExtra("TimerValue", 2);

           //Disabling 5 breath option for testing purposes, possible_points is the amount of breaths remaining.
            if(text == 1)
            {
                initial_game_count = text;
                possible_points = tempBreathCount;
                text = tempBreathCount;
            }

           else if( text == 2)
            {
                initial_game_count = text;
                possible_points = 10;
                text = 10;
            }

            /*
            Disabling 15 breath option for testing purposes
            else{
                initial_game_count = text;
                possible_points = 15;
                text = 15;
            }
            */
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    myVibrate.vibrate(500);
                }

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
                        remaining_sec.setText("3 Seconds");
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
                    //TODO: Why are the network calls here instead of recieptpage? KISS and add a flat number of points when we reach that page.
                    //NetworkCalls.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), new_user_points, MindfullnessBreathingGame.this);
                    //Now update our local pollen value
                    MainActivity.user_info.setUser_pollen(new_user_points);
                    //This old GET request was causing race conditions with the PATCH above, and wasnt necessary.
                    //NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), MindfullnessBreathingGame.this);
                    //Set the pollen value locally
                    Intent to_navigate = new Intent(mindfullness_breathing_game, ReceiptPage.class);
                    to_navigate.putExtra("NavigatedFrom", 1);
                    //Ship the new pollen values to the Recieptpage.
                    to_navigate.putExtra("PollenPayout", pollen_payout);
                    //NetworkCalls.updateDailyTaskM1(user_info.getUser_id(), 1, mindfullness_breathing_game);
                    //NetworkCalls.createQuestReport(1, user_info.getUser_id(),mindfullness_breathing_game);

                    to_navigate.putExtra("GAME", initial_game_count);
                    startActivity(to_navigate);
                }
                else
                {
                    desc_tv.setText("" + inhale_text);
                    remaining_sec.setText("3 Seconds");
                    myVibrate.vibrate(500);
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
                Intent to_navigate = new Intent(mindfullness_breathing_game, MindfullnessBreathing.class);
                if(breathing_music.isPlaying())
                {
                    breathing_music.stop();
                }
                startActivity(to_navigate);
            }
        });
        /*
        myDialog.setContentView(R.layout.custom_dialog_breathing);
        pop_up_exit = (Button) myDialog.findViewById(R.id.button_exercise_select);
        pop_up_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit_button.performClick();
            }
        });
       */

    }

    class Timer extends CountDownTimer{

        public Timer(long ms, long countdownInterval)
        {
            super(ms,countdownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            if(millisUntilFinished < 2000 ){
                remaining_sec.setText("2 Seconds");
                myVibrate.vibrate(350);
            }
            if(millisUntilFinished < 1000 ){
                remaining_sec.setText("1 Seconds");
                myVibrate.vibrate(700);
            }
            else
            {
                myVibrate.vibrate(100);
            }
        }
        @Override
        public void onFinish() {
            remaining_sec.setText("0 Seconds");
            desc_tv.setText("" + exhale_text);
        }
    }

    /*
    public static class BreathDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.custom_dialog_breathing,null))
                    .setTitle("Congratulations!")
                    .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            cont = true;
                        }
                    })
                    .setNegativeButton(R.string.Exit, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            exit_button.performClick();
                        }
                    });

            return builder.create();
        }
    }


    public void ShowPopup() {
        if(breathing_music.isPlaying())
        {
            breathing_music.stop();
        }
        emitter = myDialog.findViewById(R.id.emiter_pollen);
        pollen_points_desc_tv = myDialog.findViewById(R.id.pollens_earned_receipt_tv);
        String pollen_points_desc = pollen_points_desc_tv.getText().toString();
        String temp = "";
        for(int i = 0; i < pollen_points_desc_tv.length(); i++)
        {
            if(Character.isDigit(pollen_points_desc.charAt(i)))
            {
                temp += pollen_points_desc.charAt(i);
            }
        }
        points_to_collect = Integer.parseInt(temp);
        Log.e("POLLEN POINTS", "" + temp);
        final ParticleSystem myParticle = new ParticleSystem(MindfullnessBreathingGame.this, 10, R.drawable.pollen, 2000)
                .setAcceleration(0.0018f, -90)
                .setAcceleration(0.00013f, 180)
                .setSpeedByComponentsRange(-0.07f, 0f, -0.2f, -0.15f)
                .setFadeOut(2000);;
        pollen_button = myDialog.findViewById(R.id.pollen_icon_receipt);
        pollen_button.startAnimation(tap_me_animation);
        pollen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollen_button.clearAnimation();
                pollen_button.setImageResource(R.drawable.half_pollen);
                myParticle.emitWithGravity(pollen_points_desc_tv, Gravity.TOP, 4);
                pollen_button.setClickable(false);
                new CountDownTimer(3000, 100) {

                    public void onTick(long millisUntilFinished) {
                        String cur_score = score_tv.getText().toString();
                        int score = Integer.parseInt(cur_score);
                        Log.e("INITIALSCORE TICK", "" + score);
                        Log.e("POSSIBLESCORE TICK", "" + (int) (points_to_collect * 1 / 30));
                        score += (int) (points_to_collect * 1 / 30);
                        Log.e("TOTAL TICK","" + score);
                        score_tv.setText("" + score);
                    }

                    public void onFinish() {
                        myParticle.stopEmitting();
                        Log.e("Possible POINTS","" + points_to_collect );
                        Log.e("Initial Score POINTS","" + initial_score );
                        int total_score = points_to_collect + initial_score;
                        score_tv.setText("" + total_score);
                    }
                }.start();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }
    */
}
