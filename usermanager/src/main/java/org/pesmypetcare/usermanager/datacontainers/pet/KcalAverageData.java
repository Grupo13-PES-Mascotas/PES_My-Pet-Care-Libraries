package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KcalAverageData {
    private Double kcalAverage;

    public KcalAverageData(double kcalAverage) {
        this.kcalAverage = kcalAverage;
    }

    /**
     * Returns the value of the kcalAverage.
     * @return value of the kcalAverage
     */
    public Double getKcalAverage() {
        return kcalAverage;
    }

    /**
     * Creates a kcalAverage json object.
     * @return A JSON Object with the kcalAverage data
     */
    public JSONObject buildKcalAverageJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("kcalAverage", Double.toString(kcalAverage));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", kcalAverage=" + kcalAverage
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof KcalAverageData) {
            return ((KcalAverageData) obj).getKcalAverage().equals(this.getKcalAverage());
        }
        return false;
    }
}
