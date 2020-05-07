package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class VetVisitData {
    private String reason;
    private String address;

    /**
     * VetVisitData constructor.
     */
    public VetVisitData() { }

    /**
     * VetVisitData constructor.
     * @param reason Reason for the visit
     * @param address Vet address
     */
    public VetVisitData(String reason, String address) {
        this.reason = reason;
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Turns the VetVisitData into a Map<String, Object>.
     * @return Map<String, Object> containing the VetVisitData
     */
    public Map<String, Object> getAsMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("reason", reason);
        response.put("address", address);
        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "reason='" + reason + '\''
            + ", address='" + address + '\''
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof VetVisitData) {
            return (((VetVisitData) obj).getReason().equals(this.getReason())
                && ((VetVisitData) obj).getAddress().equals(this.getAddress()));
        }
        return false;
    }
}
