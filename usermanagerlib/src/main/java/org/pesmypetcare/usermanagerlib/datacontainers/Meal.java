package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class Meal {
    private String key;
    private MealData body;

    public Meal(String date, MealData body) {
        this.key = date;
        this.body = body;
    }

    /**
     * Returns the meal date.
     * @return Meal date
     */
    public String getDate() {
        return key;
    }

    /**
     * Sets a new meal date.
     * @param date Meal date
     */
    public void setDate(String date) {
        this.key = date;
    }

    /**
     * Returns the Meal Data.
     * @return Meal Data
     */
    public MealData getBody() {
        return body;
    }

    /**
     * Sets a new Meal Data.
     * @param body Meal Data
     */
    public void setBody(MealData body) {
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
        if (obj instanceof Meal) {
            return ((Meal) obj).getDate().equals(this.getDate())
                && ((Meal) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
