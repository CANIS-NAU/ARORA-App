package com.example.aorora.model;

import com.google.gson.annotations.SerializedName;

public class SuperflyInvite {
    @SerializedName("invite_id")
    Integer invite_id;
    @SerializedName("session")
    SuperflySession session;
    @SerializedName("recipient")
    Integer recipient;
    @SerializedName("accepted")
    Boolean accepted;

    public SuperflyInvite(Integer invite_id, SuperflySession session, Integer recipient, Boolean accepted) {
        this.invite_id = invite_id;
        this.session = session;
        this.recipient = recipient;
        this.accepted = accepted;
    }

    public Integer getInvite_id() {
        return invite_id;
    }

    public void setInvite_id(Integer invite_id) {
        this.invite_id = invite_id;
    }

    public SuperflySession getSession() {
        return session;
    }

    public void setSession(SuperflySession session) {
        this.session = session;
    }

    public Integer getRecipient() {
        return recipient;
    }

    public void setRecipient(Integer recipient) {
        this.recipient = recipient;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
