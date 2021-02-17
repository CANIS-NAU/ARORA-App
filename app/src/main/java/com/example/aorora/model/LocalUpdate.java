package com.example.aorora.model;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

public class LocalUpdate implements Serializable {
    @SerializedName("pollenScore")
    private Integer pollenScore;
    //private Map<String, Integer> userAtrium;

    public LocalUpdate(int inputPollen){
        this.pollenScore = inputPollen;
    }

    @Override
    public String toString() {
        return "LocalUpdate{" +
                "pollenScore=" + pollenScore +
                '}';
    }
}
