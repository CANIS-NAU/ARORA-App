package com.example.aorora;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.model.Butterfly;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackendTest extends AppCompatActivity {

    Button makeCall;
    Button post;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_test);

        post = (Button) findViewById(R.id.button2);
        makeCall = (Button) findViewById(R.id.backend_make_call_button);
        output = (TextView) findViewById(R.id.data_output_backend);

        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

                Call<List<Butterfly>> call = service.getButterflyInfo();
                call.enqueue(new Callback<List<Butterfly>>() {

                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.Butterfly>> call, Response<List<Butterfly>> response) {
                        output.append("Butterfly ID: " + response.body().get(0).getButterflyId() + "\n");
                        output.append("Butterfly Created At: " + response.body().get(0).getButterflyCreatedAt());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.Butterfly>> call, Throwable t) {
                        Toast.makeText(BackendTest.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.aorora.model.Butterfly butterfly = new Butterfly(123,1,"");
                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);
                Call<Butterfly> call = service.createButterfly(butterfly);
                call.enqueue(new Callback<Butterfly>() {
                    @Override
                    public void onResponse(Call<Butterfly> call, Response<Butterfly> response) {
                        Toast.makeText(BackendTest.this, "Butterfly CREATED!!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Butterfly> call, Throwable t) {
                        Toast.makeText(BackendTest.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}

/*

    Code to get details of butterfly
     com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

                Call<List<Butterfly>> call = service.getButterflyInfo();
                call.enqueue(new Callback<List<Butterfly>>() {

                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.Butterfly>> call, Response<List<Butterfly>> response) {
                        output.append("Butterfly ID: " + response.body().get(0).getButterflyId() + "\n");
                        output.append("Butterfly Created At: " + response.body().get(0).getButterflyCreatedAt());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.Butterfly>> call, Throwable t) {
                        Toast.makeText(BackendTest.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
 */

/*
User user = new User(123, "John Doe");
Call<User> call = apiService.createuser(user);
call.enqueue(new Callback<User>() {
  @Override
  public void onResponse(Call<User> call, Response<User> response) {

  }

  @Override
  public void onFailure(Call<User> call, Throwable t) {

  }
 */