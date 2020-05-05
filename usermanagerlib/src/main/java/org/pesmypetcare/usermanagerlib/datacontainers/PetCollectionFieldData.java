package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

/**
 * @author Marc Simó
 */
public class PetCollectionFieldData {
    private Map<String, Object> body;

    /*
    public PetCollectionFieldData() { }

    public PetCollectionFieldData(Map<String, Object> body) {
        this.body = body;
    }

    public PetCollectionFieldData(Object... args) {
        if (args.length%2 != 0) throw new IllegalArgumentException("PetCollectionFieldData constructor passed "
            + "arguments are not valid");
        for (int i = 0; i < args.length; i+=2) {
            body.put((String) args[i], args[i+1]);
        }
    }*/

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
