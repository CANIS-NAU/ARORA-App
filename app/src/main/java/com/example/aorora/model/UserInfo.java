package com.example.aorora.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class UserInfo {
    @SerializedName("user_info_id")
    private Integer user_info_id;
    @SerializedName("user_name_of_strength")
    private String user_name_of_strength;
    @SerializedName("user_id")
    private Integer user_id;
    @SerializedName("user_created_at")
    private String user_created_at;
    @SerializedName("user_current_mood")
    private Integer user_current_mood;
    @SerializedName("user_current_mood_updated")
    private String user_current_mood_updated;
    @SerializedName("user_current_location_lat")
    private Integer user_current_location_lat;
    @SerializedName("user_current_location_long")
    private Integer user_current_location_long;
    @SerializedName("user_current_location_updated")
    private String user_current_location_updated;
    @SerializedName("user_current_butterfly")
    private Integer user_current_butterfly;
    @SerializedName("user_pollen")
    private Integer user_pollen;
    @SerializedName("user_points")
    private Integer user_points;
    //Counts of the first 5 types of butterflies.
    @SerializedName("user_b0_count")
    private Integer user_b0_count;
    @SerializedName("user_b1_count")
    private Integer user_b1_count;
    @SerializedName("user_b2_count")
    private Integer user_b2_count;
    @SerializedName("user_b3_count")
    private Integer user_b3_count;
    @SerializedName("user_b4_count")
    private Integer user_b4_count;
    //Non-serialzed value for use in storing each count locally.
    private Map<String, Integer> local_atrium;

    @SerializedName("user_name")
    private String user_name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public UserInfo(Integer user_info_id,
                    String user_name_of_strength,
                    Integer user_id,
                    String user_created_at,
                    Integer user_current_mood,
                    String user_current_mood_updated,
                    Integer user_current_location_lat,
                    Integer user_current_location_long,
                    String user_current_location_updated,
                    Integer user_current_butterfly,
                    Integer user_pollen,
                    Integer user_points,
                    Integer user_b0_count,
                    Integer user_b1_count,
                    Integer user_b2_count,
                    Integer user_b3_count,
                    Integer user_b4_count,
                    String username,
                    String user_name, String email,
                    String password) {
        this.user_info_id = user_info_id;
        this.user_name_of_strength = user_name_of_strength;
        this.user_id = user_id;
        this.user_created_at = user_created_at;
        this.user_current_mood = user_current_mood;
        this.user_current_mood_updated = user_current_mood_updated;
        this.user_current_location_lat = user_current_location_lat;
        this.user_current_location_long = user_current_location_long;
        this.user_current_location_updated = user_current_location_updated;
        this.user_current_butterfly = user_current_butterfly;
        this.user_pollen = user_pollen;
        this.user_points = user_points;
        this.user_b0_count = user_b0_count;
        this.user_b1_count = user_b1_count;
        this.user_b2_count = user_b2_count;
        this.user_b3_count = user_b3_count;
        this.user_b4_count = user_b4_count;

        this.user_name = user_name;
        this.email = email;
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getUser_info_id() {
        return user_info_id;
    }

    public void setUser_info_id(Integer user_info_id) {
        this.user_info_id = user_info_id;
    }

    public String getUser_name_of_strength() {
        return user_name_of_strength;
    }

    public void setUser_name_of_strength(String user_name_of_strength) {
        this.user_name_of_strength = user_name_of_strength;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_created_at() {
        return user_created_at;
    }

    public void setUser_created_at(String user_created_at) {
        this.user_created_at = user_created_at;
    }

    public Integer getUser_current_mood() {
        return user_current_mood;
    }

    public void setUser_current_mood(Integer user_current_mood) {
        this.user_current_mood = user_current_mood;
    }

    public String getUser_current_mood_updated() {
        return user_current_mood_updated;
    }

    public void setUser_current_mood_updated(String user_current_mood_updated) {
        this.user_current_mood_updated = user_current_mood_updated;
    }

    public Integer getUser_current_location_lat() {
        return user_current_location_lat;
    }

    public void setUser_current_location_lat(Integer user_current_location_lat) {
        this.user_current_location_lat = user_current_location_lat;
    }

    public Integer getUser_current_location_long() {
        return user_current_location_long;
    }

    public void setUser_current_location_long(Integer user_current_location_long) {
        this.user_current_location_long = user_current_location_long;
    }

    public String getUser_current_location_updated() {
        return user_current_location_updated;
    }

    public void setUser_current_location_updated(String user_current_location_updated) {
        this.user_current_location_updated = user_current_location_updated;
    }

    public Integer getUser_current_butterfly() {
        return user_current_butterfly;
    }

    public void setUser_current_butterfly(Integer user_current_butterfly) {
        this.user_current_butterfly = user_current_butterfly;
    }

    public Integer getUser_pollen() {
        return user_pollen;
    }

    public void setUser_pollen(Integer user_pollen) {
        this.user_pollen = user_pollen;
    }

    public Integer getUser_points() {
        return user_points;
    }

    public void setUser_points(Integer user_points) {
        this.user_points = user_points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Inventory for butterfly counts
    public Integer getUser_b0_count() {
        return user_b0_count;
    }

    public void setUser_b0_count(Integer user_b0_count) {
        this.user_b0_count = user_b0_count;
    }

    public Integer getUser_b1_count() {
        return user_b1_count;
    }

    public void setUser_b1_count(Integer user_b1_count) {
        this.user_b1_count = user_b1_count;
    }

    public Integer getUser_b2_count() {
        return user_b2_count;
    }

    public void setUser_b2_count(Integer user_b2_count) {
        this.user_b2_count = user_b2_count;
    }

    public Integer getUser_b3_count() {
        return user_b3_count;
    }

    public void setUser_b3_count(Integer user_b3_count) {
        this.user_b3_count = user_b3_count;
    }

    public Integer getUser_b4_count() {
        return user_b4_count;
    }
    //Might not be what I want to do, but good in case. Will refactor later if needed.
    public Map<String, Integer> get_local_atrium(){return local_atrium;}


    public void setUser_b4_count(Integer user_b4_count) {
        this.user_b4_count = user_b4_count;
    }

    public int get_butterflytype_count(){return local_atrium.keySet().size();}

    public void build_atrium(){
        //Init local inventory hashmap
        local_atrium = new HashMap<>();
        //Populate our local HashMap, looks ugly for now, will need to make this a map in the backend likely.
        local_atrium.put("user_b0_count", this.user_b0_count);
        local_atrium.put("user_b1_count", this.user_b1_count);
        local_atrium.put("user_b2_count", this.user_b2_count);
        local_atrium.put("user_b3_count", this.user_b3_count);
        local_atrium.put("user_b4_count", this.user_b4_count);
    }
    //This will update the local atrium mappings to be used without getting new user_info data from the backend.
    public void update_local_atrium(Map<String, Integer> atriumUpdates){
        //Refresh our local atrium with the proper counts.
        Log.d("Atrium update", "Setting local atrium to new map with keyset" + Arrays.asList(atriumUpdates));
        Iterator it = atriumUpdates.entrySet().iterator();
        for(Map.Entry<String,Integer> currEntry : atriumUpdates.entrySet()) {
            String currKey = currEntry.getKey();
            Integer currVal = currEntry.getValue();
            this.local_atrium.put(currKey, currVal);
        }
        //Log.d("USERINFO ATRIUM UPDATE", "update_local_atrium: Current UserInfo atrium: " + Arrays.asList(this.local_atrium) );
        //Reflect the local atrium mapping to the count variables stored in the local model to push to the backend.
        update_counts();
    }
    //This will take translate the atrium mappings to the userinfo counts that are pushed to the
    //backend, i.e. the user_b0_count through user_b4_count.
    public void update_counts(){
        for(Map.Entry<String,Integer> currEntry : this.local_atrium.entrySet()){
            String currKey = currEntry.getKey();
            Integer currVal = currEntry.getValue();
            switch(currKey){
                case "user_b0_count":
                    setUser_b0_count(currVal);
                    break;
                case "user_b1_count":
                    setUser_b1_count(currVal);
                    break;
                case "user_b2_count":
                    setUser_b2_count(currVal);
                    break;
                case "user_b3_count":
                    setUser_b3_count(currVal);
                    break;
                case "user_b4_count":
                    setUser_b4_count(currVal);
                    break;
            }
        }
    }
}
