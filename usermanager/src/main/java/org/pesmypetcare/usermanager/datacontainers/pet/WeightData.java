package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeightData {
    private Double value;

    public WeightData(double value) {
        this.value = value;
    }

    /**
     * Returns the value of the weight.
     * @return value of the weight
     */
    public Double getValue() {
        return value;
    }

    /**
     * Turns the WeightData into a Map of key String and element Object.
     * @return WeighData turned into map
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("value", value);
        return response;
    }

    /**
     * Creates a weight json object.
     * @return A JSON Object with the weight data
     */
    public JSONObject buildWeightJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("weight", Double.toString(value));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
                + ", weight=" + value
                + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof WeightData) {
            return ((WeightData) obj).getValue().equals(this.getValue());
        }
        return false;
    }
}
