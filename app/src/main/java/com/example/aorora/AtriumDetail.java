package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
This class is navigated to once the user taps on a butterfly in their atrium.
It takes in the image associated with the atrium entry and displays it in the middle of this page.
It will also take in a description, which is not yet implemented. This description will include
the name of the butterfly and a quick fact about it. This will be expanded once SuperFlies are
implemented as they will have more details, such as the recipe of the five basic butterflies to
make them, along with a more unique image.
 */
public class AtriumDetail extends AppCompatActivity implements View.OnClickListener {
    //Class member declarations
    Context atriumDetail;
    ImageView displayImageView;
    ImageButton back_button;
    TextView descriptionTv;
    int displayImage;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atrium_detail);
        //Init context and onclicklistener
        atriumDetail = this;
        back_button = (ImageButton) findViewById(R.id.back_button_atriumDt);
        back_button.setOnClickListener(this);
        //Grab our views to update.
        //Holds the passed image from the atrium for the current butterfly.
        displayImageView = findViewById(R.id.butterfly_display);
        //Holds a description of the butterfly pressed in the atrium.
        descriptionTv = findViewById(R.id.butterfly_desc);
        //Grab the packaged intent extras
        getData();
        //Update the ImageView in the UI
        setData();
    }
    //Getter for our extras bundled with the navigating Intent. Grabs the atrium image currently.
    private void getData() {
        if(getIntent().hasExtra("image")){
            displayImage = getIntent().getIntExtra("image", 1);
        }
    }
    //Takes the data we gathered from the intent and updates the UI.
    private void setData(){
        displayImageView.setImageResource(displayImage);
    }

    @Override
    public void onClick(View v){
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == back_button.getId())
            {
             to_navigate = new Intent(this, AtriumScreen.class);
             startActivity(to_navigate);
            }

    }
}