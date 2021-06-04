package com.example.aorora.model;

import com.google.gson.annotations.SerializedName;

public class SuperflyInvite {
    @SerializedName("invite_id")
    Integer invite_id;
    @SerializedName("session")
    SuperflySession session;
    @SerializedName("recipient")
    Integer recipient;
    @SerializedName("uid_recipient")
    Integer uid_recipient;
    @SerializedName("uid_sender")
    Integer uid_sender;
    @SerializedName("accepted")
    Boolean accepted;

    public SuperflyInvite(Integer invite_id, SuperflySession session, Integer recipient, Integer uid_recipient, Integer uid_sender, Boolean accepted) {
        this.invite_id = invite_id;
        this.session = session;
        this.recipient = recipient;
        this.uid_recipient = uid_recipient;
        this.uid_sender = uid_sender;
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "SuperflyInvite{" +
                "invite_id=" + invite_id +
                ", session=" + session +
                ", recipient=" + recipient +
                ", uid_recipient=" + uid_recipient +
                ", uid_sender=" + uid_sender +
                ", accepted=" + accepted +
                '}';
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

    public Integer getUid_recipient() { return uid_recipient; }

    public void setUid_recipient(Integer uid_recipient) { this.uid_recipient = uid_recipient; }

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
