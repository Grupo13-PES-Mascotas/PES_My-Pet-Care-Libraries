package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class Wash {
    private String key;
    private WashData body;

    /**
     * Wash constructor.
     */
    public Wash() { }

    /**
     * Wash constructor.
     * @param key date value
     * @param body wash data
     */
    public Wash(String key, WashData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Wash constructor.
     * @param date Wash date
     * @param description Wash description
     * @param duration Wash duration
     */
    public Wash(String date, String description, Integer duration) {
        PetData.checkDateFormat(date);
        this.key = date;
        this.body = new WashData(description, duration);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String date) {
        PetData.checkDateFormat(date);
        this.key = date;
    }

    public WashData getBody() {
        return body;
    }

    public void setBody(WashData body) {
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
        if (obj instanceof Wash) {
            return ((Wash) obj).getKey().equals(this.getKey())
                && ((Wash) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
