package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FreqTraining {
    private FreqTrainingData body;

    public FreqTraining(FreqTrainingData body) {
        this.body = body;
    }

    /**
     * Returns the FreqTraining Data.
     * @return FreqTraining Data
     */
    public FreqTrainingData getBody() {
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
        if (obj instanceof FreqTraining) {
            return ((FreqTraining) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
