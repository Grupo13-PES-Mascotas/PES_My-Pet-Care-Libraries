package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class WashData {
    private Integer duration;

    public WashData() { }

    public WashData(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + " duration = " + duration
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
