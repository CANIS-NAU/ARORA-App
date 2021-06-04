package com.example.aorora.model;

import com.google.gson.annotations.SerializedName;

public class TradeRequest {
    @SerializedName("request_id")
    Integer request_id;
    @SerializedName("uid_sender")
    Integer uid_sender;
    @SerializedName("uid_recipient")
    Integer uid_recipient;
    @SerializedName("sender")
    UserInfo sender;
    @SerializedName("recipient")
    UserInfo recipient;
    @SerializedName("b0_requested")
    Integer b0_requested;
    @SerializedName("b1_requested")
    Integer b1_requested;
    @SerializedName("b2_requested")
    Integer b2_requested;
    @SerializedName("b3_requested")
    Integer b3_requested;
    @SerializedName("b4_requested")
    Integer b4_requested;

    public TradeRequest(Integer request_id, Integer uid_sender, Integer uid_recipient,
                        UserInfo sender, UserInfo recipient, Integer b0_requested,
                        Integer b1_requested, Integer b2_requested, Integer b3_requested,
                        Integer b4_requested) {
        this.request_id = request_id;
        this.uid_sender = uid_sender;
        this.uid_recipient = uid_recipient;
        this.sender = sender;
        this.recipient = recipient;
        this.b0_requested = b0_requested;
        this.b1_requested = b1_requested;
        this.b2_requested = b2_requested;
        this.b3_requested = b3_requested;
        this.b4_requested = b4_requested;
    }

    @Override
    public String toString() {
        return "TradeRequest{" +
                "request_id=" + request_id +
                ", uid_sender=" + uid_sender +
                ", uid_recipient=" + uid_recipient +
                ", b0_requested=" + b0_requested +
                ", b1_requested=" + b1_requested +
                ", b2_requested=" + b2_requested +
                ", b3_requested=" + b3_requested +
                ", b4_requested=" + b4_requested +
                '}';
    }

    public Integer getRequest_id() {
        return request_id;
    }

    public void setRequest_id(Integer request_id) {
        this.request_id = request_id;
    }

    public Integer getUid_sender() {
        return uid_sender;
    }

    public void setUid_sender(Integer uid_sender) {
        this.uid_sender = uid_sender;
    }

    public Integer getUid_recipient() {
        return uid_recipient;
    }

    public void setUid_recipient(Integer uid_recipient) {
        this.uid_recipient = uid_recipient;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public UserInfo getRecipient() {
        return recipient;
    }

    public void setRecipient(UserInfo recipient) {
        this.recipient = recipient;
    }

    public Integer getB0_requested() {
        return b0_requested;
    }

    public void setB0_requested(Integer b0_requested) {
        this.b0_requested = b0_requested;
    }

    public Integer getB1_requested() {
        return b1_requested;
    }

    public void setB1_requested(Integer b1_requested) {
        this.b1_requested = b1_requested;
    }

    public Integer getB2_requested() {
        return b2_requested;
    }

    public void setB2_requested(Integer b2_requested) {
        this.b2_requested = b2_requested;
    }

    public Integer getB3_requested() {
        return b3_requested;
    }

    public void setB3_requested(Integer b3_requested) {
        this.b3_requested = b3_requested;
    }

    public Integer getB4_requested() {
        return b4_requested;
    }

    public void setB4_requested(Integer b4_requested) {
        this.b4_requested = b4_requested;
    }
}
