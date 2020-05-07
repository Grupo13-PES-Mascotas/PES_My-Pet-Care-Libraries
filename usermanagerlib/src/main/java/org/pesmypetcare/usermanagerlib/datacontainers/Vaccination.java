package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class Vaccination {
    private String key;
    private VaccinationData body;

    /**
     * Vaccination constructor.
     */
    public Vaccination() { }

    /**
     * Vaccination constructor.
     * @param key date
     * @param body vaccination data
     */
    public Vaccination(String key, VaccinationData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Vaccination constructor.
     * @param date Vaccination date
     * @param description Vaccination description
     */
    public Vaccination(String date, String description) {
        PetData.checkDateFormat(date);
        this.key = date;
        this.body = new VaccinationData(description);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String date) {
        PetData.checkDateFormat(key);
        this.key = date;
    }

    public VaccinationData getBody() {
        return body;
    }

    public void setBody(VaccinationData body) {
        this.body = body;
    }

    /**
     * Turns the body into a Map<String, Object>.
     * @return Map<String, Object> containing the body
     */
    public Map<String, Object> getBodyAsMap() {
        return body.getAsMap();
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "date='" + key + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Vaccination) {
            return ((Vaccination) obj).getKey().equals(this.getKey())
                && ((Vaccination) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
