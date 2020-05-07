package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class ExerciseData {
    private String name;
    private String description;
    private List<LatLng> coordinates;
    private String endDateTime;

    /**
     * ExerciseData constructor.
     */
    public ExerciseData() { }

    /**
     * ExerciseData constructor.
     * @param name name
     * @param description description
     * @param endDateTime endDateTime
     */
    public ExerciseData(String name, String description, String endDateTime) {
        PetData.checkDateFormat(endDateTime);
        this.name = name;
        this.description = description;
        this.coordinates = null;
        this.endDateTime = endDateTime;
    }

    /**
     * ExerciseData constructor.
     * @param name name
     * @param description description
     * @param endDateTime endDateTime
     * @param coordinates coordinates
     */
    public ExerciseData(String name, String description, String endDateTime, List<LatLng> coordinates) {
        PetData.checkDateFormat(endDateTime);
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
        this.endDateTime = endDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Turns the ExerciseData into a Map<String, Object>.
     * @return Map<String, Object> containing the ExerciseData
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("endDateTime", endDateTime);
        response.put("name", name);
        response.put("description", description);
        response.put("coordinates", coordinates);
        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "name='" + name + '\''
            + ", description='" + description + '\''
            + ", endDateTime='" + endDateTime + '\''
            + ", coordinates=" + coordinates
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ExerciseData) {
            return (((ExerciseData) obj).getName().equals(this.getName())
                && ((ExerciseData) obj).getDescription().equals(this.getDescription())
                && ((ExerciseData) obj).getEndDateTime().equals(this.getEndDateTime())
                && ((ExerciseData) obj).getCoordinates().equals(this.getCoordinates()));
        }
        return false;
    }
}
