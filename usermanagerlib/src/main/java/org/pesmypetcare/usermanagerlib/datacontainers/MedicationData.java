package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MedicationData {
    private double quantity;
    private int duration;
    private int periodicity;

    public MedicationData(double inQuantity, int inDuration, int inPeriodicity) {
        this.quantity = inQuantity;
        this.duration = inDuration;
        this.periodicity = inPeriodicity;
    }

    /**
     * Returns the quantity.
     * @return Quantity of the medication.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets a new quantity.
     * @param inQuantity New meal name.
     */
    public void setMedicationQuantity(double inQuantity) {
        this.quantity = inQuantity;
    }

    /**
     * Returns the number of days of this medication.
     * @return Amount of days.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Sets a new amount of days of this medication.
     * @param inDuration Amount of kcalories.
     */
    public void setDuration(int inDuration) {
        this.duration = inDuration;
    }


    /**
     * Returns the number of times this medication needs to be administered per day.
     * @return New periodicity.
     */
    public int getPeriodicity() {
        return this.periodicity;
    }

    /**
     * Sets a new periodicity for the medication.
     * @param inPeriodicity New periodicity.
     */
    public void setPeriodicity(int inPeriodicity) {
        this.periodicity = inPeriodicity;
    }



    /**
     * Creates a meal json object.
     * @return A JSON Object with the meal data.
     */
    public JSONObject buildMedicationJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("quantity", String.valueOf(quantity));
        reqData.put("duration", String.valueOf(duration));
        reqData.put("periodicity", String.valueOf(periodicity));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
                + "quantity=" + quantity + '\''
                + ", duration=" + duration + '\''
                + ", periodicity=" + periodicity
                + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MedicationData) {
            return ((MedicationData) obj).getQuantity() == this.getQuantity()
                    && ((MedicationData) obj).getDuration() == this.getDuration()
                    && ((MedicationData) obj).getPeriodicity() == this.getPeriodicity();
        }
        return false;
    }
}
