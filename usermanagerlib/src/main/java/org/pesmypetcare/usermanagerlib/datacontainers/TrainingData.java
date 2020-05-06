package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class TrainingData {
    private Integer value;

    public TrainingData() { }

    public TrainingData(Integer duration) {
        this.value = duration;
    }

    public Integer getDuration() {
        return value;
    }

    public void setDuration(Integer duration) {
        this.value = duration;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "duration = " + value
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof TrainingData) {
            return ((TrainingData) obj).getDuration().equals(this.getDuration());
        }
        return false;
    }
}
