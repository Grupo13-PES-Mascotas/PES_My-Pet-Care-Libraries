package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class Medication {
    private String key;
    private MedicationData body;

    /**
     * Medication constructor.
     */
    public Medication() { }

    /**
     * Medication constructor.
     * @param key Medication key
     * @param body Medication data
     */
    public Medication(String key, MedicationData body) {
        PetData.checkDatePlusNameFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Medication constructor.
     * @param date Date the medication starts
     * @param medicationName Medication name
     * @param body Medication data
     */
    public Medication(String date, String medicationName, MedicationData body) {
        PetData.checkDateFormat(date);
        this.key = date + "-" + medicationName;
        this.body = body;
    }

    /**
     * Medication constructor.
     * @param date Date the medication starts
     * @param medicationName Medication name
     * @param quantity Medication quantity
     * @param duration Medication duration
     * @param periodicity Medication periodicity
     */
    public Medication(String date, String medicationName, Double quantity, Integer duration, Integer periodicity) {
        PetData.checkDateFormat(date);
        this.key = date + "-" + medicationName;
        this.body = new MedicationData(quantity, duration, periodicity);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        PetData.checkDatePlusNameFormat(key);
        this.key = key;
    }

    /**
     * Sets the key to the concatenation of date and name.
     * @param date Date the medication starts
     * @param medicationName Medication name
     */
    public void setKey(String date, String medicationName) {
        PetData.checkDateFormat(date);
        this.key = date + "-" + medicationName;
    }

    public String getDate() {
        if (key == null) {
            return null;
        }
        return key.substring(0, 19);
    }

    public String getName() {
        if (key == null) {
            return null;
        }
        return key.substring(20);
    }

    public MedicationData getBody() {
        return body;
    }

    public void setBody(MedicationData body) {
        this.body = body;
    }

    /**
     * Turns the body into a Map of key String and element Object.
     * @return Body turned into map
     */
    public Map<String, Object> getBodyAsMap() {
        return body.getAsMap();
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "key='" + key + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Medication) {
            return ((Medication) obj).getKey().equals(this.getKey())
                && ((Medication) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
