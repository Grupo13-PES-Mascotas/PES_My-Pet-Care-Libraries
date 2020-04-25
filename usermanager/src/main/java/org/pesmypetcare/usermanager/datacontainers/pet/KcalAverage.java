package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KcalAverage {
    private KcalAverageData body;

    public KcalAverage(KcalAverageData body) {
        this.body = body;
    }

    /**
     * Returns the KcalAverage Data.
     * @return KcalAverage Data
     */
    public KcalAverageData getBody() {
        return body;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof KcalAverage) {
            return ((KcalAverage) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
