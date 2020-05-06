package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class Wash {
    private String key;
    private WashData body;

    /**
     * Wash constructor from date and wash data.
     * @param key date value
     * @param body wash data
     */
    public Wash(String key, WashData body) {
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
     * Returns the wash data.
     * @return Wash data
     */
    public WashData getBody() {
        return body;
    }

    /**
     * Sets a new wash data.
     * @param body Wash data
     */
    public void setBody(WashData body) {
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
        if (obj instanceof Wash) {
            return ((Wash) obj).getDate().equals(this.getDate())
                && ((Wash) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
