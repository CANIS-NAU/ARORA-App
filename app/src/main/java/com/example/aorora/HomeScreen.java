package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.network.NetworkCalls;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class HomeScreen extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnClickListener {
    //User account info
    String userName;
    String userNamePower;
    Integer userPollen;

    //UI and Activity Elements
    Context homeScreen;
    GestureDetector gestureDetector;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton ar_game_button;
    ImageButton quest_button;
    ImageButton pop_up_twobuttons_button;
    TextView notification_tv;
    TextView label_ar_game_button;
    TextView label_quest_button;
    TextView userPollenTv;
    TextView quickAccessUName;
    TextView quickAccessPollen;
    String userPollenDisplay;
    Boolean isButtonsPoppedUp;
    Animation notification_anim;
    Vibrator myVibrate;
    LinearLayout ar_layout;
    public LayoutInflater layoutInflater;
    public View speck1;
    ConstraintLayout speck_holder_cl;
    MediaPlayer ring;
    MediaPlayer spec_alert;
    MediaPlayer buttonClick;
    boolean page_left;
    ImageView profile_butterfly;
    View popup_quick_access;
    public View quick_menu;
    boolean is_menu_inflated;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Testing a refresh of userData here instead of in M1-M3
        //NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), HomeScreen.this);
        setContentView(R.layout.activity_home_screen);
        //First check for local updates
        NetworkCalls.checkLocalUpdates(HomeScreen.this);
        //Fetch userdata from the local user_info instance in MainActivity.
        userPollen = MainActivity.user_info.getUser_pollen();
        Log.d("OnCreate Pollen", "onCreate: Displayed userPollen: " + userPollen);
        userName = MainActivity.user_info.getUser_name();
        userNamePower = MainActivity.user_info.getUser_name_of_strength();

        homeScreen = this;
        isButtonsPoppedUp = false;
        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        home_button_bottombar.setImageResource(R.drawable.home_button_filled);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        ar_layout = (LinearLayout) findViewById(R.id.ar_game_button_ll);
        ar_game_button = (ImageButton) findViewById(R.id.ar_game_button);
        quest_button = (ImageButton) findViewById(R.id.quest_button);
        pop_up_twobuttons_button = findViewById(R.id.pop_up_buttons_button);
        profile_butterfly = (ImageView) findViewById(R.id.user_butterfly_imageView);
        label_ar_game_button = (TextView) findViewById(R.id.label_ar_button);
        label_quest_button = (TextView) findViewById(R.id.label_quest_button);
        popup_quick_access = (LinearLayout) findViewById(R.id.popup_quick_access);

        //User pollen value that will be displayed on the homepage, accessed from included layout.
        userPollenTv = (TextView) popup_quick_access.findViewById(R.id.pollen_score_layout_tv);
        userPollenDisplay = Integer.toString(userPollen);
        userPollenTv.setText(userPollenDisplay);
        //Update the popup menu for pollen to reflect user account values.
        quick_menu = (LinearLayout) findViewById(R.id.include_quick_access_menu);
        quickAccessUName = (TextView) quick_menu.findViewById(R.id.quick_access_user_id_tv);
        quickAccessPollen = (TextView) quick_menu.findViewById(R.id.quickaccesspollen);
        quickAccessUName.setText(userName);
        quickAccessPollen.setText(userPollenDisplay);


        speck_holder_cl = (ConstraintLayout) findViewById(R.id.speck_holder_cl);


        is_menu_inflated = false;

        //buttonClick = MediaPlayer.create(getBaseContext(), R.raw.button1);


        //Log.d("TESTNAV", "Calling onCreate!");

        quick_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this, "Pollen Shop is underdevelopment", Toast.LENGTH_SHORT).show();
                Intent to_navigate = new Intent(homeScreen, PollenStoreDailyQuestPage.class);
                to_navigate.putExtra("NavigatedFrom", 1);
                startActivity(to_navigate);
            }
        });
        popup_quick_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                try {
                    if (buttonClick.isPlaying()) {
                        buttonClick.stop();
                        buttonClick.release();
                      buttonClick = MediaPlayer.create(getBaseContext(), R.raw.button1);
                    } buttonClick.start();
                } catch(Exception e) { e.printStackTrace(); }

                buttonClick = MediaPlayer.create(getBaseContext(), R.raw.button1);
                */

                ImageView popup_quick_acces_image = (ImageView) popup_quick_access.findViewById(R.id.pollen_score_layout_imageview);
                if(is_menu_inflated)
                {
                    popup_quick_access.findViewById(R.id.pollen_score_layout_tv).setVisibility(View.VISIBLE);

                    popup_quick_acces_image.setImageResource(R.drawable.half_pollen);
                    quick_menu.setVisibility(View.INVISIBLE);
                    is_menu_inflated = false;

                }
                else
                {
                    popup_quick_access.findViewById(R.id.pollen_score_layout_tv).setVisibility(View.INVISIBLE);
                    popup_quick_acces_image.setImageResource(R.drawable.pollen);
                    quick_menu.setVisibility(View.VISIBLE);
                    /*
                    View block1 = quick_menu.findViewById(R.id.quick_access_block_1);
                    TextView tv1 = block1.findViewById(R.id.text_view_description_quest_block);
                    tv1.setText("Mindfulness Breathing");
                    block1
                    View block2 = quick_menu.findViewById(R.id.quick_access_block_1);
                    View block3 = quick_menu.findViewById(R.id.quick_access_block_1);
                    */
                    is_menu_inflated = true;
                }
                /*
                ConstraintSet constraints = new ConstraintSet();
                LayoutInflater inflater =  LayoutInflater.from(homeScreen);
                quick_menu = inflater.inflate(R.layout.quick_access_menu, null);
                constraints.clone(speck_holder_cl);
                constraints.connect(quick_menu.getId(), ConstraintSet.LEFT, speck_holder_cl.getId(), ConstraintSet.LEFT, 600);
                constraints.connect(quick_menu.getId(), ConstraintSet.BOTTOM, speck_holder_cl.getId(), ConstraintSet.BOTTOM, 1000);
                constraints.applyTo(speck_holder_cl);
                */

            }
        });
        //This does not invoke the network calls, it simply accesses the user_info object values.
        switch (MainActivity.user_info.getUser_current_butterfly()){
            case 0:
                profile_butterfly.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                profile_butterfly.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                profile_butterfly.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                profile_butterfly.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                profile_butterfly.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                profile_butterfly.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                profile_butterfly.setImageResource(R.drawable.orange_butterfly_image);
                break;
        }
        gestureDetector = new GestureDetector(homeScreen, HomeScreen.this);
        notification_tv = (TextView) findViewById(R.id.notification_body_homescreen);
        myVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        layoutInflater = LayoutInflater.from(homeScreen);
        speck1 = layoutInflater.inflate(R.layout.speck_notification, null);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        quest_button.setOnClickListener(this);
        ar_game_button.setOnClickListener(this);
        notification_tv.setOnClickListener(this);
        speck1.setOnClickListener(this);
       // notification_tv.setVisibility(View.VISIBLE);
        ring = MediaPlayer.create(homeScreen,R.raw.notify_2);
        spec_alert = MediaPlayer.create(homeScreen,R.raw.notify_wav);

        //to stop music
        page_left = false;

        // Constraints to inflate random specks on layout

        /*
        new CountDownTimer(7000, 100) {
            ConstraintSet constraints = new ConstraintSet();
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                speck_holder_cl.addView(speck1, 0);
                final int random = new Random().nextInt(701) + 100;
                constraints.clone(speck_holder_cl);
                constraints.connect(speck1.getId(), ConstraintSet.LEFT, speck_holder_cl.getId(), ConstraintSet.LEFT, random);
                constraints.connect(speck1.getId(), ConstraintSet.BOTTOM, speck_holder_cl.getId(), ConstraintSet.BOTTOM, 1000);
                constraints.applyTo(speck_holder_cl);
                if(!page_left) {
                    spec_alert.start();
                }
            }
        }.start();
*/
        notification_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_reverse);

        notification_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notification_tv.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //Central white button to show and hide AR and Quest buttons.
        pop_up_twobuttons_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                if(!isButtonsPoppedUp) {
                    pop_up_twobuttons_button.setImageDrawable(getResources().getDrawable(R.drawable.menu_button_unfilled));
                    ar_layout.setVisibility(View.VISIBLE);
                    quest_button.setVisibility(View.VISIBLE);
                    label_quest_button.setVisibility(View.VISIBLE);
                    isButtonsPoppedUp = true;
                    ar_game_button.setClickable(TRUE);
                    quest_button.setClickable(TRUE);
                }
                else{
                    pop_up_twobuttons_button.setImageDrawable(getResources().getDrawable(R.drawable.menu_button_filled));
                    ar_layout.setVisibility(View.INVISIBLE);
                    quest_button.setVisibility(View.INVISIBLE);
                    label_quest_button.setVisibility(View.INVISIBLE);
                    isButtonsPoppedUp = false;
                    ar_game_button.setClickable(FALSE);
                    quest_button.setClickable(FALSE);
                }
            }
        });

        /*
        new CountDownTimer(10000, 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                notification_tv.startAnimation(notification_anim);

                if(!page_left) {
                    ring.start();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEfect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        myVibrate.vibrate(500);
                    }
                }

            }
        }.start();
        */
    }

    @Override
    public void onBackPressed() {
        //Do nothing on backpress, override the default behavior.
    }

    //Built in overriden onFLing method for swiping control between Activities
    //Implements from the GestureDetector interface.
    @Override
    public boolean onFling (MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y)
    {
        page_left = true;
        //This checks if we swiped left on the homepage
        if (motionEvent1.getX() - motionEvent2.getX() > 150) {
            Intent profilePage = new Intent(homeScreen, MindfullnessSelection.class);
            startActivity(profilePage);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            return true;
        }
        //This checks if we swiped right on the homepage
        if (motionEvent2.getX() - motionEvent1.getX() > 150) {
            Intent mindfullness = new Intent(homeScreen, ProfilePage.class);
            startActivity(mindfullness);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            return true;
        } else {
            return true;
        }
    }
    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(ring.isPlaying()) {
            ring.stop();
        }
        if(spec_alert.isPlaying())
        {
            spec_alert.stop();
        }
        page_left = true;

        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(homeScreen, ProfilePage.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            Toast.makeText(this, "Community page is under maintenance.", Toast.LENGTH_SHORT).show();
            //to_navigate = new Intent(bfDetailsPage, CommunityPage.class);
            //startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId() || view_id == quest_button.getId())
        {
            to_navigate = new Intent(homeScreen, MindfullnessSelection.class);
            startActivity(to_navigate);

        }
        else if(view_id == home_button_bottombar.getId())
        {
            //to_navigate = new Intent(homeScreen, homeScreen);
        }
        else if (view_id == speck1.getId())
        {
            to_navigate = new Intent(homeScreen, CommunityPage.class);
            to_navigate.putExtra("notification", true);
            startActivity(to_navigate);
        }
        else if(view_id == notification_tv.getId())
        {
            to_navigate = new Intent(homeScreen, MindfullnessBreathing.class);
            startActivity(to_navigate);
        }
        else if(view_id == ar_game_button.getId())
        {
            to_navigate = new Intent(homeScreen, ARScreen.class);
            startActivity(to_navigate);
            Log.d("ARBUTTON", "Navigating to AR.");
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.NAUVRLab.ARProduct");
            //startActivity(launchIntent);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    // We don't need to implement those unless otherwise told. They just need to be there
    // because we are implementing the GestureDetector class
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
    protected void onResume() {
        super.onResume();
        //TODO: Push local updates to the backend, keep trying. Userinfo call should do it.
        //NetworkCalls.checkLocalUpdates(HomeScreen.this);
    }
}
