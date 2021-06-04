package com.example.aorora.network;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.SuperflyGamePage;
import com.example.aorora.model.DailyTask;
import com.example.aorora.model.DailyTaskReturn;
import com.example.aorora.model.LocalUpdate;
import com.example.aorora.model.MoodReportIdReturn;
import com.example.aorora.model.NotificationCreateReturn;
import com.example.aorora.model.QuestReportCreateReturn;
import com.example.aorora.model.SuperflyInvite;
import com.example.aorora.model.SuperflySession;
import com.example.aorora.model.TradeRequest;
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
import java.util.ArrayList;
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

    public static void updateUserAtrium(int user_id, Map<String, Integer> counts, final Context context, RetrofitResponseListener networkCallListener) {
        Call call = service.updateUserAtrium(user_id, counts);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                {
                    Toast.makeText(context, " Atrium Counts Updated Successfully", Toast.LENGTH_SHORT).show();
                    networkCallListener.onSuccess();

                }
                else
                {
                    Toast.makeText(context, "Atrium Update FAILED!", Toast.LENGTH_SHORT).show();
                    networkCallListener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Points update failed, writing to file.", Toast.LENGTH_SHORT).show();
                networkCallListener.onFailure();
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

    public static void setUserStagedButterfly(int user_id, int staged_butterfly, final Context context, RetrofitResponseListener networkCallListener){
        Call call = service.setUserStagedButterfly(user_id, staged_butterfly);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess()){
                    Log.d("setUserStaged", "Butterfly staged successfully");
                    networkCallListener.onSuccess();
                }
                else{
                    networkCallListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("setUserStaged", "Butterfly stage unsuccessful");
                networkCallListener.onFailure();
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
                    //Grab the current superfly session, if there is one for this user.
                    if(MainActivity.user_info.getUser_superflysession_id() != -1){
                        NetworkCalls.getSuperflySession(MainActivity.user_info.getUser_superflysession_id(), context);
                    }
                    //Load invites without listening for the response.
                    NetworkCalls.loadInvites(MainActivity.user_info.getUser_id(), context);
                    NetworkCalls.loadTradeRequests(MainActivity.user_info.getUser_id(), context);

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

    public static void getUserInfo(int user_id, final Context context, RetrofitResponseListener networkCallListener)
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
                    //Grab the current superfly session, if there is one for this user.
                    if(MainActivity.user_info.getUser_superflysession_id() != -1){
                        NetworkCalls.getSuperflySession(MainActivity.user_info.getUser_superflysession_id(), context, new RetrofitResponseListener() {
                            @Override
                            public void onSuccess() {
                                NetworkCalls.loadTradeRequests(MainActivity.user_info.getUser_id(), context, new RetrofitResponseListener() {
                                    @Override
                                    public void onSuccess() {
                                        //Load invites without listening for the response.
                                        NetworkCalls.loadInvites(MainActivity.user_info.getUser_id(), context);

                                        Log.d("RESPONSESTR", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                                        networkCallListener.onSuccess();
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                    else{
                        //Load invites without listening for the response.
                        NetworkCalls.loadInvites(MainActivity.user_info.getUser_id(), context);

                        Log.d("RESPONSESTR", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                        networkCallListener.onSuccess();
                    }

                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    networkCallListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                networkCallListener.onFailure();
            }
        });
    }

    public static void createUserSuperfly(int user_id, int superfly_id, final Context context){
        Call call = service.createUserSuperfly(user_id, superfly_id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess()){
                    Toast.makeText(context, "Superfly created!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("CreateUserSuperfly","Creation post request failed.");
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
                Log.d("Made it into onResponse with code", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(context, "Superfly session created successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("RESPONSESTR", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    SuperflySession newSession = response.body();
                    newSession.buildParticipantsArray();
                    Log.d("NEWSESSION", "Current_id of new session:  " + newSession.toString());
                    //Set the current session
                    MainActivity.user_info.setCurrentSession(newSession);
                    //Since we succeeded, navigate to the new session with passed context.
                    Intent intent = new Intent(context, SuperflyGamePage.class);
                    context.startActivity(intent);
                }
                else{
                    Log.d("HTTP RESP", "Alternate response code detected!");
                }

            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Toast.makeText(context, "Superfly session did not work!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * Call to post a new superfly session. This has a callback to the UI as well.
     */
    public static void createSuperflySession(int participant_0, final Context context, RetrofitResponseListener networkCallListener){
        Call<SuperflySession> call = service.createSession(participant_0);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {
                Log.d("Made it into onResponse with code", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(context, "Superfly session created successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("RESPONSESTR", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    SuperflySession newSession = response.body();
                    newSession.buildParticipantsArray();
                    Log.d("NEWSESSION", "Current_id of new session:  " + newSession.toString());
                    //Set the current session
                    MainActivity.user_info.setCurrentSession(newSession);
                    //Since we succeeded, navigate to the new session with passed context.
                    Intent intent = new Intent(context, SuperflyGamePage.class);
                    context.startActivity(intent);
                    networkCallListener.onSuccess();
                }
                else{
                    Log.d("HTTP RESP", "Alternate response code detected!");
                    networkCallListener.onFailure();
                }

            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Toast.makeText(context, "Superfly session did not work!", Toast.LENGTH_SHORT).show();
                networkCallListener.onFailure();
            }
        });

    }
    /*
    Loads all invite objects corresponding to the passed user id.
    */
    public static void loadInvites(int recipient_id, final Context context){
        Call call = service.getSuperflyInvites(recipient_id);
        //This is used when we want to refresh the UI and need a synchronous method to do so.
        //execute() runs this method synchronously.
        //TODO Refresh invite list.


        //Otherwise we don't need it to be synchronous
        call.enqueue(new Callback<ArrayList<SuperflyInvite>>() {
            @Override
            public void onResponse(Call<ArrayList<SuperflyInvite>> call, Response<ArrayList<SuperflyInvite>> response) {
                try{
                    Log.d("Invite response", response.body().toString());
                    Log.d("Response list", response.body().toString());
                    MainActivity.user_info.setCurrentInvites(response.body());
                    //Show the invites for debugging if desired.
                    for(SuperflyInvite currInvite : response.body()){
                        Log.d("Invite Item", currInvite.toString());
                    }
                }
                catch(Exception e){
                    Log.d("Invite error", "No invites found. " + e.getMessage());

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("INVITEGET", "Yeah it broke" + t.getMessage());


            }
        });
    }



    /*
    Loads all invite objects corresponding to the passed user id.
    */
    public static void loadInvites(int recipient_id, final Context context, RetrofitResponseListener networkCallListener){
        Call call = service.getSuperflyInvites(recipient_id);

        //Otherwise we don't need it to be synchronous
        call.enqueue(new Callback<ArrayList<SuperflyInvite>>() {
            @Override
            public void onResponse(Call<ArrayList<SuperflyInvite>> call, Response<ArrayList<SuperflyInvite>> response) {
                Log.d("Response list", response.body().toString());
                try{
                    Log.d("Invite response", response.body().toString());
                    MainActivity.user_info.setCurrentInvites(response.body());
                    //Show the invites for debugging if desired.
                    for(SuperflyInvite currInvite : response.body()){
                        Log.d("Invite Item", currInvite.toString());
                    }
                    //Communicate success to the calling function/activity
                    networkCallListener.onSuccess();
                }
                catch(Exception e){
                    Log.d("Invite error", "No invites found. " + e.getMessage());
                    //Communicate failure to the calling function/activity
                    networkCallListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("INVITEGET", "Yeah it broke" + t.getMessage());
                //Communicate failure to the calling function/activity
                networkCallListener.onFailure();
            }
        });
    }


    /*
   Loads all trade objects corresponding to the passed user id, no listener here.
   */
    public static void loadTradeRequests(int recipient_id, final Context context ){
        Call<ArrayList<TradeRequest>> call = service.getTradeRequests(recipient_id);

        //Otherwise we don't need it to be synchronous
        call.enqueue(new Callback<ArrayList<TradeRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<TradeRequest>> call, Response<ArrayList<TradeRequest>> response) {
                Log.d("Response list", response.body().toString());
                try{
                    Log.d("Trade response", response.body().toString());
                    MainActivity.user_info.setCurrentTrades(response.body());
                    //Show the invites for debugging if desired.
                    for(TradeRequest currRequest : response.body()){
                        Log.d("Trade Item", currRequest.toString());
                    }
                }
                catch(Exception e){
                    Log.d("Trades loading error", "No invites found. " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("TradesGet", "Yeah it broke" + t.getMessage());
            }
        });
    }



    /*
   Loads all traderequest objects corresponding to the passed user id. Has a listener to callback to the UI.
   */
    public static void loadTradeRequests(int recipient_id, final Context context, RetrofitResponseListener networkCallListener ){
        Call<ArrayList<TradeRequest>> call = service.getTradeRequests(recipient_id);

        //Otherwise we don't need it to be synchronous
        call.enqueue(new Callback<ArrayList<TradeRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<TradeRequest>> call, Response<ArrayList<TradeRequest>> response) {
                Log.d("Response list", response.body().toString());
                try{
                    Log.d("Trade response", response.body().toString());
                    MainActivity.user_info.setCurrentTrades(response.body());
                    //Show the invites for debugging if desired.
                    for(TradeRequest currRequest : response.body()){
                        Log.d("Trade Item", currRequest.toString());
                    }
                    networkCallListener.onSuccess();
                }
                catch(Exception e){
                    Log.d("Trades loading error", "No invites found. " + e.getMessage());
                    networkCallListener.onFailure();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("TradesGet", "Yeah it broke" + t.getMessage());
                networkCallListener.onFailure();

            }
        });
    }

    public static void deleteTradeRequest(int recipient_id, final Context context, RetrofitResponseListener networkCallListener ){
        Call call = service.deleteTradeRequest(recipient_id);

        //Otherwise we don't need it to be synchronous
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("Response list", response.body().toString());
                if (response.isSuccess()) {
                    Log.d("Delete request", "Deleted request successfully.");
                    networkCallListener.onSuccess();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("TradesGet", "Yeah it broke" + t.getMessage());
                networkCallListener.onFailure();

            }
        });
    }

    public static void deleteSuperflyInvites(int recipient_id, final Context context ){
        Call call = service.deleteInvites(recipient_id);

        //Otherwise we don't need it to be synchronous
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccess()) {
                    Log.d("Delete invites", "Deleted invites successfully.");
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Delete invites", "Yeah it broke" + t.getMessage());

            }
        });
    }



    public static void updateSuperflyProgress(int session_id, Map<String, Integer> currentCounts, final Context context, RetrofitResponseListener networkCallListener){
        Call<SuperflySession> call = service.updateSuperflyProgress(session_id, currentCounts);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {
                if(response.isSuccess()){
                    //This might break things with stackoverflow error.
                    MainActivity.user_info.setCurrentSession(response.body());
                    networkCallListener.onSuccess();

                }


            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                networkCallListener.onFailure();
            }
        });
    }

    /**
     * Call to get the current status of a superfly session
     */
    public static void getSuperflySession(Integer session_id, final Context context){
        Call call = service.getSession(session_id);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {

                if(response.code() == 404){
                    Log.d("SuperflySession GET", "No active superfly session found for this user.");

                }
                else{
                    SuperflySession newSession = response.body();
                    newSession.buildParticipantsArray();
                    newSession.buildAssignedButterfliesArray();
                    MainActivity.user_info.setCurrentSession(response.body());
                    Log.d("Session Retrieved", MainActivity.user_info.getCurrentSession().toString());
                }

            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Log.d("getSuperflySession", "Error retrieving superfly session!");

            }
        });
    }

    /**
     * Call to get the current status of a superfly session with listener to callback
     */
    public static void getSuperflySession(Integer session_id, final Context context, RetrofitResponseListener networkCallListener){
        Call call = service.getSession(session_id);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {

                if(response.code() == 404){
                    Log.d("SuperflySession GET", "No active superfly session found for this user.");
                    networkCallListener.onFailure();
                }
                else{
                    SuperflySession newSession = response.body();
                    newSession.buildParticipantsArray();
                    newSession.buildAssignedButterfliesArray();
                    MainActivity.user_info.setCurrentSession(newSession);
                    Log.d("Session Retrieved", MainActivity.user_info.getCurrentSession().toString());
                    networkCallListener.onSuccess();
                }

            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Log.d("getSuperflySession", "Error retrieving superfly session!");
                networkCallListener.onFailure();

            }
        });
    }

    /**
     * PATCHES a user into the new session
     * @param desiredSession  Target session to join
     * @param newParticipant The user who seeks to join the superfly session
     * @param context Where this call came from.
     */
    public static void joinSession(SuperflySession desiredSession, UserInfo newParticipant, final Context context){
        //First, determine how many participants are currently in the session
        Integer participantCount = desiredSession.getSession_participant_count();
        Integer newParticipantId = newParticipant.getUser_id();
        Integer sessionId = desiredSession.getSession_id();
        //Declare call
        Call<SuperflySession> call = null;

        if(desiredSession.getSession_started()){
            Toast.makeText(context, "Cannot join started session!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Cases as we need to update one of the fields, participant 1 - 4, depending on other users.
        switch(participantCount){
            case 1:
                desiredSession.setParticipant_1(newParticipant);
                call = service.addParticipant1(sessionId, newParticipantId);
                Log.d("JoinSession", "One participant found");
                break;
            case 2:
                desiredSession.setParticipant_2(newParticipant);
                call = service.addParticipant2(sessionId, newParticipantId);
                Log.d("JoinSession", "Two participants found");
                break;
            case 3:
                desiredSession.setParticipant_3(newParticipant);
                call = service.addParticipant3(sessionId, newParticipantId);
                Log.d("JoinSession", "Three participants found");
                break;
            case 4:
                desiredSession.setParticipant_4(newParticipant);
                call = service.addParticipant4(sessionId, newParticipantId);
                Log.d("JoinSession", "Four participants found");
                break;
            default:
                Log.d("joinSession", "Too many participants, cannot join!");
                Toast.makeText(context, "Cannot join a session that is full!", Toast.LENGTH_SHORT).show();
                return;
        }
        //Make new variable for use in onResponse to set the current participant count locally.
        final int newCount = participantCount++;

        //Once we made our call and updated the session correctly, queue it up.
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {
                //Set this to true disable navigation to session, for invite page testing mostly.
                Boolean testing = false;

                if(response.code() == 200){
                    SuperflySession newSession = response.body();
                    newSession.buildParticipantsArray();
                    newSession.buildAssignedButterfliesArray();
                    newSession.setSession_participant_count(newCount);
                    Log.d("Joined session", "Setting local session" + newSession.toString());
                    //Add the user to their new session so they can load the game page.
                    MainActivity.user_info.setCurrentSession(newSession);
                    //Since we succeeded, navigate to the new session with passed context.
                    if(!testing) {
                        Intent intent = new Intent(context, SuperflyGamePage.class);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                //Log.d("UpdateSession", "Call attempted: " + call.request().toString());
                Log.d("UpdateSession", "No work big sad: " + t.getMessage());
            }
        });
    }


    public static void leaveSession(int user_id, final Context context){
        Call<UserInfo> call = service.leaveSession(user_id, -1);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    /**
     * Call to delete the session corresponding to this id.
     */
    public static void deleteSuperflySession(Integer session_id, final Context context){
        Call call = service.deleteSession(session_id);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {

                if(response.code() == 404){
                    Log.d("SuperflySession GET", "No active superfly session found for this user.");

                }
                if(response.isSuccess()){
                    Log.d("DeleteSEssion", "Deleted session " + session_id.toString());
                }

            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Log.d("DeleteSession", "Error deleting superfly session!");

            }
        });
    }

    /**
     * Marks the session specified by id as started, setting a boolean flag in the backend.
     * Utilizes a PATCH request in GetDataService:
     * @param session_id The session_id of the session to be started.
     */
    public static void startSession(int session_id, final Context context){
        Call<SuperflySession> call = service.startSession(session_id, true);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {
                Log.d("StartSession", "Session started successfully");
            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Log.d("StartSession", "Session failed to start.");
            }
        });

    }


    /**
     * Marks the session specified by id as started, setting a boolean flag in the backend.
     * Utilizes a PATCH request in GetDataService:
     * @param session_id The session_id of the session to be started.
     */
    public static void startSession(int session_id, final Context context, RetrofitResponseListener networkCallListener){
        Call<SuperflySession> call = service.startSession(session_id, true);
        call.enqueue(new Callback<SuperflySession>() {
            @Override
            public void onResponse(Call<SuperflySession> call, Response<SuperflySession> response) {
                NetworkCalls.getSuperflySession(session_id, context, new RetrofitResponseListener() {
                    @Override
                    public void onSuccess() {
                        MainActivity.user_info.getCurrentSession().buildAssignedButterfliesArray();
                        MainActivity.user_info.getCurrentSession().setSession_started(true);

                        Log.d("StartSession", "Session started successfully");
                        networkCallListener.onSuccess();
                    }

                    @Override
                    public void onFailure() {
                        networkCallListener.onFailure();
                    }
                });

            }

            @Override
            public void onFailure(Call<SuperflySession> call, Throwable t) {
                Log.d("StartSession", "Session failed to start.");
                networkCallListener.onFailure();
            }
        });

    }

    public static void sendTradeRequest(int sender_id, int recipient_id, Map<String, Integer> requestedButterflies, final Context context, RetrofitResponseListener networkCallListener){
        Call<TradeRequest> call = service.createTradeRequest(sender_id, recipient_id, requestedButterflies);
        call.enqueue(new Callback<TradeRequest>() {
            @Override
            public void onResponse(Call<TradeRequest> call, Response<TradeRequest> response) {
                if(response.isSuccess()){
                    networkCallListener.onSuccess();
                }
                else{
                    networkCallListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<TradeRequest> call, Throwable t) {
                networkCallListener.onFailure();
            }
        });
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
    //TODO Replace this manual solution with a shared preferences or sqlite on-device solution.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void checkLocalUpdates(final Context context){
        String updateFileName = "localupdate.json";
        //Use the passed context to find the location to write the json file.
        File localFilesDir = context.getFilesDir();
        String pathStr = localFilesDir + "/" + updateFileName;
        final Path pathObj = Paths.get(pathStr);

        //If we have no JSON file stored, do nothing.
        if(!Files.exists(pathObj)){
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
