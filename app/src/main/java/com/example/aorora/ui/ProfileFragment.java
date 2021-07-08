package com.example.aorora.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aorora.AtriumScreen;
import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.network.NetworkCalls;
import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {

    TextView userPollenTv, userAtriumCountTv, userNameTextView;
    Integer userPollenCount, userButterflyCount;
    LayoutInflater inflater;

    LinearLayout pollen_count, atrium_count, userNameLayout;

    CardView pollen_score_card_view, atrium_count_card_view;
    ImageView userButterflyImgView;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Called when a fragment is attached as a child of this fragment.
     *
     * <p>This is called after the attached fragment's <code>onAttach</code> and before
     * the attached fragment's <code>onCreate</code> if the fragment has not yet had a previous
     * call to <code>onCreate</code>.</p>
     *
     * @param childFragment child fragment being attached
     */
    @Override
    public void onAttachFragment(@NonNull @NotNull Fragment childFragment) {
        NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), getContext());
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        NetworkCalls.getUserInfo(getUserId(), getContext());
//        android.widget.Toast.makeText(getContext(), "1. OnCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userPollenTv = (TextView) rootView.findViewById(R.id.pollen_score_layout_tv_2);
        userAtriumCountTv = (TextView) rootView.findViewById(R.id.atirum_count);
        pollen_count = (LinearLayout) rootView.findViewById(R.id.pollen_view_count_LL);
        atrium_count = (LinearLayout) rootView.findViewById(R.id.atrium_butterfly_count_LL);
        atrium_count_card_view = (CardView) rootView.findViewById(R.id.atrium_count_card_view);
        pollen_score_card_view = (CardView) rootView.findViewById(R.id.pollen_score_card_view);
        userButterflyImgView = (ImageView) rootView.findViewById(R.id.user_butterfly_imageView);
        userNameLayout = (LinearLayout) rootView.findViewById(R.id.user_name_layout);
        userNameTextView = (TextView) rootView.findViewById(R.id.user_name_text_view);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        NetworkCalls.getUserInfo(MainActivity.user_info.getUser_id(), getContext());
    }


    @Override
    public void onStart() {
        super.onStart();
//        android.widget.Toast.makeText(getContext(), "3. OnStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
//        android.widget.Toast.makeText(getContext(), "4. OnResume", Toast.LENGTH_SHORT).show();
        super.onResume();
//        android.widget.Toast.makeText(getContext(), "switching in", Toast.LENGTH_SHORT).show();

//        //This UserAuth object is initialized using the very first login object, UserAuth,
//        //that is generated from this login form. It gives us a userId, which we need.
//        UserAuth user = (UserAuth) response.body();
        //NetworkCalls.getDailyTaskOfUser(user.getUser_id(),MainActivity.this);
        //This will build and assign a UserInfo instance to the user_info variable above
        //for package-wide use.

        setName();

        userPollenCount = getUserPollenCount();
//        setCount(userPollenTv, userPollenCount);

        userPollenTv.setText(String.valueOf(userPollenCount));

        userButterflyCount = getUserAtriumCount();
        setCount(userAtriumCountTv, userButterflyCount);

        pollen_score_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(getContext(), "This is Pollen Count", Toast.LENGTH_SHORT).show();
            }
        });

        pollen_score_card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "This is Pollen Count", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        atrium_count_card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "This is Count of all your butterflies", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        atrium_count_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_navigate = new Intent(getContext(), AtriumScreen.class);
                startActivity(to_navigate);

            }
        });

        userButterflyImgView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                android.widget.Toast.makeText(getContext(), "Superfly Butterfly, Play the AR Game to find more", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        userButterflyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(getContext(), "Superfly Butterfly, Play the AR Game to find more", Toast.LENGTH_SHORT).show();
            }
        });

        userNameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sp;
                sp = getActivity().getSharedPreferences("AroraPreferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear().apply();
                Intent intent = new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            }
        });

        userNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(getContext(), "Hello "+MainActivity.user_info.getUser_name() + " - Long press to logout", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void recreateActivity() {
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
//        overridePendingTransition(0, 0);
        startActivity(intent);
//        overridePendingTransition(0, 0);
    }

    private void setName() {
        userNameTextView.setText(MainActivity.user_info.getUser_name());
    }

    private void setCount(TextView tv, Integer count) {
        tv.setText(String.valueOf(count));

    }

    private Integer getUserPollenCount() {
        return MainActivity.user_info.getUser_pollen();
    }

    private Integer getUserAtriumCount(){
        return MainActivity.user_info.getTotalButterflyCount() ;
    }

    private Integer getUserId(){
        return MainActivity.user_info.getUser_id();
    }

}