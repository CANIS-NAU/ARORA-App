package com.example.aorora;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MindfullnessFeatherSelection extends AppCompatActivity implements View.OnClickListener {

    ImageButton green_feather;
    ImageButton purple_feather;
    ImageButton whiteish_feather;
    ImageButton blue_feather;
    ImageButton start_meditation;
    ImageButton exit_button;

    TextView green_desc_tv;
    TextView purple_desc_tv;
    TextView whiteish_desc_tv;
    TextView blue_desc_tv;

    Context featherSelection;

    int feather_selected;
    int duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindfullness_feather_selection);

        green_feather = (ImageButton) findViewById(R.id.green_feather_button);
        purple_feather = (ImageButton) findViewById(R.id.purple_feather_button);
        whiteish_feather = (ImageButton) findViewById(R.id.whiteish_feather_button);
        blue_feather = (ImageButton) findViewById(R.id.blue_feather_button);
        start_meditation = (ImageButton) findViewById(R.id.select_feather_button);
        exit_button = (ImageButton) findViewById(R.id.exit_button_feather_selection);

        green_desc_tv = (TextView) findViewById(R.id.green_feather_desc_tv);
        purple_desc_tv = (TextView) findViewById(R.id.purple_feather_desc_tv);
        whiteish_desc_tv = (TextView) findViewById(R.id.whiteish_feather_desc_tv);
        blue_desc_tv = (TextView) findViewById(R.id.blue_feather_desc_tv);

        featherSelection = this;

        //Default Values
        feather_selected = 1;
        duration = 1;

        if(getIntent().hasExtra("Duration"))
        {
            duration = getIntent().getIntExtra("Duration", 1);
        }

        green_feather.setOnClickListener(this);
        purple_feather.setOnClickListener(this);
        whiteish_feather.setOnClickListener(this);
        blue_feather.setOnClickListener(this);
        start_meditation.setOnClickListener(this);
        exit_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        if(view_id == green_feather.getId())
        {
            blue_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            whiteish_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            purple_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            green_desc_tv.setTextColor(getResources().getColor(R.color.colorWhite));

            feather_selected = 1;
        }
        else if(view_id == purple_feather.getId())
        {
            blue_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            whiteish_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            purple_desc_tv.setTextColor(getResources().getColor(R.color.colorWhite));
            green_desc_tv.setTextColor(getResources().getColor(R.color.customGray));

            feather_selected = 2;
        }
        else if(view_id == whiteish_feather.getId())
        {
            blue_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            whiteish_desc_tv.setTextColor(getResources().getColor(R.color.colorWhite));
            purple_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            green_desc_tv.setTextColor(getResources().getColor(R.color.customGray));

            feather_selected = 3;
        }
        else if(view_id ==  blue_feather.getId())
        {
            blue_desc_tv.setTextColor(getResources().getColor(R.color.colorWhite));
            whiteish_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            purple_desc_tv.setTextColor(getResources().getColor(R.color.customGray));
            green_desc_tv.setTextColor(getResources().getColor(R.color.customGray));

            feather_selected = 4;
        }
        else if(view_id == start_meditation.getId())
        {
            Intent to_navigate = new Intent(featherSelection, MindfulnessMeditationGame_R.class);
            to_navigate.putExtra("Duration", duration);
            to_navigate.putExtra("Feather", feather_selected);
            startActivity(to_navigate);
        }
        else if(view_id == exit_button.getId())
        {
            finish();
        }
    }
}
