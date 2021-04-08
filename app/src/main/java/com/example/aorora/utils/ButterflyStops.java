package com.example.aorora.utils;
import android.location.*;
import android.util.Log;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/*
This is a currently hardcoded list of butterfly locations to be referenced and used to spawn certain
butterflies based on a user's geolocation.
 */
public class ButterflyStops {
    Map<String, Location> coordinateMap;

    public ButterflyStops() {
        coordinateMap = new HashMap<>();
        coordinateMap.put("Home", createLocation(33.3441741,-111.5888014));
        coordinateMap.put("Union", createLocation(35.18885857101099, -111.65491450017184));
    }
    public Map<String, Location> getCoordinateMap(){return coordinateMap;}

    //Create a location to be added to the CoordinateMap.
    private Location createLocation(double lat, double lon){
        Location newLocation = new Location("");
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lon);
        return newLocation;
    }

    public Location getLocation(String location){
        //If we have the coordinate registered, return it.
        if(coordinateMap.get(location) != null){
            return coordinateMap.get(location);
        }
        //Otherwise there is not a mapping to that stop.
        Log.d("Coord not registered", "COORDINATE NOT FOUND IN LOCATIONINFO COORDINATEMAP");
        return new Location("");

    }
}

