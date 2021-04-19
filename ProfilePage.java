package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.model.Butterfly;
import com.example.aorora.model.DailyTask;
import com.example.aorora.model.UserAuth;
import com.example.aorora.model.UserInteraction;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.RetrofitClientInstance;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {

    GestureDetector gestureDetector;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton butterfly_selection_button;
    ImageButton jar_button;
    ImageButton settings_button;
    ImageButton pollen_button;
    TextView user_name_tv;
    TextView user_score_tv;
    TextView butterfly_name_tv;
    TextView butterfly_description_tv;
    Context profilePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        profile_button_bottombar.setImageResource(R.drawable.profile_filled_button);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        butterfly_selection_button = (ImageButton) findViewById(R.id.profile_butterfly_button);
        jar_button = (ImageButton) findViewById(R.id.jar_button_profile_page);
        pollen_button = (ImageButton) findViewById(R.id.pollen_button_profile_page);
        settings_button = (ImageButton) findViewById(R.id.settings_button_profile_page);
        user_name_tv = (TextView) findViewById(R.id.profile_user_name_tv);
        user_score_tv = (TextView) findViewById(R.id.profile_user_score);
        butterfly_name_tv = (TextView) findViewById(R.id.profile_page_bf_name_tv);
        butterfly_description_tv = (TextView) findViewById(R.id.profile_page_bf_desc_tv);
        profilePage = this;

        user_score_tv.setText("" + MainActivity.user_info.getUser_pollen());
        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        jar_button.setOnClickListener(this);
        butterfly_selection_button.setOnClickListener(this);
        settings_button.setOnClickListener(this);
        pollen_button.setOnClickListener(this);
        gestureDetector = new GestureDetector(profilePage, ProfilePage.this);

        switch ( MainActivity.user_info.getUser_current_butterfly()){
            case 0:
                butterfly_selection_button.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                butterfly_selection_button.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                butterfly_selection_button.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                butterfly_selection_button.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                butterfly_selection_button.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                butterfly_selection_button.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                butterfly_selection_button.setImageResource(R.drawable.orange_butterfly_image);
                break;
        }

    }


    @Override
    public void onClick(View v) {
        int view_id =  v.getId();
        Intent to_navigate;
        if(view_id == butterfly_selection_button.getId() || view_id == jar_button.getId())
        {
            to_navigate = new Intent(profilePage, ButterflyCollectionPage.class);
            startActivity(to_navigate);

        }
        else if(view_id == community_button_bottombar.getId())
        {
            to_navigate = new Intent(profilePage, CommunityPage.class);
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(profilePage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(profilePage, HomeScreen.class);
            startActivity(to_navigate);

        }
        else if(view_id == settings_button.getId())
        {
            //to_navigate = new Intent(profilePage, MindfulnessMeditationGame_R.class);
            //startActivity(to_navigate);
            //sendOutLike(1,2);
            //getButterfly();
            //to_navigate = new Intent(profilePage, EndOfMindfulnessGamePage.class);
            //startActivity(to_navigate);
        }
        else if(view_id == pollen_button.getId())
        {
            to_navigate = new Intent(profilePage, PollenStoreDailyQuestPage.class);
            to_navigate.putExtra("NavigatedFrom", 2);
            startActivity(to_navigate);
        }
    }

    @Override
    public boolean onFling (MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y)
    {
        Intent to_navigate;
        if (motionEvent2.getX() - motionEvent1.getX() > 150) {
            to_navigate = new Intent(profilePage, CommunityPage.class);
            startActivity(to_navigate);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            return true;
        }
        else if (motionEvent1.getX() - motionEvent2.getX() > 150)
        {
            to_navigate = new Intent(profilePage, HomeScreen.class);
            startActivity(to_navigate);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            return true;
        }
        else {
            return true;
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

    public void sendOutLike(int user_id_1, int user_id_2)
    {
        int user_sender_id = 2;
        int user_receiver_id = 3;

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<UserInteraction> call = service.userInteract(user_sender_id,user_receiver_id, 1, "LIKE 2 to 3");
        call.enqueue(new Callback<UserInteraction>() {

            @Override
            public void onResponse(Call<UserInteraction>call, Response<UserInteraction> response) {

                if(response.isSuccess())
                {
                    UserInteraction user_interaction = response.body();
                    Toast.makeText(ProfilePage.this, "USER INTERACTION ID: " + user_interaction.getUser_interaction_id(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ProfilePage.this, "NOT SUCCESFULL" , Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(ProfilePage.this, "FAILED " + response.code(), Toast.LENGTH_SHORT).show();
                //surveyPage = new Intent(context, SurveyPage.class);
                //startActivity(surveyPage);
            }

            @Override
            public void onFailure(Call<UserInteraction> call, Throwable t) {
                Toast.makeText(ProfilePage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getButterfly()
    {
        com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

        Call<List<Butterfly>> call = service.getButterflyInfo();
        call.enqueue(new Callback<List<Butterfly>>() {

            @Override
            public void onResponse(Call<List<com.example.aorora.model.Butterfly>> call, Response<List<Butterfly>> response) {
                Toast.makeText(ProfilePage.this, "Butterfly ID: " + response.body().get(0).getButterflyId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<com.example.aorora.model.Butterfly>> call, Throwable t) {
                Toast.makeText(ProfilePage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void createUserButterfly()
    {

    }
}
