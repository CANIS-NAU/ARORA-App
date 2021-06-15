package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aorora.adapter.AtriumAdapter;
import com.example.aorora.network.NetworkCalls;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
This screen contains the atrium inventory logic and code to display butterfly counts from the
backend. Each butterfly presented is currently one of five basic butterflies for use in the MVP
of ARORA. These butterflies will be collected and stored here in the Atrium as a core component of
M4.
 */
public class AtriumScreen extends AppCompatActivity implements View.OnClickListener{
    //User account info
    Integer userPollen;
    Integer userId;

    //RecyclerView Variables
    private RecyclerView atriumRecycler;
    AtriumAdapter atriumAdapter;
    RecyclerView.LayoutManager layoutManager;

    Context atriumScreen;
    Button add_butterflies;

    //Images for the butterflies
    int images[] = {R.drawable.red_1, R.drawable.yellow_1,
            R.drawable.violet_1, R.drawable.green_1,
            R.drawable.blue_2};

    //Counts of each type of butterfly.
    int counts[];

    //Grab the user's local atrium map to be updated and pushed back to the UserInfo Model.
    Map<String, Integer> local_atrium = new HashMap<>();

    //Stores the total amount of unique basic butterflies currently registered for the user.
    Integer butterflyTypeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atrium);
        //Grab the userId
        userId = MainActivity.user_info.getUser_id();

        //Get the built atrium and set it to something easier to access here.
        local_atrium = MainActivity.user_info.get_local_atrium();
        //Grab the unique butterflies in the user atrium
        butterflyTypeCount = local_atrium.size();
        //Initialize the array to be passed to the recyclerview.
        counts = new int[butterflyTypeCount];

        //Grab the recyclerview
        atriumRecycler = findViewById(R.id.atriumRecycler);
        atriumAdapter = new AtriumAdapter(this, images, counts);
        atriumRecycler.setAdapter(atriumAdapter);
        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        atriumRecycler.setLayoutManager(layoutManager);
        atriumRecycler.setHasFixedSize(true);

        atriumScreen = this;
        add_butterflies = (Button) findViewById(R.id.add_button);

        //Onclicklisteners for this class.
        add_butterflies.setOnClickListener(this);

        //Initialize recyclerview ArrayList with the map values from the user's atrium
        for(Integer i = 0; i < butterflyTypeCount; i++){
            String current_butterfly = "user_b" + i.toString() +"_count";
            counts[i] = local_atrium.get(current_butterfly);
            System.out.println("Adding butterfly for: " + current_butterfly);
        }

    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        if(view_id == add_butterflies.getId()){
            for(Integer i = 0; i < counts.length - 1; i++){
                String currString = "user_b" + i.toString() + "_count";
                counts[i] = counts[i] + 1;
                local_atrium.put(currString, counts[i]);
            }
            System.out.println("Local Atrium: " + Arrays.asList(local_atrium));
            Toast.makeText(atriumScreen, "Adding to each butterfly count", Toast.LENGTH_SHORT).show();
            atriumAdapter.notifyDataSetChanged();
            //First update ourselves locally
            MainActivity.user_info.update_local_atrium(local_atrium);
            //Once we get the updated local atrium, push the new atrium map to the backend.
            NetworkCalls.updateUserAtrium(userId, local_atrium, atriumScreen);
        }
    }
}
