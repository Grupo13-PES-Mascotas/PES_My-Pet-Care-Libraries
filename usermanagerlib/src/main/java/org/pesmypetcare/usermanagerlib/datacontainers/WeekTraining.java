package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WeekTraining {
    private WeekTrainingData body;

    public WeekTraining(WeekTrainingData body) {
        this.body = body;
    }

    /**
     * Returns the WeekTraining Data.
     * @return WeekTraining Data
     */
    public WeekTrainingData getBody() {
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
        if (obj instanceof WeekTraining) {
            return ((WeekTraining) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
