package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class PetCollectionField {
    private String key;
    private Map<String, Object> body;

    public PetCollectionField() { }

    public PetCollectionField(String key, Map<String, Object> body) {
        this.key = key;
        this.body = body;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
            + "key='" + key + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PetCollectionField) {
            return ((PetCollectionField) obj).getKey().equals(this.getKey()) &&
                ((PetCollectionField) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
