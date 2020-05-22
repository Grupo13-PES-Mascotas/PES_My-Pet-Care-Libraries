package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class MedicationData {
    private static final String DURATION = "duration";
    private static final String QUANTITY = "quantity";
    private static final String PERIODICITY = "periodicity";
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
        response.put(QUANTITY, quantity);
        response.put(DURATION, duration);
        response.put(PERIODICITY, periodicity);
        return response;
    }

    /**
     * Creates a meal json object.
     * @return A JSON Object with the meal data.
     */
    public JSONObject buildMedicationJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(QUANTITY, String.valueOf(quantity));
        reqData.put(DURATION, String.valueOf(duration));
        reqData.put(PERIODICITY, String.valueOf(periodicity));
        return new JSONObject(reqData);
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
            return ((MedicationData) obj).getQuantity().equals(this.getQuantity())
                && ((MedicationData) obj).getDuration().equals(this.getDuration())
                && ((MedicationData) obj).getPeriodicity().equals(this.getPeriodicity());
        }
        return false;
    }
}
