package com.example.aorora;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.adapter.HorizontalAdapter;
import com.example.aorora.adapter.HorizontalTimeAdapter;
import com.example.aorora.interfaces.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

public class MindfullnessMeditation extends AppCompatActivity implements View.OnClickListener {

    ImageButton play_button;
    String duration_string;
    Context mindfulnessMeditation;
    ImageView alpha_channel_iv;
    TextView text_view;
    Boolean testMode;
    Animation infinite_blink;
    RecyclerView recyclerView;
    RecyclerView recyclerViewTime;
    int game_theme;
    com.example.aorora.adapter.HorizontalAdapter horizontalAdapter;
    com.example.aorora.adapter.HorizontalTimeAdapter horizontalTimeAdapter;

    ImageView info_floating_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_meditation);

        mindfulnessMeditation = this;
        game_theme = 0;
        play_button = (ImageButton) findViewById(R.id.play_button_walking);
        alpha_channel_iv = (ImageView) findViewById(R.id.alpha_channel_meditation_icon);
        recyclerViewTime = findViewById(R.id.recyclerViewTime);
        recyclerView = findViewById(R.id.horizontal_recycler_view_meditation);
        play_button.setOnClickListener(this);

        info_floating_button = findViewById(R.id.info_floating_button);
        info_floating_button.setOnClickListener(this);


        generateDataListHorizontal();
      
        List<String> data = Arrays.asList("","90 seconds", "");
      
        generateTimeDataList(data);
        infinite_blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.infinite_blink);
        alpha_channel_iv.startAnimation(infinite_blink);
        recyclerView.smoothScrollToPosition(3);
        recyclerViewTime.smoothScrollToPosition(3);

        //DEV MODE FLAG TO END THE ACTIVITY QUICKLY
        testMode = false;

    }


    private void generateDataListHorizontal()
    {
        horizontalAdapter = new com.example.aorora.adapter.HorizontalAdapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {Log.e("ItemClicked", "Item Clicked at Position " + position);
            }
        });

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MindfullnessMeditation.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(horizontalAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                selectMiddleItem();
            }
        });
    }


    private void generateTimeDataList(List<String> data)
    {
        horizontalTimeAdapter = new com.example.aorora.adapter.HorizontalTimeAdapter(MindfullnessMeditation.this, data);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewTime);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MindfullnessMeditation.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTime.setLayoutManager(layoutManager);
        recyclerViewTime.setAdapter(horizontalTimeAdapter);
        recyclerViewTime.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                selectTimeMiddleItem();
            }
        });
    }


    public void selectMiddleItem()
    {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleIndex = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleIndex = layoutManager.findLastVisibleItemPosition();
        TextView text_view;
        for(int visibleIndex = firstVisibleIndex; visibleIndex < lastVisibleIndex; visibleIndex++)
        {
            HorizontalAdapter.HorizontalViewHolder viewHolder = (HorizontalAdapter.HorizontalViewHolder) recyclerView.findViewHolderForAdapterPosition(visibleIndex);

            if(viewHolder != null)
            {
                text_view = viewHolder.itemView.findViewById(R.id.feather_name_tv);

                text_view.setTextColor(getResources().getColor(R.color.customGray));
                int[] location = new int[2];
                viewHolder.itemView.getLocationOnScreen(location);
                int x = location[0];
                double halfWidth = viewHolder.itemView.getWidth() * .5;
                double rightSide = x + halfWidth;
                double leftSide = x - halfWidth;
                //double halfScreen = recyclerView.getWidth() * .5;
                double halfScreen = 400;
                boolean isInMiddle =  leftSide < halfScreen && halfScreen < rightSide;
                text_view.setVisibility(View.INVISIBLE);
                viewHolder.itemView.findViewById(R.id.cover_image_feather).setAlpha(0.3f);
                if(isInMiddle)
                {
                    game_theme = viewHolder.getAdapterPosition();
                    text_view.setTextColor(getResources().getColor(R.color.colorWhite));
                    text_view.setVisibility(View.VISIBLE);
                    viewHolder.itemView.findViewById(R.id.cover_image_feather).setAlpha(1f);
                }
            }
        }
    }
    public void selectTimeMiddleItem()
    {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewTime.getLayoutManager();
        int firstVisibleIndex = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleIndex = layoutManager.findLastVisibleItemPosition();

        for(int visibleIndex = firstVisibleIndex; visibleIndex < lastVisibleIndex; visibleIndex++)
        {
            HorizontalTimeAdapter.HorizontalTimeViewHolder viewHolder = (HorizontalTimeAdapter.HorizontalTimeViewHolder) recyclerViewTime.findViewHolderForAdapterPosition(visibleIndex);

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
                    text_view.setTextColor(getResources().getColor(R.color.colorWhite));
                }
            }

        }
    }

    @Override
    public void onClick(View v)
    {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == play_button.getId())
        {
            boolean two_digit = false;
            int duration_int = 0;
            duration_string = String.valueOf(text_view.getText());
            if(duration_string.equals("90 seconds"))
            {
                //Desired duration to be sent to the game in ms.
                duration_int = 90000;
            }
            else if(duration_string.equals("3 minutes"))
            {
                duration_int = 180000;
            }

            if(testMode){
                duration_int = 10000;
            }

            to_navigate = new Intent(mindfulnessMeditation, MindfulnessMeditationGame_R.class);
            to_navigate.putExtra("Theme",game_theme);
            to_navigate.putExtra("Duration", duration_int);
            startActivity(to_navigate);
        }
        else if(view_id == info_floating_button.getId()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.mindfullness_meditation_icon)
                    .setTitle(R.string.meditation_title_info)
                    .setMessage(R.string.mindfulness_meditation_info)
                    .setPositiveButton("Okay Got It!!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            ImageView imageView = dialog.findViewById(android.R.id.icon);
            if (imageView != null)
//                imageView.setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setColorFilter(Color.BLACK);
        }
    }
}
