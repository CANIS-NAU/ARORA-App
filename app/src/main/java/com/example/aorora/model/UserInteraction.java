package com.example.aorora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInteraction
{
    @SerializedName("user_interaction_id")
    private Integer user_interaction_id;
    @SerializedName("user_interaction_type_id")
    private Integer user_interaction_type_id;
    @SerializedName("initiator_user_id")
    private Integer initiator_user_id;
    @SerializedName("receiver_user_id")
    private Integer receiver_user_id;
    @SerializedName("quest_record_id")
    private Integer quest_record_id;
    @SerializedName("user_interaction_content")
    private String user_interaction_content;
    @SerializedName("user_interaction_created_at")
    private String user_interaction_created_at;

    public UserInteraction(Integer user_interaction_id, Integer user_interaction_type_id,
                           Integer initiator_user_id, Integer receiver_user_id, Integer quest_record_id,
                           String user_interaction_content, String user_interaction_created_at)
    {
        this.user_interaction_id = user_interaction_id;
        this.user_interaction_type_id = user_interaction_type_id;
        this.initiator_user_id = initiator_user_id;
        this.receiver_user_id = receiver_user_id;
        this.quest_record_id = quest_record_id;
        this.user_interaction_content = user_interaction_content;
        this.user_interaction_created_at = user_interaction_created_at;
    }

    public Integer getUser_interaction_id() {return user_interaction_id;}
    public Integer getUser_interaction_type_id() {return user_interaction_type_id;}
    public Integer getUser_initiator_id() {return initiator_user_id;}
    public Integer getUser_receiver_id() {return receiver_user_id;}
    public Integer getQuest_record_id() {return quest_record_id;}
    public String getUser_interaction_content() {return user_interaction_content;}
    public String getUser_interaction_created_at() {return user_interaction_created_at;}

    public void setUser_interaction_id(Integer user_interaction_id)
    {
        this.user_interaction_id = user_interaction_id;
    }

    public void setUser_interaction_type_id(Integer user_interaction_type_id)
    {
        this.user_interaction_type_id = user_interaction_type_id;
    }

    public void setUser_initiator_id(Integer initiator_user_id)
    {
        this.initiator_user_id = initiator_user_id;
    }

    public void setUser_receiver_id(Integer receiver_user_id)
    {
        this.receiver_user_id = receiver_user_id;
    }

    public void setQuest_record_id(Integer quest_record_id)
    {
        this.quest_record_id = quest_record_id;
    }

    public void setUser_interaction_content(String user_interaction_content)
    {
        this.user_interaction_content = user_interaction_content;
    }

    public void setUser_interaction_created_at(String user_interaction_created_at)
    {
        this.user_interaction_created_at = user_interaction_created_at;
    }


}
