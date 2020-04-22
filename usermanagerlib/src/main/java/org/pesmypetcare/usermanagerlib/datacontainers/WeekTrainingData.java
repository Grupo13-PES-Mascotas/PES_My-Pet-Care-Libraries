package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeekTrainingData {
    private Double weekTraining;

    public WeekTrainingData(double weekTraining) {
        this.weekTraining = weekTraining;
    }

    /**
     * Returns the value of the weekTraining.
     * @return value of the weekTraining
     */
    public Double getWeekTraining() {
        return weekTraining;
    }

    /**
     * Creates a weekTraining json object.
     * @return A JSON Object with the weekTraining data
     */
    public JSONObject buildWeekTrainingJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("weekTraining", Double.toString(weekTraining));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", weekTraining=" + weekTraining
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof WeekTrainingData) {
            return ((WeekTrainingData) obj).getWeekTraining().equals(this.getWeekTraining());
        }
        return false;
    }
}
