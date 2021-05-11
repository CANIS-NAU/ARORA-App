package com.example.aorora.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/*This is an instance of the superfly creation game. This groups users together to create
* superflies by contributing to the current butterfly counts until the recipe is made.*/
public class SuperflySession {
    //Id of the session
    @SerializedName("session_id")
    private Integer session_id;
    @SerializedName("session_start_date")
    private String session_start_date;
    @SerializedName("session_participant_count")
    private Integer session_participant_count;


    //Participants in the superfly session.
    @SerializedName("participant_0")
    private UserInfo participant_0;
    @SerializedName("participant_1")
    private UserInfo participant_1;
    @SerializedName("participant_2")
    private UserInfo participant_2;
    @SerializedName("participant_3")
    private UserInfo participant_3;
    @SerializedName("participant_4")
    private UserInfo participant_4;



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


    public SuperflySession(Integer session_id, String session_start_date, Integer session_participant_count,
                           UserInfo participant_0, UserInfo participant_1, UserInfo participant_2,
                           UserInfo participant_3, UserInfo participant_4, Superfly superfly_recipe,
                           Integer current_b0_count, Integer current_b1_count, Integer current_b2_count,
                           Integer current_b3_count, Integer current_b4_count ) {
        this.session_id = session_id;
        this.session_start_date = session_start_date;
        this.session_participant_count = session_participant_count;
        this.participant_0 = participant_0;
        this.participant_1 = participant_1;
        this.participant_2 = participant_2;
        this.participant_3 = participant_3;
        this.participant_4 = participant_4;
        this.superfly_recipe = superfly_recipe;
        this.current_b0_count = current_b0_count;
        this.current_b1_count = current_b1_count;
        this.current_b2_count = current_b2_count;
        this.current_b3_count = current_b3_count;
        this.current_b4_count = current_b4_count;
    }



    //Prints the id and all counts
    //TODO: Print other participants and their info.
    @NonNull
    @Override
    public String toString() {
        return "session_id: " + session_id + " current_b0: " + current_b0_count +  " current_b1: " + current_b1_count +
                " current_b2: " + current_b2_count + " current_b3: " + current_b3_count
                + " current_b4: " + current_b4_count + "\n Superfly recipe: " + superfly_recipe.toString();
    }

    public Integer getSession_id() {
        return session_id;
    }

    public void setSession_id(Integer session_id) {
        this.session_id = session_id;
    }

    public String getSession_start_date() {
        return session_start_date;
    }

    public void setSession_start_date(String session_start_date) {
        this.session_start_date = session_start_date;
    }

    public Integer getSession_participant_count() {
        return session_participant_count;
    }

    public void setSession_participant_count(Integer session_participant_count) {
        this.session_participant_count = session_participant_count;
    }

    public UserInfo getParticipant_0() {
        return participant_0;
    }

    public void setParticipant_0(UserInfo participant_0) {
        this.participant_0 = participant_0;
    }

    public UserInfo getParticipant_1() {
        return participant_1;
    }

    public void setParticipant_1(UserInfo participant_1) {
        this.participant_1 = participant_1;
    }

    public UserInfo getParticipant_2() {
        return participant_2;
    }

    public void setParticipant_2(UserInfo participant_2) {
        this.participant_2 = participant_2;
    }

    public UserInfo getParticipant_3() {
        return participant_3;
    }

    public void setParticipant_3(UserInfo participant_3) {
        this.participant_3 = participant_3;
    }

    public UserInfo getParticipant_4() {
        return participant_4;
    }

    public void setParticipant_4(UserInfo participant_4) {
        this.participant_4 = participant_4;
    }

    public Superfly getSuperfly_recipe() {
        return superfly_recipe;
    }

    public void setSuperfly_recipe(Superfly superfly_recipe) {
        this.superfly_recipe = superfly_recipe;
    }

    public Integer getCurrent_b0_count() {
        return current_b0_count;
    }

    public void setCurrent_b0_count(Integer current_b0_count) {
        this.current_b0_count = current_b0_count;
    }

    public Integer getCurrent_b1_count() {
        return current_b1_count;
    }

    public void setCurrent_b1_count(Integer current_b1_count) {
        this.current_b1_count = current_b1_count;
    }

    public Integer getCurrent_b2_count() {
        return current_b2_count;
    }

    public void setCurrent_b2_count(Integer current_b2_count) {
        this.current_b2_count = current_b2_count;
    }

    public Integer getCurrent_b3_count() {
        return current_b3_count;
    }

    public void setCurrent_b3_count(Integer current_b3_count) {
        this.current_b3_count = current_b3_count;
    }

    public Integer getCurrent_b4_count() {
        return current_b4_count;
    }

    public void setCurrent_b4_count(Integer current_b4_count) {
        this.current_b4_count = current_b4_count;
    }

}
