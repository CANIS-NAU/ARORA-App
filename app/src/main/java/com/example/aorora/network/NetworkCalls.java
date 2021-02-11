package com.example.aorora.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.model.DailyTask;
import com.example.aorora.model.DailyTaskReturn;
import com.example.aorora.model.MoodReportIdReturn;
import com.example.aorora.model.NotificationCreateReturn;
import com.example.aorora.model.QuestReportCreateReturn;
import com.example.aorora.model.UserInfo;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCalls {

    public static GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    public static void updateUserCurrentButterfly(int user_id, int user_butterfly_id, final Context context) {

        Call call = service.updateUserCurrentButterfly(user_id, user_butterfly_id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {
                    //Toast.makeText(context, "butterfly Id Updated Successfuly", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateUserCurrentPoints(int user_id, int user_pollen, final Context context) {

        Call call = service.updateUserPollen(user_id, user_pollen);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                {
                    Toast.makeText(context, " POLLEN UPDATED Updated Successfuly", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //This probably doesn't work yet, but its a good start.
    public static void updateUserAtrium(int user_id, Map<String, Integer> counts, final Context context) {
        Call call = service.updateUserAtrium(user_id, counts);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                {
                    Toast.makeText(context, " Atrium Counts Updated Successfuly", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Atrium Update FAILED!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                try {
                    FileWriter file = new FileWriter("../../res/raw/backendUpdates.txt");
                } catch (IOException e) {
                    Log.d("updateUserAtrium", "Error writing to file");
                    e.printStackTrace();
                }
                //Create json file with gson
                Gson gson = new Gson();
                Type gsonType = new TypeToken<HashMap>(){}.getType();
                String gsonString = gson.toJson(MainActivity.user_info.get_local_atrium(), gsonType);
                Log.d("updateUserAtrium", "Json version of atrium" + gsonString);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getUserInfo(int user_id, final Context context)
    {
        //Find these services in the interface GetDataService. Create a UserInfo object
        Call<UserInfo> call = service.getUserInfo(user_id);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {
                    //PACKAGE GLOBAL USED WITHOUT DOCUMENTATION. BAD! Specify that user_info is from
                    //MainActivity!
                    MainActivity.user_info = response.body();
                    //Since the user's atrium map is not a serialized value from the backend, we must initialize
                    //it manually with this function.
                    MainActivity.user_info.build_atrium();
                    Log.d("RESPONSESTR", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    //Toast.makeText(context, "User Info Gathered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void createMoodReport(int user_id, int q1_response, int q2_response, final Context context)
    {
        Call<MoodReportIdReturn> call = service.createMoodReport(user_id,q1_response,q2_response);
        call.enqueue(new Callback<MoodReportIdReturn>() {
            @Override
            public void onResponse(Call<MoodReportIdReturn> call, Response<MoodReportIdReturn> response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {

                    //Toast.makeText(context, "Mood Report Created", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MoodReportIdReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getDailyTaskOfUser(int user_id, final Context context)
    {
        Call<DailyTask> call = service.getDailyTask(user_id);
        call.enqueue(new Callback<DailyTask> () {

            @Override
            public void onResponse(Call<DailyTask>  call, Response<DailyTask> response) {
                //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                MainActivity.daily_task = response.body();
            }

            @Override
            public void onFailure(Call<DailyTask>  call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateDailyTaskM1(int user_id, int update, final Context context)
    {

        Call<DailyTaskReturn> call = service.updateDailyTaskM1(user_id,user_id,update);
        call.enqueue(new Callback<DailyTaskReturn>() {
            @Override
            public void onResponse(Call<DailyTaskReturn> call, Response<DailyTaskReturn> response) {
                //Toast.makeText(context, "M1 Achieved: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<DailyTaskReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateDailyTaskM2(int user_id, int update, final Context context)
    {

        Call<DailyTaskReturn> call = service.updateDailyTaskM2(user_id,user_id,update);
        call.enqueue(new Callback<DailyTaskReturn>() {
            @Override
            public void onResponse(Call<DailyTaskReturn> call, Response<DailyTaskReturn> response) {
                //Toast.makeText(context, "M1 Achieved: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<DailyTaskReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateDailyTaskM3(int user_id, int update, final Context context)
    {

        Call<DailyTaskReturn> call = service.updateDailyTaskM3(user_id,user_id,update);
        call.enqueue(new Callback<DailyTaskReturn>() {
            @Override
            public void onResponse(Call<DailyTaskReturn> call, Response<DailyTaskReturn> response) {
                //Toast.makeText(context, "M1 Achieved: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<DailyTaskReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void createQuestReport(int quest_id, int user_id, final Context context)
    {
        Call<QuestReportCreateReturn> call = service.createQuestReport(quest_id,user_id);
        call.enqueue(new Callback<QuestReportCreateReturn>() {
            @Override
            public void onResponse(Call<QuestReportCreateReturn> call, Response<QuestReportCreateReturn> response) {
                //Toast.makeText(context, "Quest Report Created ID: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<QuestReportCreateReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //public static void getNotificationTypeById

    /**
     * To be used for when the user hits the like button on a notification
     * @param sender_id
     * @param receiver_id
     * //@param context
     */
    public static void createLike( int sender_id, int receiver_id , int interaction_type, int quest_report_id, String context)
    {

        Call<NotificationCreateReturn> call = service.createLike( sender_id, receiver_id, interaction_type, quest_report_id, context);
        call.enqueue(new Callback<NotificationCreateReturn>() {
            @Override
            public void onResponse(Call<NotificationCreateReturn> call, Response<NotificationCreateReturn> response) {
                //Toast.makeText(context, "Quest Report Created ID: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<NotificationCreateReturn> call, Throwable t) {
               // Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * To be used for when the user hits the like button on a notification
     * @param user_interaction_id
     */
    public static void removeLike(final int user_interaction_id)
    {/**
        Call<Void> call = service.removeLike( user_interaction_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //Toast.makeText(context, "Quest Report Created ID: " + response.body(), Toast.LENGTH_SHORT).show();
                Log.i("LIKE REMOVED", "Like #"+user_interaction_id);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("LIKE REMOVED", "Like #"+user_interaction_id+" could not be removed.\n", t);
            }
        });*/
    }

    //public static void updateLike(int )
}
