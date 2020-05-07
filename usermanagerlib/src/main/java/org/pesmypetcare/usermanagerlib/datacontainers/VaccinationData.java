package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class VaccinationData {
    private String description;

    /**
     * VaccinationData constructor.
     */
    public VaccinationData() { }

    /**
     * VaccinationData constructor.
     * @param description Description
     */
    public VaccinationData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Turns the VaccinationData into a Map<String, Object>.
     * @return Map<String, Object> containing the VaccinationData
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("description", description);
        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "description='" + description + '\''
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof VaccinationData) {
            return (((VaccinationData) obj).getDescription().equals(this.getDescription()));
        }
        return false;
    }
}
