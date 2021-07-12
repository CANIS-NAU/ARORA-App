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
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aorora.adapter.HorizontalMountainAdapter;
import com.example.aorora.interfaces.OnItemClickListener;

//Represents the MindfullnessWalking Selection Page, continues into MindfullnessWalkingGame if selected.
public class MindfullnessWalking extends AppCompatActivity implements View.OnClickListener {

    ImageButton play;
    Context mindfullnessWalking;
    ImageButton exit_button;
    ImageView alphaChannelImage;
    int game_theme;
    ImageView info_floating_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_walking);

        mindfullnessWalking = this;

        play = (ImageButton) findViewById(R.id.play_button_walking);
        exit_button = (ImageButton) findViewById(R.id.exit_button_walking);
        alphaChannelImage = (ImageView) findViewById(R.id.alpha_channel_walking_icon);

        exit_button.setOnClickListener(this);
        play.setOnClickListener(this);

        info_floating_button = findViewById(R.id.info_floating_button);
        info_floating_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        if(view_id == play.getId())
        {
            Intent intent = new Intent(MindfullnessWalking.this, MindfullnessWalkingGame.class);
            intent.putExtra("Game Theme", game_theme);
            startActivity(intent);
        }
        else if(view_id == info_floating_button.getId()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_learn)
                    .setTitle(R.string.alert_title_info)
                    .setMessage(R.string.mindfullness_walking_info)
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
