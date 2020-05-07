package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class Exercise {
    private String key;
    private ExerciseData body;

    /**
     * Exercise Constructor.
     */
    public Exercise() { }

    /**
     * Exercise constructor from date and training duration.
     * @param key date value
     * @param body exercise data
     */
    public Exercise(String key, ExerciseData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Exercise constructor.
     * @param startDateTime Exercise key
     * @param endDateTime body endDateTime
     * @param name body name
     * @param description body description
     */
    public Exercise(String startDateTime, String endDateTime, String name, String description) {
        PetData.checkDateFormat(startDateTime);
        PetData.checkDateFormat(endDateTime);
        this.key = startDateTime;
        this.body = new ExerciseData(name, description, endDateTime);
    }

    /**
     * Exercise constructor.
     * @param startDateTime Exercise key
     * @param endDateTime body endDateTime
     * @param name body name
     * @param description body description
     * @param coordinates body coordinates
     */
    public Exercise(String startDateTime, String endDateTime, String name, String description,
                    List<LatLng> coordinates) {
        PetData.checkDateFormat(startDateTime);
        PetData.checkDateFormat(endDateTime);
        this.key = startDateTime;
        this.body = new ExerciseData(name, description, endDateTime, coordinates);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String startDateTime) {
        PetData.checkDateFormat(startDateTime);
        this.key = startDateTime;
    }

    public ExerciseData getBody() {
        return body;
    }

    public void setBody(ExerciseData body) {
        this.body = body;
    }

    /**
     * Turns the body into a Map<String, Object>.
     * @return Map<String, Object> containing the body
     */
    public Map<String, Object> getBodyAsMap() {
        return body.getAsMap();
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "date='" + key + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Exercise) {
            return ((Exercise) obj).getKey().equals(this.getKey())
                && ((Exercise) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
