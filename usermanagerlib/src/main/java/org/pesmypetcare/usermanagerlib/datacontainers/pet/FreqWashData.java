package org.pesmypetcare.usermanagerlib.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FreqWashData {
    private Double freqWash;

    public FreqWashData(double freqWash) {
        this.freqWash = freqWash;
    }

    /**
     * Returns the value of the freqWash.
     * @return value of the freqWash
     */
    public Double getFreqWash() {
        return freqWash;
    }

    /**
     * Creates a freqWash json object.
     * @return A JSON Object with the freqWash data
     */
    public JSONObject buildFreqWashJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("freqWash", Double.toString(freqWash));
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", freqWash=" + freqWash
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof FreqWashData) {
            return ((FreqWashData) obj).getFreqWash().equals(this.getFreqWash());
        }
        return false;
    }
}
