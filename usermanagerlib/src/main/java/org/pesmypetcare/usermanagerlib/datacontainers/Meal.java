package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Meal {
    private Date date;
    private MealData body;

    /**
     * Returns the meal date
     * @return Meal date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets a new meal date
     * @param date Meal date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the Meal Data
     * @return Meal Data
     */
    public MealData getBody() {
        return body;
    }

    /**
     * Sets a new Meal Data
     * @param body Meal Data
     */
    public void setBody(MealData body) {
        this.body = body;
    }


    @NonNull
    @Override
    public String toString() {
        return "{"
            + "name='" + date.toString() + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Meal) {
            return ((Meal) obj).getDate().equals(this.getDate())
                && ((Meal) obj).getBody()==(this.getBody());
        }
        return false;
    }
}
