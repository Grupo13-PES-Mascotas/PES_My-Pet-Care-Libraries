package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class Illness {
    private String key;
    private IllnessData body;

    /**
     * Illness constructor.
     */
    public Illness() {
    }

    /**
     * Illness constructor.
     * @param key Date
     * @param body Illness data
     */
    public Illness(String key, IllnessData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * Illness constructor.
     * @param startDateTime StartDateTime
     * @param endDateTime body endDateTime
     * @param description body description
     * @param type body type
     * @param severity body severity
     */
    public Illness(String startDateTime, String endDateTime, String description, IllnessType type,
                   SeverityType severity) {
        PetData.checkDateFormat(startDateTime);
        PetData.checkDateFormat(endDateTime);
        this.key = startDateTime;
        this.body = new IllnessData(endDateTime, description, type, severity);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        PetData.checkDateFormat(key);
        this.key = key;
    }

    public IllnessData getBody() {
        return body;
    }

    public void setBody(IllnessData body) {
        this.body = body;
    }

    /**
     * Turns the body into a Map of key String and element Object.
     * @return Body turned into map
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
        if (obj instanceof Illness) {
            return ((Illness) obj).getKey().equals(this.getKey())
                && ((Illness) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
