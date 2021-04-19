package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollenStoreDailyQuestPage extends AppCompatActivity implements View.OnClickListener{

    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    Context pollenStoreDailyQuestPage;
    RecyclerView pollen_recycler;
    RelativeLayout currentPollenLayout;
    private com.example.aorora.adapter.QuestAdapter questAdapter;
    private com.example.aorora.adapter.PollenShopAdapter shopAdapter;
    int userPollen;
    TextView userPollenTv;
    Button daily_quest_button;
    Button pollen_shop_button;
    ImageView daily_quest_underline;
    ImageView pollen_shop_underline;

    Intent currentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollen_store_daily_quest_page);

        //Fetch user account details
        userPollen = MainActivity.user_info.getUser_pollen();

        pollenStoreDailyQuestPage = this;
        pollen_recycler = (RecyclerView) findViewById(R.id.pollenRecyclerView);

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);

        daily_quest_button = (Button) findViewById(R.id.daily_quests_tabs_button);
        pollen_shop_button = (Button) findViewById(R.id.pollen_shop_tabs_button);
        daily_quest_underline = (ImageView) findViewById(R.id.underline_daily_quests);
        pollen_shop_underline = (ImageView) findViewById(R.id.underline_pollen_shop);
        currentPollenLayout = (RelativeLayout) findViewById(R.id.pollen_count_layout);
        userPollenTv = (TextView) currentPollenLayout.findViewById(R.id.current_pollentv);
        userPollenTv.setText(Integer.toString(userPollen) + " Pollen"  );

        currentIntent = this.getIntent();

        daily_quest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pollen_shop_underline.setVisibility(View.INVISIBLE);
                daily_quest_underline.setVisibility(View.VISIBLE);

                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);
                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {

                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                        generateDataListQuest(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                        Toast.makeText(pollenStoreDailyQuestPage, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        pollen_shop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollen_shop_underline.setVisibility(View.VISIBLE);
                daily_quest_underline.setVisibility(View.INVISIBLE);

                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);
                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {
                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                        generateDataListShop(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                        Toast.makeText(pollenStoreDailyQuestPage, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if(currentIntent.hasExtra("NavigatedFrom"))
        {
            int navigated_from = currentIntent.getIntExtra("NavigatedFrom",1);
            if(navigated_from == 1)
            {
                daily_quest_button.performClick();
            }
            else{
                pollen_shop_button.performClick();
            }
        }
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataListQuest(List<com.example.aorora.model.RetroPhoto> photoList) {
        questAdapter = new com.example.aorora.adapter.QuestAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(pollenStoreDailyQuestPage);
        pollen_recycler.setLayoutManager(layoutManager);
        pollen_recycler.setAdapter(questAdapter);
    }

    private void generateDataListShop(List<com.example.aorora.model.RetroPhoto> photoList) {
        shopAdapter = new com.example.aorora.adapter.PollenShopAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(pollenStoreDailyQuestPage);
        pollen_recycler.setLayoutManager(layoutManager);
        pollen_recycler.setAdapter(shopAdapter);
    }
    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(pollenStoreDailyQuestPage, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(pollenStoreDailyQuestPage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(pollenStoreDailyQuestPage, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            Toast.makeText(PollenStoreDailyQuestPage.this, "Community page is under maintenance.", Toast.LENGTH_SHORT).show();
            //to_navigate = new Intent(pollenStoreDailyQuestPage, CommunityPage.class);
            //startActivity(to_navigate);
        }
    }
}
