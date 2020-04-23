package org.pesmypetcare.usermanagerlib.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KcalData {
    private Double kcal;

    public KcalData(double kcal) {
        this.kcal = kcal;
    }

    /**
     * Returns the value of the kcal.
     * @return value of the kcal
     */
    public Double getKcal() {
        return kcal;
    }

    /**
     * Creates a kcal json object.
     * @return A JSON Object with the kcal data
     */
    public JSONObject buildKcalJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("kcal", Double.toString(kcal));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", kcal=" + kcal
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof KcalData) {
            return ((KcalData) obj).getKcal().equals(this.getKcal());
        }
        return false;
    }
}
