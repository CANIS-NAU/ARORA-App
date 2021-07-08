package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.TypedArrayUtils;

import com.example.aorora.model.DailyTask;
import com.example.aorora.model.UserAuth;
import com.example.aorora.model.UserInfo;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitClientInstance;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static UserInfo user_info;
    public static DailyTask daily_task;

    Button login_button;
    EditText username_et, password_et;
    CheckBox saveLoginInfo;
    AppCompatImageView butterflyImage;

    Intent surveyPage;
    Context context;

    //This service is our backend connection that will respond to http requests we made in GetDataService.java
    GetDataService service;

    SharedPreferences sp;
    Boolean saveLoginInfoBool;

//    SharedPreferences.Editor editor = sp.edit();

//    Integer []themes = {R.style.THEME_DUSK, R.style.THEME_BLUE, R.style.THEME_EVENING, R.style.THEME_MOUNTAIN, R.style.THEME_STAR};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("AroraPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//        int currTheme = sp.getInt("Theme", -1);
//        Toast.makeText(MainActivity.this, String.valueOf(currTheme), Toast.LENGTH_SHORT).show();
//        setTheme(currTheme);
        setContentView(R.layout.activity_main);


        login_button = findViewById(R.id.login_button);
        username_et = findViewById(R.id.login_email_et);
        password_et = findViewById(R.id.login_password_et);
        saveLoginInfo = findViewById(R.id.saveLoginCheckBox);
//        butterflyImage = findViewById(R.id.logo_butterfly);
        //Init our backend service
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        context = this;

        saveLoginInfoBool = sp.getBoolean("save_login", false);
        if(saveLoginInfoBool){
            // call login function here
            String username = sp.getString("username","");
            String password = sp.getString("password","");
            login(username, password);
            Toast.makeText(this, "Welcome back " + sp.getString("username", ""), Toast.LENGTH_SHORT).show();
        }


        // Login button ON click Listener
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = username_et.getText().toString();
                String password = password_et.getText().toString();

                if (validateLogin(username, password)) {
                    login(username, password);
                    editor.clear().apply();
                    if (saveLoginInfo.isChecked()){
                        editor.putBoolean("save_login", true);
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();
                    }
                    else {
                        editor.clear().commit();
                    }
                }

            }
        });

//
//        butterflyImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int newTheme = new Random().nextInt(themes.length);
//                editor.putInt("Theme", themes[newTheme]).apply();
//                recreate();
//            }
//        });
//
    }


    private boolean validateLogin(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            username_et.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(username_et, InputMethodManager.SHOW_IMPLICIT);
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            password_et.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(password_et, InputMethodManager.SHOW_IMPLICIT);
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void login(String username, String password) {
        Log.d("Beginning login", "Logging into the system via login function");
        Call<UserAuth> call = service.login(username, password);
        Log.d("Beginning login", "Invoked service.login");
        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.isSuccess()) {
                    //This UserAuth object is initialized using the very first login object, UserAuth,
                    //that is generated from this login form. It gives us a userId, which we need.
                    UserAuth user = (UserAuth) response.body();
                    //NetworkCalls.getDailyTaskOfUser(user.getUser_id(),MainActivity.this);
                    //This will build and assign a UserInfo instance to the user_info variable above
                    //for package-wide use.
                    NetworkCalls.getUserInfo(user.getUser_id(), MainActivity.this);
                    surveyPage = new Intent(context, slider_survey_page.class);
                    startActivity(surveyPage);
                } else {
                    Toast.makeText(MainActivity.this, "USER ID or PASSWORD IS WRONG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("VERBOSE1", "" + t.getCause());
                    Log.e("VERBOSE2", "" + t.getMessage());
                    Log.e("VERBOSE3", "" + t.toString());
                    Toast.makeText(MainActivity.this, "Couldn't reach the network, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
