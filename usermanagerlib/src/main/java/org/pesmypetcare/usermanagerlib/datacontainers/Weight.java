package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class Weight {
    private String key;
    private WeightData body;

    /**
     * Weight constructor.
     */
    public Weight() { }

    /**
     * Weight constructor.
     * @param key date value
     * @param body weight data
     */
    public Weight(String key, WeightData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Weight constructor.
     * @param date Weight date
     * @param weight Weight value
     */
    public Weight(String date, Integer weight) {
        PetData.checkDateFormat(date);
        this.key = date;
        this.body = new WeightData(weight);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String date) {
        PetData.checkDateFormat(date);
        this.key = date;
    }

    public WeightData getBody() {
        return body;
    }

    public void setBody(WeightData body) {
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
            + "date='" + key + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Weight) {
            return ((Weight) obj).getKey().equals(this.getKey())
                && ((Weight) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
