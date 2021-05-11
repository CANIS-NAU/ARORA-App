package com.example.aorora.model;

import com.google.gson.annotations.SerializedName;

public class Superfly {
    @SerializedName("superfly_id")
    private Integer superfly_id;
    @SerializedName("superfly_name")
    private String superfly_name;
    @SerializedName("superfly_photo")
    private String superfly_photo;
    @SerializedName("b0_count")
    private Integer b0_count;
    @SerializedName("b1_count")
    private Integer b1_count;
    @SerializedName("b2_count")
    private Integer b2_count;
    @SerializedName("b3_count")
    private Integer b3_count;
    @SerializedName("b4_count")
    private Integer b4_count;


    public Superfly(Integer superfly_id, String superfly_name, String superfly_photo, Integer b0_count, Integer b1_count, Integer b2_count, Integer b3_count, Integer b4_count) {
        this.superfly_id = superfly_id;
        this.superfly_name = superfly_name;
        this.superfly_photo = superfly_photo;
        this.b0_count = b0_count;
        this.b1_count = b1_count;
        this.b2_count = b2_count;
        this.b3_count = b3_count;
        this.b4_count = b4_count;
    }

    @Override
    public String toString() {
        return "Superfly{" +
                "superfly_id=" + superfly_id +
                ", superfly_name='" + superfly_name + '\'' +
                ", superfly_photo='" + superfly_photo + '\'' +
                ", b0_count=" + b0_count +
                ", b1_count=" + b1_count +
                ", b2_count=" + b2_count +
                ", b3_count=" + b3_count +
                ", b4_count=" + b4_count +
                '}';
    }

    public Integer getSuperfly_id() {
        return superfly_id;
    }

    public void setSuperfly_id(Integer superfly_id) {
        this.superfly_id = superfly_id;
    }

    public String getSuperfly_name() {
        return superfly_name;
    }

    public void setSuperfly_name(String superfly_name) {
        this.superfly_name = superfly_name;
    }

    public String getSuperfly_photo() {
        return superfly_photo;
    }

    public void setSuperfly_photo(String superfly_photo) {
        this.superfly_photo = superfly_photo;
    }

    public Integer getB0_count() {
        return b0_count;
    }

    public void setB0_count(Integer b0_count) {
        this.b0_count = b0_count;
    }

    public Integer getB1_count() {
        return b1_count;
    }

    public void setB1_count(Integer b1_count) {
        this.b1_count = b1_count;
    }

    public Integer getB2_count() {
        return b2_count;
    }

    public void setB2_count(Integer b2_count) {
        this.b2_count = b2_count;
    }

    public Integer getB3_count() {
        return b3_count;
    }

    public void setB3_count(Integer b3_count) {
        this.b3_count = b3_count;
    }

    public Integer getB4_count() {
        return b4_count;
    }

    public void setB4_count(Integer b4_count) {
        this.b4_count = b4_count;
    }





}
