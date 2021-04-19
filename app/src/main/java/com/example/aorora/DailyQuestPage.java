package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aorora.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyQuestPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    RecyclerView recyclerView;
    private com.example.aorora.adapter.QuestAdapter questAdapter;
    Context dailyQuestPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quest_page);

        dailyQuestPage = this;
        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);

        com.example.aorora.network.GetDataService service =
                com.example.aorora.network.RetrofitClientInstance
                        .getRetrofitInstance()
                        .create(com.example.aorora.network.GetDataService.class);

        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {

            @Override
            public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                generateDataListQuest(response.body());
            }

            @Override
            public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                Toast.makeText(dailyQuestPage, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataListQuest(List<com.example.aorora.model.RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.recylerView_daily_quest);
        questAdapter = new com.example.aorora.adapter.QuestAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dailyQuestPage);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(questAdapter);
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(dailyQuestPage, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(dailyQuestPage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(dailyQuestPage, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            to_navigate = new Intent(dailyQuestPage, CommunityPage.class);
            startActivity(to_navigate);
        }
    }
}
