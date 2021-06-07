package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.adapter.HorizontalTimeAdapter;

import java.util.Arrays;
import java.util.List;

public class MindfullnessBreathing extends AppCompatActivity implements View.OnClickListener {

    ImageButton play_button;
    int timerCount;
    Context mindfullnessBreathing;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageView alpha_channel_iv;
    Animation infinite_blink;
    ImageButton exit_button;
    RecyclerView time_selection_recyler_view;
    com.example.aorora.adapter.HorizontalTimeAdapter horizontalTimeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_breathing);
        mindfullnessBreathing = this;

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        play_button = (ImageButton) findViewById(R.id.play_button_walking);
        alpha_channel_iv = (ImageView) findViewById(R.id.alpha_channel_breathing_icon);
        exit_button = (ImageButton) findViewById(R.id.exit_button_walking);
        time_selection_recyler_view = (RecyclerView) findViewById(R.id.recycler_view_time_selection_breathing);

        //Removed "5 Breaths" and "15 Breaths" strings from array list for testing purposes

        List<String> data = Arrays.asList("", "5 Breaths" ,"");
        generateTimeDataList(data);
        time_selection_recyler_view.smoothScrollToPosition(3);
        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        play_button.setOnClickListener(this);
        exit_button.setOnClickListener(this);

        infinite_blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.infinite_blink);
        alpha_channel_iv.startAnimation(infinite_blink);

    }
    private void generateTimeDataList(List<String> data)
    {
        horizontalTimeAdapter = new com.example.aorora.adapter.HorizontalTimeAdapter(MindfullnessBreathing.this, data);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(time_selection_recyler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MindfullnessBreathing.this, LinearLayoutManager.HORIZONTAL, false);
        time_selection_recyler_view.setLayoutManager(layoutManager);
        time_selection_recyler_view.setAdapter(horizontalTimeAdapter);
        time_selection_recyler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                selectTimeMiddleItem();
            }
        });
    }
    public void selectTimeMiddleItem()
    {
        LinearLayoutManager layoutManager = (LinearLayoutManager) time_selection_recyler_view.getLayoutManager();
        int firstVisibleIndex = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleIndex = layoutManager.findLastVisibleItemPosition();
        TextView text_view;
        for(int visibleIndex = firstVisibleIndex; visibleIndex < lastVisibleIndex; visibleIndex++)
        {
            HorizontalTimeAdapter.HorizontalTimeViewHolder viewHolder = (HorizontalTimeAdapter.HorizontalTimeViewHolder) time_selection_recyler_view.findViewHolderForAdapterPosition(visibleIndex);

            if(viewHolder != null)
            {
                text_view = viewHolder.itemView.findViewById(R.id.minute_horizontal_tv);
                text_view.setTextColor(getResources().getColor(R.color.customGray));
                int[] location = new int[2];
                viewHolder.itemView.getLocationOnScreen(location);
                int x = location[0];
                double halfWidth = viewHolder.itemView.getWidth() * .5;
                double rightSide = x + halfWidth;
                double leftSide = x - halfWidth;
                double halfScreen = 400;
                boolean isInMiddle =  leftSide < halfScreen && halfScreen < rightSide;

                if(isInMiddle)
                {
                    timerCount = viewHolder.getAdapterPosition();
                    text_view.setTextColor(getResources().getColor(R.color.colorWhite));
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessBreathing, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessBreathing, MindfullnessSelection.class);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessBreathing, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            Toast.makeText(this, "Community page is under maintenance.", Toast.LENGTH_SHORT).show();
            //to_navigate = new Intent(bfDetailsPage, CommunityPage.class);
            //startActivity(to_navigate);
        }
        else if(view_id == play_button.getId())
        {
            to_navigate = new Intent(mindfullnessBreathing, MindfullnessBreathingGame.class);
            to_navigate.putExtra("TimerValue", timerCount);
            //to_navigate.putExtra("NavigatedFrom", -1);
            startActivity(to_navigate);
        }
        else if(view_id == exit_button.getId())
        {
            finish();
        }
    }
}
