package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aorora.network.NetworkCalls;

import static com.example.aorora.MainActivity.user_info;

public class ButterflyDetailsPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton selectButterfly_ib;
    Context bfDetailsPage;
    Button selectButton;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterfly_details_page);

        bfDetailsPage = this;
        selectButton = (Button) findViewById(R.id.select_button_bf_details_page);
        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        selectButterfly_ib = (ImageButton) findViewById(R.id.butterfly_details_page_bf);
        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        selectButton.setOnClickListener(this);
        selectButterfly_ib.setOnClickListener(this);
        Intent intent = getIntent();
        if(intent.hasExtra("ButterflyId"))
        {
            position = intent.getIntExtra("ButterflyId",0);
        }

        switch (position){
            case 0:
                selectButterfly_ib.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                selectButterfly_ib.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                selectButterfly_ib.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                selectButterfly_ib.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                selectButterfly_ib.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                selectButterfly_ib.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                selectButterfly_ib.setImageResource(R.drawable.orange_butterfly_image);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(bfDetailsPage, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(bfDetailsPage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(bfDetailsPage, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            Toast.makeText(ButterflyDetailsPage.this, "Community page is under maintenance.", Toast.LENGTH_SHORT).show();
            //to_navigate = new Intent(bfDetailsPage, CommunityPage.class);
            //startActivity(to_navigate);
        }
        else if(view_id == selectButton.getId() || view_id == selectButterfly_ib.getId())
        {
            int user_butterfly;
            switch (position){
                case 0:
                    user_butterfly = 0;
                    break;
                case 1:
                    user_butterfly = 1;
                    break;
                case 2:
                    user_butterfly = 2;
                    break;
                case 3:
                    user_butterfly = 3;
                    break;
                case 4:
                    user_butterfly = 4;
                    break;
                case 5:
                    user_butterfly = 5;
                    break;
                default:
                    user_butterfly = 0;
                    break;
            }

            NetworkCalls.updateUserCurrentButterfly(user_info.getUser_id(), user_butterfly, this);
            to_navigate = new Intent(bfDetailsPage, ProfilePage.class);
            startActivity(to_navigate);
        }
    }
}
