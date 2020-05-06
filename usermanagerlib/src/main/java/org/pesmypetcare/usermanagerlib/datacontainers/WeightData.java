package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class WeightData {
    private Integer weight;

    public WeightData() { }

    public WeightData(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + " weight = " + weight
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
