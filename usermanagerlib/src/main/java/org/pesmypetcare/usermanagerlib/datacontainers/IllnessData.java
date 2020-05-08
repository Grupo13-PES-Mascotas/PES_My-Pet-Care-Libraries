package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class IllnessData {
    private String endDateTime;
    private String description;
    private IllnessType type;
    private SeverityType severity;

    /**
     * IllnessData constructor.
     */
    public IllnessData() { }

    /**
     * IllnessData constructor.
     * @param endDateTime End DateTime
     * @param description Description
     * @param type Illness type
     * @param severity Severity
     */
    public IllnessData(String endDateTime, String description, IllnessType type, SeverityType severity) {
        PetData.checkDateFormat(endDateTime);
        this.endDateTime = endDateTime;
        this.description = description;
        this.type = type;
        this.severity = severity;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        PetData.checkDateFormat(endDateTime);
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IllnessType getType() {
        return type;
    }

    public void setType(IllnessType type) {
        this.type = type;
    }

    public SeverityType getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    /**
     * Turns the IllnessData into a Map of key String and element Object.
     * @return IllnessData turned into map
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("endDateTime", endDateTime);
        response.put("description", description);
        response.put("type", type.name());
        response.put("severity", severity.name());
        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "endDateTime='" + endDateTime + '\''
            + ", description='" + description + '\''
            + ", type='" + type + '\''
            + ", severity='" + severity + '\''
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof IllnessData) {
            return ((IllnessData) obj).getEndDateTime().equals(this.getEndDateTime())
                && ((IllnessData) obj).getDescription().equals(this.getDescription())
                && ((IllnessData) obj).getType().equals(this.getType())
                && ((IllnessData) obj).getSeverity().equals(this.getSeverity());
        }
        return false;
    }
}
