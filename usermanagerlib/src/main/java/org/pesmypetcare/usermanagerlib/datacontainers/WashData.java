package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class WashData {
    private Integer value;

    public WashData() { }

    public WashData(Integer duration) {
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
        if (obj instanceof WashData) {
            return ((WashData) obj).getDuration().equals(this.getDuration());
        }
        return false;
    }
}
