package com.example.aorora;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aorora.model.SuperflySession;
import com.example.aorora.model.UserInfo;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitResponseListener;

public class SuperflyFinishPage extends AppCompatActivity implements View.OnClickListener {
    Button homeButton;
    SuperflySession currentSession;
    ImageView superflyDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_finished);

        //Init variables
        homeButton = findViewById(R.id.home_button);
        superflyDisplay = findViewById(R.id.superfly_image);
        currentSession = MainActivity.user_info.getCurrentSession();

        Log.d("FinishPage", "Image for superfly: " + currentSession.getSuperfly_recipe().getSuperfly_photo());

        homeButton.setOnClickListener(this);
        //Get image for superfly
        superflyDisplay.setImageResource(getSuperflyImage());
        homeButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                homeButton.setVisibility(View.VISIBLE);
            }
        }, 2000);


        leaveSession();

    }

    public int getSuperflyImage(){
        Resources resources = this.getResources();
        final String fileName = currentSession.getSuperfly_recipe().getSuperfly_photo();
        final int resourceId = resources.getIdentifier(fileName, "drawable", getPackageName());
        Log.d("Id", "Id: " + resourceId);

        return resourceId;
    }

    public void leaveSession(){
        //Check 1: Make sure the usersuperfly object was successfully posted to the backend.
        NetworkCalls.createUserSuperfly(MainActivity.user_info.getUser_id(), currentSession.getSuperfly_recipe().getSuperfly_id(), this, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                //Check 2: If posting suceeds, check if we need to delete the session.
                //Remove from session
                MainActivity.user_info.setCurrentSession(null);
                NetworkCalls.leaveSession(MainActivity.user_info.getUser_id(), SuperflyFinishPage.this);
            }

            @Override
            public void onFailure() {
                //TODO: Make this a better flow for network failure.
                Toast.makeText(SuperflyFinishPage.this, "Superfly posting failed, please rejoin the session.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View view) {
        int view_id = view.getId();
        if(view_id == homeButton.getId()){
//            Intent to_navigate = new Intent(this, mMainActivity.class);
//            startActivity(to_navigate);
            finish();
        }
    }
}
