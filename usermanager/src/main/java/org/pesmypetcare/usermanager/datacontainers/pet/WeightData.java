package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeightData {
    private Double weight;

    public WeightData(double weight) {
        this.weight = weight;
    }

    /**
     * Returns the value of the weight.
     * @return value of the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Creates a weight json object.
     * @return A JSON Object with the weight data
     */
    public JSONObject buildWeightJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("weight", Double.toString(weight));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", weight=" + weight
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof WeightData) {
            return ((WeightData) obj).getWeight().equals(this.getWeight());
        }
        return false;
    }
}
