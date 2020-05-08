package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class WashData {
    private String description;
    private Integer duration;

    /**
     * WashData constructor.
     */
    public WashData() { }

    /**
     * WashData constructor.
     * @param description Description
     * @param duration Duration
     */
    public WashData(String description, Integer duration) {
        this.description = description;
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Turns the WashData into a Map of key String and element Object.
     * @return WashData turned into map
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("description", description);
        response.put("duration", duration);
        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "description='" + description + '\''
            + ", duration=" + duration
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof WashData) {
            return ((WashData) obj).getDescription().equals(this.getDescription())
                && ((WashData) obj).getDuration().equals(this.getDuration());
        }
        return false;
    }
}
