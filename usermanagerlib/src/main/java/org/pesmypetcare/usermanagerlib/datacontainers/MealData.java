package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class MealData {
    private String mealName;
    private Double kcal;

    public MealData(String mealName, double kcal) {
        this.mealName = mealName;
        this.kcal = kcal;
    }

    /**
     * Returns the meal name.
     * @return Meal name
     */
    public String getMealName() {
        return mealName;
    }

    /**
     * Sets a new meal name.
     * @param mealName New meal name
     */
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    /**
     * Returns the amount of kcalories.
     * @return Amount of kcalories
     */
    public Double getKcal() {
        return kcal;
    }

    /**
     * Sets a new amount of kcalories.
     * @param kcal Amount of kcalories
     */
    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    /**
     * Creates a meal json object.
     * @return A JSON Object with the meal data
     */
    public JSONObject buildJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("mealName", mealName);
        reqData.put("kcal", Double.toString(kcal));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "mealName='" + mealName + '\''
            + ", kcal=" + kcal
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MealData) {
            return ((MealData) obj).getMealName().equals(this.getMealName())
                && ((MealData) obj).getKcal().equals(this.getKcal());
        }
        return false;
    }
}
