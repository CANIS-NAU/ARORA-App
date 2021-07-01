package com.example.aorora.model;

import com.google.gson.annotations.SerializedName;

public class UserSuperfly {
    @SerializedName("user_superfly_id")
    private Integer user_superfly_id;
    @SerializedName("id_user")
    private Integer id_user;
    @SerializedName("id_superfly")
    private Integer id_superfly;
    @SerializedName("user")
    private UserInfo user;
    @SerializedName("superfly")
    private Superfly superfly;


    public UserSuperfly(Integer user_superfly_id, Integer id_user, Integer id_superfly,
                            UserInfo user, Superfly superfly) {
        this.user_superfly_id = user_superfly_id;
        this.id_user = id_user;
        this.id_superfly = id_superfly;
        this.user = user;
        this.superfly = superfly;
    }

    @Override
    public String toString() {
        return "UserSuperfly{" +
                "user_superfly_id=" + user_superfly_id +
                ", id_user=" + id_user +
                ", id_superfly=" + id_superfly +
                '}';
    }


    public Integer getUser_superfly_id() {
        return user_superfly_id;
    }

    public void setUser_superfly_id(Integer user_superfly_id) {
        this.user_superfly_id = user_superfly_id;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_superfly() {
        return id_superfly;
    }

    public void setId_superfly(Integer id_superfly) {
        this.id_superfly = id_superfly;
    }
}
