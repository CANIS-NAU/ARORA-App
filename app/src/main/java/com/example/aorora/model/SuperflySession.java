package com.example.aorora.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
    @SerializedName("session_ended")
    private Boolean session_ended;

    //Ids for participants in the session
    @SerializedName("id_0")
    private Integer id_0;
    @SerializedName("id_1")
    private Integer id_1;
    @SerializedName("id_2")
    private Integer id_2;
    @SerializedName("id_3")
    private Integer id_3;
    @SerializedName("id_4")
    private Integer id_4;

    //Participants in the superfly session.
    @SerializedName("participant_0")
    public UserInfo participant_0;
    @SerializedName("participant_1")
    public UserInfo participant_1;
    @SerializedName("participant_2")
    public UserInfo participant_2;
    @SerializedName("participant_3")
    public UserInfo participant_3;
    @SerializedName("participant_4")
    public UserInfo participant_4;



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

    @SerializedName("butterfly_participant_0")
    private Integer butterfly_participant_0;
    @SerializedName("butterfly_participant_1")
    private Integer butterfly_participant_1;
    @SerializedName("butterfly_participant_2")
    private Integer butterfly_participant_2;
    @SerializedName("butterfly_participant_3")
    private Integer butterfly_participant_3;
    @SerializedName("butterfly_participant_4")
    private Integer butterfly_participant_4;

    //Locally initialized variables
    Integer[] assignedButterflies;
    UserInfo[] participantsArray;


    public SuperflySession(Integer session_id, String session_start_date,
                           Integer session_participant_count, Boolean session_started,
                           Boolean session_ended, Integer id_0, Integer id_1, Integer id_2, Integer id_3, Integer id_4,
                           UserInfo participant_0, UserInfo participant_1, UserInfo participant_2,
                           UserInfo participant_3, UserInfo participant_4, Superfly superfly_recipe,
                           Integer current_b0_count, Integer current_b1_count,
                           Integer current_b2_count, Integer current_b3_count, Integer current_b4_count,
                           Integer butterfly_participant_0, Integer butterfly_participant_1,
                           Integer butterfly_participant_2, Integer butterfly_participant_3, Integer butterfly_participant_4) {
        this.session_id = session_id;
        this.session_start_date = session_start_date;
        this.session_participant_count = session_participant_count;
        this.session_started = session_started;
        this.session_ended = session_ended;
        this.id_0 = id_0;
        this.id_1 = id_1;
        this.id_2 = id_2;
        this.id_3 = id_3;
        this.id_4 = id_4;
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
        this.butterfly_participant_0 = butterfly_participant_0;
        this.butterfly_participant_1 = butterfly_participant_1;
        this.butterfly_participant_2 = butterfly_participant_2;
        this.butterfly_participant_3 = butterfly_participant_3;
        this.butterfly_participant_4 = butterfly_participant_4;
        buildParticipantsArray();
    }

    //Gets the next set of needed butterflies for the session.
    public UserInfo[] buildParticipantsArray() {
        Log.d("getParticipantsArray", "Creating array");
        int numParticipants = this.getSession_participant_count();
        participantsArray = new UserInfo[numParticipants];
        Log.d("Current participant numbers", this.getSession_participant_count().toString());
        for(int i = 0; i < numParticipants; i++){
            //Get the actual string name of the method.
            String getterName = "getParticipant_" + i;

            try {
                //Since we found the method, invoke it
                Method method = SuperflySession.class.getMethod(getterName);
                Object currentParticipant = method.invoke(this);
                participantsArray[i] = (UserInfo) currentParticipant;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.d("getNextRound", "No method found");
                e.printStackTrace();
            }

        }
        return this.participantsArray;
    }

    //Gets the next set of needed butterflies for the session.
    public Integer[] buildAssignedButterfliesArray() {
        Log.d("getParticipantsArray", "Creating array");
        assignedButterflies = new Integer[session_participant_count];
        int numParticipants = this.getSession_participant_count();
        Log.d("Current part numbers", this.getSession_participant_count().toString());
        for(int i = 0; i < numParticipants; i++){
            String getterName = "getButterfly_participant_" + i;
            Log.d("Participant id", getterName);
            try {
                Method method = SuperflySession.class.getMethod(getterName);
                Object currParticipantButterfly = method.invoke(this);
                assignedButterflies[i] = (Integer) currParticipantButterfly;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.d("getNextRound", "No method found");
                e.printStackTrace();
            }
        }
        return this.assignedButterflies;
    }

    //Gets the next set of needed butterflies for the session.
    public void updateButterflyCount(int butterflyType, int butterflyCount) {
        String getterName = "getCurrent_b" + butterflyType + "_count";
        String setterName = "setCurrent_b" + butterflyType + "_count";
        Log.d("Participant id", setterName);
        try {
            Method getterMethod = SuperflySession.class.getMethod(getterName);
            Method setterMethod = SuperflySession.class.getMethod(setterName);
            Object prevCountObj = getterMethod.invoke(this );
            Integer prevCount = (Integer) prevCountObj;
            setterMethod.invoke(this, prevCount + butterflyCount);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.d("getNextRound", "No method found");
            e.printStackTrace();
        }

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

    public Boolean getSession_ended() {
        return session_ended;
    }

    public void setSession_ended(Boolean session_ended) {
        this.session_ended = session_ended;
    }

    public Superfly getSuperfly_recipe() {
        return superfly_recipe;
    }

    public void setSuperfly_recipe(Superfly superfly_recipe) {
        this.superfly_recipe = superfly_recipe;
    }

    public Integer getId_0() {
        return id_0;
    }

    public void setId_0(Integer id_0) {
        this.id_0 = id_0;
    }

    public Integer getId_1() {
        return id_1;
    }

    public void setId_1(Integer id_1) {
        this.id_1 = id_1;
    }

    public Integer getId_2() {
        return id_2;
    }

    public void setId_2(Integer id_2) {
        this.id_2 = id_2;
    }

    public Integer getId_3() {
        return id_3;
    }

    public void setId_3(Integer id_3) {
        this.id_3 = id_3;
    }

    public Integer getId_4() {
        return id_4;
    }

    public void setId_4(Integer id_4) {
        this.id_4 = id_4;
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

    public Integer getButterfly_participant_0() {
        return butterfly_participant_0;
    }

    public UserInfo[] getParticipantsArray() {
        return participantsArray;
    }

    public Integer[] getAssignedButterflies() {
        return assignedButterflies;
    }

    public void setButterfly_participant_0(Integer butterfly_participant_0) {
        this.butterfly_participant_0 = butterfly_participant_0;
    }

    public Integer getButterfly_participant_1() {
        return butterfly_participant_1;
    }

    public void setButterfly_participant_1(Integer butterfly_participant_1) {
        this.butterfly_participant_1 = butterfly_participant_1;
    }

    public Integer getButterfly_participant_2() {
        return butterfly_participant_2;
    }

    public void setButterfly_participant_2(Integer butterfly_participant_2) {
        this.butterfly_participant_2 = butterfly_participant_2;
    }

    public Integer getButterfly_participant_3() {
        return butterfly_participant_3;
    }

    public void setButterfly_participant_3(Integer butterfly_participant_3) {
        this.butterfly_participant_3 = butterfly_participant_3;
    }

    public Integer getButterfly_participant_4() {
        return butterfly_participant_4;
    }

    public void setButterfly_participant_4(Integer butterfly_participant_4) {
        this.butterfly_participant_4 = butterfly_participant_4;
    }
}
