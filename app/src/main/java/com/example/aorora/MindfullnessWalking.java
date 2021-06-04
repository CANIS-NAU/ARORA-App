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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aorora.adapter.HorizontalMountainAdapter;
import com.example.aorora.interfaces.OnItemClickListener;

//Represents the MindfullnessWalking Selection Page, continues into MindfullnessWalkingGame if selected.
public class MindfullnessWalking extends AppCompatActivity implements View.OnClickListener {

    Animation infinite_blink;
    ImageButton left_most_button;
    ImageButton left_button;
    ImageButton right_button;
    ImageButton right_most_button;
    ImageButton center;
    ImageButton play;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    Context mindfullnessWalking;
    ImageButton exit_button;
    ImageView alphaChannelImage;
    //Commenting out all instances of recyclerView to remove options for exercise
    //RecyclerView recyclerView;
    HorizontalMountainAdapter horizontalAdapter;
    int game_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_walking);

        mindfullnessWalking = this;

        //recyclerView = findViewById(R.id.horizontal_recycler_view_walking);
        play = (ImageButton) findViewById(R.id.play_button_walking);
        exit_button = (ImageButton) findViewById(R.id.exit_button_walking);
        alphaChannelImage = (ImageView) findViewById(R.id.alpha_channel_walking_icon);

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);

        exit_button.setOnClickListener(this);
        play.setOnClickListener(this);
        generateDataListHorizontal();
        //recyclerView.smoothScrollToPosition(3);

        //infinite_blink = AnimationUtils.loadAnimation(getApplicationContext(),
          //      R.anim.infinite_blink);
        //alphaChannelImage.startAnimation(infinite_blink);
    }

    private void generateDataListHorizontal() {
        horizontalAdapter = new HorizontalMountainAdapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });

        SnapHelper snapHelper = new LinearSnapHelper();
        //snapHelper.attachToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mindfullnessWalking, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(horizontalAdapter);
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                selectMiddleItem();
            }
        });*/
    }
    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        int temp;
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessWalking, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessWalking, MindfullnessSelection.class);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(mindfullnessWalking, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            Toast.makeText(this, "Community page is under maintenance.", Toast.LENGTH_SHORT).show();
            //to_navigate = new Intent(bfDetailsPage, CommunityPage.class);
            //startActivity(to_navigate);
        }
        else if(view_id == exit_button.getId())
        {
            to_navigate = new Intent(mindfullnessWalking, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == play.getId())
        {
            Intent intent = new Intent(MindfullnessWalking.this, MindfullnessWalkingGame.class);
            intent.putExtra("Game Theme", game_theme);
            startActivity(intent);
        }
    }


    /*public void selectMiddleItem() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleIndex = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleIndex = layoutManager.findLastVisibleItemPosition();
        TextView text_view;
        for (int visibleIndex = firstVisibleIndex; visibleIndex < lastVisibleIndex; visibleIndex++) {
            HorizontalMountainAdapter.HorizontalViewHolder viewHolder = (HorizontalMountainAdapter.HorizontalViewHolder) recyclerView.findViewHolderForAdapterPosition(visibleIndex);

            if (viewHolder != null) {
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
                boolean isInMiddle = leftSide < halfScreen && halfScreen < rightSide;
                text_view.setVisibility(View.INVISIBLE);
                viewHolder.itemView.findViewById(R.id.cover_image_feather).setAlpha(0.3f);
                if (isInMiddle) {
                    game_theme = viewHolder.getAdapterPosition();
                    text_view.setTextColor(getResources().getColor(R.color.colorWhite));
                    text_view.setVisibility(View.VISIBLE);
                    viewHolder.itemView.findViewById(R.id.cover_image_feather).setAlpha(1f);
                }
            }
        }
    }*/
}
