package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class WeightData {
    private Integer value;

    /**
     * WeigthData constructor.
     */
    public WeightData() { }

    /**
     * WeightData constructor.
     * @param weight new Weight value
     */
    public WeightData(Integer weight) {
        this.value = weight;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer weight) {
        this.value = weight;
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

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "weight = " + value
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
