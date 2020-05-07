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

    /**
     * MealData constructor.
     */
    public MealData() { }

    /**
     * MealData constructor.
     * @param mealName mealName
     * @param kcal kcalories
     */
    public MealData(String mealName, double kcal) {
        this.mealName = mealName;
        this.kcal = kcal;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    /**
     * Turns the MealData into a Map<String, Object>.
     * @return Map<String, Object> containing the MealData
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("mealName", mealName);
        response.put("kcal", kcal);
        return response;
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
