package com.example.aorora.model;

import com.google.gson.annotations.SerializedName;

/*This is an instance of the superfly creation game. This groups users together to create
* superflies by contributing to the current butterfly counts until the recipe is made.*/
public class SuperflySession {
    //Participants in the superfly session.
    @SerializedName("participant_1")
    private UserInfo participant_1;
    @SerializedName("participant_2")
    private UserInfo participant_2;
    @SerializedName("participant_3")
    private UserInfo participant_3;
    @SerializedName("participant_4")
    private UserInfo participant_4;
    @SerializedName("participant_5")
    private UserInfo participant_5;
    @SerializedName("superfly_recipe")
    private Superfly superfly_recipe;

    //Current progress and counts of butterflies for the recipe.
    @SerializedName("current_b0_count")
    private Integer current_b0_count;
    @SerializedName("current_b1_count")
    private Integer current_b1_count;
    @SerializedName("current_b2_count")
    private Integer current_b2_count;
    @SerializedName("current_b3_count")
    private Integer current_b3_count;
    @SerializedName("current_b4_count")
    private Integer current_b4_count;



}
