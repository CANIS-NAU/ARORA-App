package com.example.aorora.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aorora.ARScreen;
import com.example.aorora.MindfullnessBreathing;
import com.example.aorora.MindfullnessMeditation;
import com.example.aorora.MindfullnessWalking;
import com.example.aorora.R;

public class LearnFragment extends Fragment {

    CardView mindfulness_breathing, mindfulness_meditation, mindfulness_walking;
    ImageView info_floating_button;

    public LearnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        mindfulness_breathing = (CardView) rootView.findViewById(R.id.mindfulness_breathing);
        mindfulness_meditation = (CardView) rootView.findViewById(R.id.mindfulness_meditation);
        mindfulness_walking = (CardView) rootView.findViewById(R.id.mindfulness_walking);

        info_floating_button = rootView.findViewById(R.id.info_floating_button);
        info_floating_button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setIcon(R.drawable.ic_learn_new)
                    .setTitle(R.string.learning_title_info)
                    .setMessage(R.string.learning_info)
                    .setPositiveButton("Okay Got It!!!", null)
                    .setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            ImageView imageView = dialog.findViewById(android.R.id.icon);
            if (imageView != null)
//                imageView.setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setColorFilter(Color.BLACK);
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        mindfulness_breathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNavigate = new Intent(getContext(), MindfullnessBreathing.class);
                startActivity(toNavigate);
            }
        });

        mindfulness_breathing.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Relieve your stress, Breath In - Breath Out", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mindfulness_meditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNavigate = new Intent(getContext(), MindfullnessMeditation.class);
                startActivity(toNavigate);
            }
        });

        mindfulness_meditation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Practice Meditation", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mindfulness_walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNavigate = new Intent(getContext(), MindfullnessWalking.class);
                startActivity(toNavigate);
            }
        });

        mindfulness_walking.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Exercise Walking", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}