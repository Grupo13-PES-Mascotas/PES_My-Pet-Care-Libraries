package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class PetCollectionField {
    private String key;
    private PetCollectionFieldData body;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PetCollectionFieldData getBody() {
        return body;
    }

    public void setBody(PetCollectionFieldData body) {
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
