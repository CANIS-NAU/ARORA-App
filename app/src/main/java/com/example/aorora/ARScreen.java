package com.example.aorora;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aorora.interfaces.GeoCoordsCallback;
import com.example.aorora.model.LocalUpdate;
import com.example.aorora.network.CheckConnectivity;
import com.example.aorora.network.GetConnInfo;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitClientInstance;
import com.example.aorora.utils.ButterflyStops;
//Local GPS Packages
import android.location.Location;

//Networked GPS Packages
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/*Eventual Gateway to M4, which will take 10 pollen from the user and launch the necessary activity
for the AR or 2D gamification element where users catch butterflies. This is also a test page for
new features, such as geocooordinates, json storage of local values, and internet connectivity checks.
These feature tests are run onclick of the get coordinates button.

This class implements both the onclicklistener interface and GetConnInfo. The important interface,
GetConnInfo, is used to return geocoordinates to this Activity from the AsyncTask CheckConnectivity
after waiting for it to complete. TODO: Refactor usage of interface to remove direct implementation.
 */
public class ARScreen extends AppCompatActivity implements View.OnClickListener, GetConnInfo {
    //User account info
    Integer userPollen;
    Integer userId;

    //Retrofit network object
    GetDataService service;

    //Geolocation service
    private FusedLocationProviderClient fusedLocationClient;
    //Coordinate list we defined
    Context arScreen;
//    ImageButton home_button_bottombar;
//    ImageButton profile_button_bottombar;
//    ImageButton community_button_bottombar;
//    ImageButton quest_button_bottombar;
    Button spendPollenBtn;
    Button coordsBtn;
    Location currentLocation;
    ButterflyStops butterflyStops;
    Boolean isConnected;
    CheckConnectivity connCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //User account info
        userPollen = MainActivity.user_info.getUser_pollen();
        userId = MainActivity.user_info.getUser_id();

        //Retrofit objects
        //Init our backend service
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        //Init fused location client, should work with either network or gps.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Init butterfly locations
        butterflyStops = new ButterflyStops();
        Location locationHome = butterflyStops.getLocation("Home");
        Log.d("My haus", locationHome.toString());


        setContentView(R.layout.activity_ar_screen);
        arScreen = this;
        spendPollenBtn = (Button) findViewById(R.id.spend_pollen_btn);
        coordsBtn = (Button) findViewById(R.id.geo_coord_bttn);


        //Onclicklisteners for this class.
//        home_button_bottombar.setOnClickListener(this);
//        profile_button_bottombar.setOnClickListener(this);
//        community_button_bottombar.setOnClickListener(this);
//        quest_button_bottombar.setOnClickListener(this);

