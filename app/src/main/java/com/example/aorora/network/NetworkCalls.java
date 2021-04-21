package com.example.aorora.network;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.model.DailyTask;
import com.example.aorora.model.DailyTaskReturn;
import com.example.aorora.model.LocalUpdate;
import com.example.aorora.model.MoodReportIdReturn;
import com.example.aorora.model.NotificationCreateReturn;
import com.example.aorora.model.QuestReportCreateReturn;
import com.example.aorora.model.SuperflySession;
import com.example.aorora.model.UserInfo;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aorora.network.NetworkCallsInternal.writeLocalUpdate;

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
                Toast.makeText(context, "Points update failed, writing to file.", Toast.LENGTH_SHORT).show();
                //Call the internal json file writing function to store this update locally
                writeLocalUpdate(context);
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }


    public static void updateUserAtrium(int user_id, Map<String, Integer> counts, final Context context) {
        Call call = service.updateUserAtrium(user_id, counts);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                {
                    Toast.makeText(context, " Atrium Counts Updated Successfully", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Atrium Update FAILED!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Points update failed, writing to file.", Toast.LENGTH_SHORT).show();
                //Call the internal json file writing function to store this update locally
                writeLocalUpdate(context);
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
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
                    //Use static variable exposed from MainActivity to store the user_info and access
                    //Across all activities.
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

    /**
     * Call to post a new superfly session. This is not for when invites are accepted, use PATCH!
     */
    public static void createSuperflySession(int participant_0, final Context context){
        Call<SuperflySession> call = service.createSession(participant_0);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {
                Toast.makeText(context, "Superfly session created successfully!", Toast.LENGTH_SHORT).show();
                Log.d("RESPONSESTR", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Toast.makeText(context, "Superfly session did not work!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Call to get the current status of a superfly session
     */
    public static void getSuperflySession(Integer session_id, final Context context){
        //TODO: GET request.
    }

    /**
     * PATCHES a user into the new session
     * @param session_id Session primary key for the target session to join
     * @param new_participant The user who seeks to join the superfly session
     * @param context Where this call came from.
     */
    public static void joinSession(Integer session_id, Integer new_participant, final Context context){

    }

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


    /**/

    /**
     * Local Update Check to update the backend if a network connection
     *     is detected using the locally stored json file holding updates to the user info
     *     model since the last network connection.
     * @param context Context of the calling activity.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void checkLocalUpdates(final Context context){
        String updateFileName = "localupdate.json";
        //Use the passed context to find the location to write the json file.
        File localFilesDir = context.getFilesDir();
        String pathStr = localFilesDir + "/" + updateFileName;
        final Path pathObj = Paths.get(pathStr);

        //If we have no JSON file stored, do nothing.
        if(!Files.exists(pathObj)){
            Toast.makeText(context, "Local Update NOT Detected!", Toast.LENGTH_SHORT).show();
            Log.d("CheckLocalUpdates", "No update detected");
            return;
        }
        //Otherwise there is a local update ready to parse.
        Toast.makeText(context, "Local Update Detected!", Toast.LENGTH_SHORT).show();

        //Init gson to read to object
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
        //Init buffered reader to read json file.
        BufferedReader brJson = null;
        try {
            //Open up a reader to be used by gson to parse the file to the LocalUpdate object.
            brJson = new BufferedReader(new FileReader(pathStr));
            //This type token tells gson that we want to parse JSON into a LocalUpdate object.
            Type type = new TypeToken<LocalUpdate>(){}.getType();
            //Create a LocalUpdate object using gson and our reader and type fields.
            final LocalUpdate newUpdate = gson.fromJson(brJson, type);
            final boolean finished = false;
            Log.d("LOCALUPDATEOBJ Network", "LocalUpdate object from json: " + newUpdate.toString());
            //Now that we have the local updates, lets push the values to the backend.
            NetworkCallsInternal.updateUserCurrentPoints(MainActivity.user_info.getUser_id(), newUpdate.getPollenScore(), context, new RetrofitResponseListener() {
                @Override
                public void onSuccess() {
                    //Update local model values as well.
                    MainActivity.user_info.setUser_pollen(newUpdate.getPollenScore());

                    //That worked, now update the Atrium too.
                    NetworkCallsInternal.updateUserAtrium(MainActivity.user_info.getUser_id(), newUpdate.getUserAtrium(), context, new RetrofitResponseListener() {
                        @Override
                        public void onSuccess() {
                            MainActivity.user_info.update_local_atrium(newUpdate.getUserAtrium());
                            //Since both calls have succeeded, delete our local file.
                            try {
                                Files.deleteIfExists(pathObj);
                                Toast.makeText(context, "All updates successful, deleting json.", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Log.d("checkLocalUpdates", "Couldn't find json to delete????");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure() {
                            Log.d("Pollen Local Update", "NETWORK UPDATE OF ATRIUM FAILED FROM LOCAL FILE.");
                            return;
                        }
                    });
                }

                @Override
                public void onFailure() {
                    Log.d("Pollen Local Update", "NETWORK UPDATE OF POLLEN FAILED FROM LOCAL FILE.");
                    return;
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
