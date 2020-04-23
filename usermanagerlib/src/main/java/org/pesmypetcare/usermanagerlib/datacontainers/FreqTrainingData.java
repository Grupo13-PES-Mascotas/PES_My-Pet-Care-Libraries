package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FreqTrainingData {
    private Double freqTraining;

    public FreqTrainingData(double freqTraining) {
        this.freqTraining = freqTraining;
    }

    /**
     * Returns the value of the freqTraining.
     * @return value of the freqTraining
     */
    public Double getFreqTraining() {
        return freqTraining;
    }

    /**
     * Creates a freqTraining json object.
     * @return A JSON Object with the freqTraining data
     */
    public JSONObject buildFreqTrainingJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("freqTraining", Double.toString(freqTraining));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", freqTraining=" + freqTraining
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof FreqTrainingData) {
            return ((FreqTrainingData) obj).getFreqTraining().equals(this.getFreqTraining());
        }
        return false;
    }
}
