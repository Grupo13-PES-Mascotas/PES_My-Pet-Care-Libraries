package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class MedicationData {
    private Double quantity;
    private Integer duration;
    private Integer periodicity;

    /**
     * MedicationData constructor.
     */
    public MedicationData() { }

    /**
     * MedicationData constructor.
     * @param quantity Quantity to be taken
     * @param duration Duration of the medication
     * @param periodicity Interval time between dose
     */
    public MedicationData(Double quantity, Integer duration, Integer periodicity) {
        this.quantity = quantity;
        this.duration = duration;
        this.periodicity = periodicity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    /**
     * Turns the MedicationData into a Map of key String and element Object.
     * @return MedicationData turned into map
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("quantity", quantity);
        response.put("duration", duration);
        response.put("periodicity", periodicity);
        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "quantity=" + quantity
            + ", duration=" + duration
            + ", periodicity=" + periodicity
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MedicationData) {
            return (((MedicationData) obj).getQuantity().equals(this.getQuantity())
                && ((MedicationData) obj).getDuration().equals(this.getDuration())
                && ((MedicationData) obj).getPeriodicity().equals(this.getPeriodicity()));
        }
        return false;
    }
}
