package com.example.aorora.model;
import com.example.aorora.MainActivity;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

/*This class houses the updated values that are to be serialized to the local json file for when
the user loses connection when trying to push values to the backend. If more values are desired to
be saved, add them to this class so they can be serialized.
 */
public class LocalUpdate implements Serializable {
    @SerializedName("pollenScore")
    private Integer pollenScore;
    @SerializedName("userAtrium")
    private Map<String, Integer> userAtrium;

    public LocalUpdate(){
        this.pollenScore = MainActivity.user_info.getUser_pollen();
        this.userAtrium = MainActivity.user_info.get_local_atrium();
    }

    public Integer getPollenScore() {
        return pollenScore;
    }

    public Map<String, Integer> getUserAtrium() {
        return userAtrium;
    }

    @Override
    public String toString() {
        return "LocalUpdate{" +
                "pollenScore=" + pollenScore +
                "UserAtrium=" + userAtrium.toString() +
                '}';
    }
}
