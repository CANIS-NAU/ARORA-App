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
    @SerializedName("session_started")
    private Boolean session_started;

    //Participants in the superfly session.
    @SerializedName("participant_0")
    private Integer participant_0;
    @SerializedName("participant_1")
    private Integer participant_1;
    @SerializedName("participant_2")
    private Integer participant_2;
    @SerializedName("participant_3")
    private Integer participant_3;
    @SerializedName("participant_4")
    private Integer participant_4;



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
                           Boolean session_started, Integer participant_0, Integer participant_1,
                           Integer participant_2, Integer participant_3, Integer participant_4,
                           Superfly superfly_recipe, Integer current_b0_count, Integer current_b1_count,
                           Integer current_b2_count, Integer current_b3_count, Integer current_b4_count) {
        this.session_id = session_id;
        this.session_start_date = session_start_date;
        this.session_participant_count = session_participant_count;
        this.session_started = session_started;
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


    @Override
    public String toString() {
        return "SuperflySession{" +
                "session_id=" + session_id +
                ", participant_0=" + participant_0 +
                ", participant_1=" + participant_1 +
                ", participant_2=" + participant_2 +
                ", participant_3=" + participant_3 +
                ", participant_4=" + participant_4 +
                ", superfly_recipe=" + superfly_recipe +
                ", current_b0_count=" + current_b0_count +
                ", current_b1_count=" + current_b1_count +
                ", current_b2_count=" + current_b2_count +
                ", current_b3_count=" + current_b3_count +
                ", current_b4_count=" + current_b4_count +
                '}';
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

    public Boolean getSession_started() {
        return session_started;
    }

    public void setSession_started(Boolean session_started) {
        this.session_started = session_started;
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

    public Integer getParticipant_0() {
        return participant_0;
    }

    public void setParticipant_0(Integer participant_0) {
        this.participant_0 = participant_0;
    }

    public Integer getParticipant_1() {
        return participant_1;
    }

    public void setParticipant_1(Integer participant_1) {
        this.participant_1 = participant_1;
    }

    public Integer getParticipant_2() {
        return participant_2;
    }

    public void setParticipant_2(Integer participant_2) {
        this.participant_2 = participant_2;
    }

    public Integer getParticipant_3() {
        return participant_3;
    }

    public void setParticipant_3(Integer participant_3) {
        this.participant_3 = participant_3;
    }

    public Integer getParticipant_4() {
        return participant_4;
    }

    public void setParticipant_4(Integer participant_4) {
        this.participant_4 = participant_4;
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
