package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class WeightData {
    private Integer value;

    public WeightData() { }

    public WeightData(Integer weight) {
        this.value = weight;
    }

    public Integer getWeight() {
        return value;
    }

    public void setWeight(Integer weight) {
        this.value = weight;
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
            return ((WeightData) obj).getWeight().equals(this.getWeight());
        }
        return false;
    }
}
