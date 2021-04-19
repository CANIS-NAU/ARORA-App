package com.example.aorora;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.aorora.network.NetworkCalls;

public class slider_survey_page extends AppCompatActivity {

    TextView textview_question_1, textview_question_1_value_seekbar;
    SeekBar seekbar_butterfly_1;
    ImageView imageview_butterfly_question_1, imageview_butterfly_question_1_black;

    TextView textview_question_2, textview_question_2_value_seekbar;
    SeekBar seekbar_butterfly_2;
    ImageView imageview_butterfly_question_2, imageview_butterfly_question_2_black;

    Button skip, submit;

//    String[] moodsArrayPleasent_Unpleasent = new String[]{ "Very Unpleasent", "Unpleasent", "Neutral", "Pleasent", "Very Pleasent" };
//    String[] moodsArrayHappy_sad = new String[]{ "Very Sad", "sad", "okay", "happy", "Very happy" };

//    String[] moodsArrayPleasent_Unpleasent = getResources().getStringArray(R.array.mood_array_pleasent_unpleasent);
//    String[] moodsArrayHappy_sad = getResources().getStringArray(R.array.mood_array_pleasent_unpleasent);
    int q1_response = 3, q2_response = 3 ;
    int initial_location = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_survey_page);
        skip = findViewById(R.id.skip_survey);
        submit = findViewById(R.id.submit_survey);

        initialize_butterfly_1();
        initialize_butterfly_2();

        seekbar_butterfly_1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textview_question_1_value_seekbar.setText(moodsArrayPleasent_Unpleasent[progress-1]);
                textview_question_1_value_seekbar.setText(getResources().getStringArray(R.array.mood_array_pleasent_unpleasent)[progress-1]);
                imageview_butterfly_question_1.setImageAlpha(getImageAlpha(progress));
                imageview_butterfly_question_1_black.setImageAlpha(getImageAlpha(5-progress));
                q1_response = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar_butterfly_2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textview_question_2_value_seekbar.setText(moodsArrayHappy_sad[progress-1]);
                textview_question_2_value_seekbar.setText(getResources().getStringArray(R.array.mood_array_calm_tense)[progress-1]);
                imageview_butterfly_question_2.setImageAlpha(getImageAlpha(progress));
                imageview_butterfly_question_2_black.setImageAlpha(getImageAlpha(5-progress));
                q2_response = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeAcivity = new Intent(slider_survey_page.this, HomeScreen.class);
                startActivity(homeAcivity);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("RESPONSE INTEGER 1", String.valueOf(q1_response));
                Log.v("RESPONSE INTEGER 2", String.valueOf(q2_response));
                NetworkCalls.createMoodReport(MainActivity.user_info.getUser_id(), q1_response, q2_response, slider_survey_page.this);
                Intent homeAcivity = new Intent(slider_survey_page.this, HomeScreen.class);
                startActivity(homeAcivity);
            }
        });
    }

    private void initialize_butterfly_1() {
        textview_question_1 = findViewById(R.id.textview_question_1);
        textview_question_1_value_seekbar = findViewById(R.id.textview_question_1_value_seekbar);

        imageview_butterfly_question_1 = findViewById(R.id.imageview_butterfly_question_1);
        imageview_butterfly_question_1_black = findViewById(R.id.imageview_butterfly_question_1_black);

        seekbar_butterfly_1 = findViewById(R.id.seekbar_question_1);

        textview_question_1.setText(R.string.survey_question_1);
        textview_question_1_value_seekbar.setText(getResources().getStringArray(R.array.mood_array_pleasent_unpleasent)[initial_location-1]);

        seekbar_butterfly_1.setProgress(initial_location);

        imageview_butterfly_question_1.setImageAlpha(getImageAlpha(initial_location));
        imageview_butterfly_question_1_black.setImageAlpha(getImageAlpha(initial_location));
    }

    private void initialize_butterfly_2() {
        textview_question_2 = (TextView) findViewById(R.id.textview_question_2);
        textview_question_2_value_seekbar = (TextView) findViewById(R.id.textview_question_2_value_seekbar);

        imageview_butterfly_question_2 = (ImageView) findViewById(R.id.imageview_butterfly_question_2) ;
        imageview_butterfly_question_2_black = (ImageView) findViewById(R.id.imageview_butterfly_question_2_black);

        seekbar_butterfly_2 = (SeekBar) findViewById(R.id.seekbar_question_2);

        textview_question_2.setText(R.string.survey_question_2);
        textview_question_2_value_seekbar.setText(getResources().getStringArray(R.array.mood_array_calm_tense)[initial_location-1]);

        seekbar_butterfly_2.setProgress(initial_location);

        imageview_butterfly_question_2.setImageAlpha(getImageAlpha(initial_location));
        imageview_butterfly_question_2_black.setImageAlpha(getImageAlpha(initial_location));
    }

    private int getImageAlpha(int i) {
        return ((i*2*10)*(255))/100;
    }
}