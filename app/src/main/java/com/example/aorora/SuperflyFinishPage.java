package com.example.aorora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aorora.model.UserInfo;
import com.example.aorora.network.NetworkCalls;

public class SuperflyFinishPage extends AppCompatActivity implements View.OnClickListener {
    Button homeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superfly_finished);
        homeButton = findViewById(R.id.home_button);

        homeButton.setOnClickListener(this);
        homeButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                homeButton.setVisibility(View.VISIBLE);
            }
        }, 2000);


    }

    @Override
    public void onClick(View view) {
        int view_id = view.getId();
        if(view_id == homeButton.getId()){
            //Remove from session
            MainActivity.user_info.setCurrentSession(null);
            NetworkCalls.leaveSession(MainActivity.user_info.getUser_id(), this);
            Intent to_navigate = new Intent(this, HomeScreen.class);
            startActivity(to_navigate);
        }
    }
}
