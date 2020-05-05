package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class PetCollectionFieldData {
    private Map<String, Object> body;

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PetCollectionFieldData) {
            return ((PetCollectionFieldData) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
