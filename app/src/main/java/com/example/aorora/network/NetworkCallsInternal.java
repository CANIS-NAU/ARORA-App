package com.example.aorora.network;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.aorora.model.LocalUpdate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*These network calls are NOT meant to be called by any activity. They will be called from NetworkCalls.java
as supporting utilities such as writing local updates and communicating those updates to the backend after
the user reconnects to a network. Thus this class is package private.
Questions? Email Joe Vargovich: jrv233@nau.edu */

class NetworkCallsInternal {
    //Service interface for network calls via retrofit
    public static GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    /**
     * This function is used to create a new json file with the local updates we want to keep to push
     *   to the backend due to network failure.
     * @param context The context of the calling Activity viewcontroller class.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void writeLocalUpdate(final Context context){
        Log.d("LOCAL UPDATE", "Writing Local update as network failed");
        //Make local update object file, holds pollen and atrium from UserInfo locally.
        LocalUpdate localUpdate = new LocalUpdate();

        //Init gson instance
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String fileName = "localupdate.json";
        //Use the passed context to find the location to write the json file.
        File localFilesDir = context.getFilesDir();
        //String localFilesPath = localFilesDir.toString() + "/";

        //Make buffered writer for efficient writing
        try {
            //Get a writer to the desired location to write our file named above.
            String pathStr = localFilesDir + "/" + fileName;
            Path pathObj = Paths.get(pathStr);

            Writer writer = Files.newBufferedWriter(pathObj);
            Log.d("path", pathStr);
            Log.d("FILE WRITING", "Wrote file at path" + pathStr);
            //Convert to json and write the file
            Log.d("GSON json printed", gson.toJson(localUpdate));
            gson.toJson(localUpdate, writer);
            //Close our writer
            writer.close();
        }
        //Couldn't open or write to the file.
        catch (IOException e) {
            e.printStackTrace();
            //TODO retry here using recursion and count parameter.
        }
    }

    /**
     * This will be called internally from CheckLocalUpdates to see if we can communicate our locally
     * stored pollen update values to the backend sucessfully.
     * @param user_id The user id to be updated
     * @param user_pollen The amount of pollen stored locally.
     * @param context A context, specifically passed from the calling viewcontroller.
     * @param retrofitResponseListener Will be used to communicate if we were sucessful or not
     *  to the calling function.
     */
    public static void updateUserCurrentPoints( int user_id, int user_pollen, final Context context, final RetrofitResponseListener retrofitResponseListener) {

        Call call = service.updateUserPollen(user_id, user_pollen);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                {
                    Toast.makeText(context, " POLLEN UPDATED Updated Successfuly", Toast.LENGTH_SHORT).show();
                    retrofitResponseListener.onSuccess();
                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Points update failed again, keeping json.", Toast.LENGTH_SHORT).show();
                retrofitResponseListener.onFailure();
            }
        });
    }

    //This method will use the retrofitResponseListener to communicate the status of the request back to the calling function.

    /**
     * This will be called internally from CheckLocalUpdates to see if we can communicate our locally
     * stored atrium update values to the backend sucessfully.
     * @param user_id The user id to be updated
     * @param counts The counts of each type of butterfly in the atrium mapping
     * @param context A context, specifically passed from the calling viewcontroller.
     * @param retrofitResponseListener Will be used to communicate if we were sucessful or not
     *  to the calling function.
     */
    public static void updateUserAtrium(int user_id, Map<String, Integer> counts, final Context context, final RetrofitResponseListener retrofitResponseListener) {
        Call call = service.updateUserAtrium(user_id, counts);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                {
                    Toast.makeText(context, " Atrium Counts Updated Successfuly", Toast.LENGTH_SHORT).show();
                    retrofitResponseListener.onSuccess();

                }
                else
                {
                    Toast.makeText(context, "Atrium Update FAILED!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Atrium update failed again, keeping local json.", Toast.LENGTH_SHORT).show();
                retrofitResponseListener.onFailure();
            }
        });
    }
}
