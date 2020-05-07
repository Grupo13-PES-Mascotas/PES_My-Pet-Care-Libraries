package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class VetVisit {
    private String key;
    private VetVisitData body;

    /**
     * VetVisit constructor.
     */
    public VetVisit() { }

    /**
     * VetVisit constructor.
     * @param key Vet visit date
     * @param body Vet visit data
     */
    public VetVisit(String key, VetVisitData body) {
        PetData.checkDateFormat(key);
        this.key = key;
        this.body = body;
    }

    /**
     * VetVisit constructor.
     * @param date Vet visit date
     * @param reason Vet visit reason
     * @param address Vet visit address
     */
    public VetVisit(String date, String reason, String address) {
        PetData.checkDateFormat(date);
        this.key = date;
        this.body = new VetVisitData(reason, address);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        PetData.checkDateFormat(key);
        this.key = key;
    }

    public VetVisitData getBody() {
        return body;
    }

    public void setBody(VetVisitData body) {
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
        if (obj instanceof VetVisit) {
            return ((VetVisit) obj).getKey().equals(this.getKey())
                && ((VetVisit) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
