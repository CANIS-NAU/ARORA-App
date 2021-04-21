package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aorora.model.DailyTask;
import com.example.aorora.model.UserAuth;
import com.example.aorora.model.UserInfo;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitClientInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static UserInfo user_info;
    public static DailyTask daily_task;

    Button  login_button;
    Intent surveyPage;
    Context context;
    EditText username_et;
    EditText password_et;
    //Determined if a user has logged in before. To be used in autopopulating login form.
    boolean is_first_time_username_et;
    boolean is_first_time_password_et;
    //This service is our backend connection that will respond to http requests we made in GetDataService.java
    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = findViewById(R.id.login_button);
        username_et = findViewById(R.id.login_email_et);
        password_et = findViewById(R.id.login_password_et);
        is_first_time_password_et = true;
        is_first_time_username_et = true;
        //Init our backend service
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        context = this;

        username_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_first_time_username_et)
                {
                    username_et.setText("");
                    is_first_time_username_et = false;
                }
            }
        });

        password_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_first_time_password_et)
                {
                    password_et.setText("");
                    is_first_time_password_et = false;
                }
            }
        });

        // Login button ON click Listener
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = username_et.getText().toString();
                String password = password_et.getText().toString();
                if(validateLogin(username, password))
                {
                    login(username,password);
                }
            }
        });

    }

    private void login(String username, String password)
    {
        Log.d("Beginnign login", "Logging into the system via login function");
        Call<UserAuth> call = service.login(username,password);
        Log.d("Beginnign login", "Invoked service.login");
        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if(response.isSuccess())
                {
                    //This UserAuth object is initialized using the very first login object, UserAuth,
                    //that is generated from this login form. It gives us a userId, which we need.
                    UserAuth user = (UserAuth) response.body();
                    //NetworkCalls.getDailyTaskOfUser(user.getUser_id(),MainActivity.this);
                    //This will build and assign a UserInfo instance to the user_info variable above
                    //for package-wide use.
                    NetworkCalls.getUserInfo(user.getUser_id(), MainActivity.this);
                    surveyPage = new Intent(context, slider_survey_page.class);
                    startActivity(surveyPage);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "USER ID or PASSWORD IS WRONG", Toast.LENGTH_SHORT).show();
                }
                //surveyPage = new Intent(context, SurveyPage.class);
                //startActivity(surveyPage);
            }
            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                if (t instanceof IOException)
                {
                    //Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    Log.e("VERBOSE1", "" + t.getCause());
                    Log.e("VERBOSE2", "" + t.getMessage());
                    Log.e("VERBOSE3", "" + t.toString());
                    Toast.makeText(MainActivity.this, "Couldn't reach the network, try again.", Toast.LENGTH_SHORT).show();

                    // logging probably not necessary
                }
                else {
                    //Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
