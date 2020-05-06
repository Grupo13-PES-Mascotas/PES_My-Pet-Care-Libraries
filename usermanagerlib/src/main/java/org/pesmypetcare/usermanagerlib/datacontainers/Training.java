package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class Training {
    private String key;
    private TrainingData body;

    /**
     * Training constructor from date and training duration.
     * @param key date value
     * @param body training data
     */
    public Training(String key, TrainingData body) {
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
     * Returns the training data.
     * @return training data
     */
    public TrainingData getBody() {
        return body;
    }

    /**
     * Sets a new training data.
     * @param body training data
     */
    public void setBody(TrainingData body) {
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
        if (obj instanceof Training) {
            return ((Training) obj).getDate().equals(this.getDate())
                && ((Training) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
