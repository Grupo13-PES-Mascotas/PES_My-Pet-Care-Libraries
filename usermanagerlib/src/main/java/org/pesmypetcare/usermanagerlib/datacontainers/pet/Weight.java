package org.pesmypetcare.usermanagerlib.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Weight {
    private WeightData body;

    public Weight(WeightData body) {
        this.body = body;
    }

    /**
     * Returns the Weight Data.
     * @return Weight Data
     */
    public WeightData getBody() {
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
        if (obj instanceof Weight) {
            return ((Weight) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