        //This onClickListener will spend pollen if the user has at least 10 of it.
        spendPollenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!hasEnoughPollen()) {
                    Toast.makeText(ARScreen.this, "Sorry! Not enough pollen! Complete some quests!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Otherwise, we have enough pollen, decrement it and update the backend.
                userPollen -= 10;
                //Finally do the PUT request with the new pollen value. May need to refresh the UI.
                MainActivity.user_info.setUser_pollen(userPollen);
                //This will update the backend and set the current pollen to our decremented value.
                NetworkCalls.updateUserCurrentPoints(userId, userPollen, ARScreen.this);

                //intent to butterfly game
                startActivity(new Intent(arScreen, ButterflyGameActivity.class));
            }
        });

        coordsBtn.setOnClickListener(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        runConnCheck();

    }

    private void runConnCheck(){
        //Init async check for connectivity
        connCheck = new CheckConnectivity();
        //Set the connCheck to reference this class when returning the connection boolean.
        connCheck.connInfoCheck = ARScreen.this;
        //Do the connectivity check everytime we resume the activity. Start the check itself.
        connCheck.execute();
    }

    /*Suppress this lint warning, I call hasPermissions and get all needed permissions, or disallow
      the user to continue if they do not accept them.
    */
    @SuppressLint("MissingPermission")
    private void getUserLocation(final GeoCoordsCallback geoCallback) {
        Toast.makeText(this, "Getting user coordinates", Toast.LENGTH_SHORT).show();
        String[] permissionsNeeded = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        Location currentLocation;
        //Check if we have permissions, if not, ask for them.
        if (!hasPermissions(this, permissionsNeeded)) {
            ActivityCompat.requestPermissions(this, permissionsNeeded, 1);
        }
        //After we request the permissions, check again. If they are not provided display a Toast.
        if(!hasPermissions(this, permissionsNeeded)){
            Toast.makeText(this, "You must provide permissions to use the location services.", Toast.LENGTH_SHORT).show();
        }
        //If we do end up having the permissions, then try to get the location.
        else{
            //Handled by the checks above, getting permissions works with it.
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                //Send the location back to the listener via the callback.
                                geoCallback.onCallBack(location);
                            }
                        }
                    });
        }
    }
    //Function to check for permissions passed to it to see if they are granted or not.
    public Boolean hasPermissions(Context ct, String[] inputPermissions) {
        //Make sure we have a context and permission list.
        if(ct != null && inputPermissions != null){
            //Check each permission in the list and see if they are GRANTED.
            for(String currPermission : inputPermissions){
                if(ActivityCompat.checkSelfPermission(ct,currPermission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        //If we either have nothing to check, or we didn't have a denied permission, return true.
        return true;
    }

    //Check called before launching the game.
    public boolean hasEnoughPollen() {
        return userPollen >= 10;
    }


    /*
    This function will check if we are nearby any butterflystops, if so, it returns which ones.
    TODO: Make butterfly location subclass that holds the type of butterflies to spawn and related
    info at a location.
     */
    private Map<String, Location> findNearbyLocations(Location currLocation) {
        Log.d("KEYS", butterflyStops.getCoordinateMap().keySet().toString());
        for(Map.Entry<String,Location> currEntry : butterflyStops.getCoordinateMap().entrySet()) {
            String locationStr = currEntry.getKey();
            Log.d("CURR VALUE", "Current key value" + currEntry.getValue().getLatitude());
            double distance = currLocation.distanceTo(currEntry.getValue());
            Log.d("Location Checks", "findNearby: Distance to " + locationStr + "from current location: " + distance);
        }
        return null;
        }

    /*
    Implemented interface function that receives a boolean from the AsyncTask CheckConnectivity, which
    takes the GetConnInfo interface as a class member to communicate it back here.
     */
    @Override
    public void getConnInfo(Boolean isConnected){
        this.isConnected = isConnected;
        Log.d("GetConnInfo", "Is connected? " + isConnected.toString());
    }

    public void writeFileTest(){
        //Create localupdate object.
        LocalUpdate localUpdate = new LocalUpdate();

        //Init gson instance
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String fileName = "localupdate.json";
        File file = new File(fileName);

        File localFilesDir = ARScreen.this.getFilesDir();
        String localFilesPath = localFilesDir.toString() + "/";

        //Make buffered writer for efficient writing
        try {
            //Get a writer to the desired location to write our file named above.
            String path = localFilesDir + "/" + fileName;

            Writer writer = Files.newBufferedWriter(Paths.get(path));
            Log.d("path", path);

            //Log.d("FILE WRITING", "Wrote file at path" + path);
            //Convert to json and write the file
           // Log.d("GSON json", gson.toJson(localUpdate));
            gson.toJson(localUpdate, writer);
            //Close our writer
            writer.close();

            Log.d("Files", "Path: " + path);
            File directory = new File(localFilesPath);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }

            for (int i = 0; i < files.length; i++)
            {
                Log.d("Files", "FileName: " + files[i].getName());

            }


            BufferedReader brJson = new BufferedReader(new FileReader(path));
            Type type = new TypeToken<LocalUpdate>(){}.getType();
            LocalUpdate newUpdate = gson.fromJson(brJson, type);


            Log.d("LOCALUPDATEOBJ", "LocalUpdate object from json: " + newUpdate.toString());

        }
        //Couldn't open or write to the file.
        catch (IOException e) {
            e.printStackTrace();
        }

    }


   @Override
    public void onClick(View v) {
        int view_id = v.getId();

        if(view_id == coordsBtn.getId()){
            writeFileTest();
            runConnCheck();
            if(!isConnected) {
                Toast.makeText(this, "Not connected!", Toast.LENGTH_SHORT).show();
            }
                //First trying to display latitude.
                getUserLocation(new GeoCoordsCallback() {
                    @Override
                    public void onCallBack(Location returnedLocation) {
                        Log.d("Returned Lat", "returnedLocationLat" + returnedLocation.getLatitude());
                        Toast.makeText(ARScreen.this, "Latitude: " + returnedLocation.getLatitude() + "Long: " + returnedLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        currentLocation = returnedLocation;
                        //Make our coordinate object from the returned location.
                        findNearbyLocations(currentLocation);
                    }
                });

        }

    }
    //Have an onDestroy method for when we navigate away from this page.
    @Override
    protected void onDestroy(){
        //If we have a connCheck thread running, destroy it to prevent a memory leak.
        if(connCheck != null && connCheck.getStatus() == AsyncTask.Status.RUNNING){
            connCheck.cancel(true);
        }
        connCheck = null;
        super.onDestroy();
    }

}