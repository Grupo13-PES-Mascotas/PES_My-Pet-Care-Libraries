package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class WeightData {
    private Integer value;

    /**
     * WeigthData constructor
     */
    public WeightData() { }

    /**
     * WeightData constructor
     * @param weight new Weight value
     */
    public WeightData(Integer weight) {
        this.value = weight;
    }

    /**
     * Retrieve weight value
     * @return weight value
     */
    public Integer getWeight() {
        return value;
    }

    /**
     * Set weight value
     * @param weight weight value
     */
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
