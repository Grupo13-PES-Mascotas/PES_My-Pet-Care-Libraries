package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class Meal {
    private String key;
    private MealData body;

    /**
     * Meal constructor.
     */
    public Meal() { }

    /**
     * Meal constructor.
     * @param key date value
     * @param body meal data
     */
    public Meal(String key, MealData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Meal constructor.
     * @param date Key value
     * @param mealName Body meal name
     * @param kcal Body kcalories
     */
    public Meal(String date, String mealName, double kcal) {
        PetData.checkDateFormat(date);
        this.key = date;
        this.body = new MealData(mealName, kcal);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String date) {
        PetData.checkDateFormat(key);
        this.key = date;
    }

    public MealData getBody() {
        return body;
    }

    public void setBody(MealData body) {
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
        if (obj instanceof Meal) {
            return ((Meal) obj).getKey().equals(this.getKey())
                && ((Meal) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
