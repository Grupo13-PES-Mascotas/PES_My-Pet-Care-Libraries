package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class Weight {
    private String key;
    private WeightData body;

    /**
     * Weight constructor from date and weight data.
     * @param key date value
     * @param body weight data
     */
    public Weight(String key, WeightData body) {
        this.key = key;
        this.body = body;
    }

    /**
     * Returns the date.
     * @return Date
     */
    public String getDate() {
        return key;
    }

    /**
     * Sets a new Date.
     * @param date Date
     */
    public void setDate(String date) {
        this.key = date;
    }

    /**
     * Returns the weight data.
     * @return Weight data
     */
    public WeightData getBody() {
        return body;
    }

    /**
     * Sets a new Weight data.
     * @param body Weight data
     */
    public void setBody(WeightData body) {
        this.body = body;
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
            return ((Weight) obj).getDate().equals(this.getDate())
                && ((Weight) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
