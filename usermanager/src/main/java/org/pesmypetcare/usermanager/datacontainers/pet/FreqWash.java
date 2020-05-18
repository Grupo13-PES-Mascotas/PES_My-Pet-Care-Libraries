package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FreqWash {
    private FreqWashData body;

    public FreqWash(FreqWashData body) {
        this.body = body;
    }

    /**
     * Returns the FreqWash Data.
     * @return FreqWash Data
     */
    public FreqWashData getBody() {
        return body;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof FreqWash) {
            return ((FreqWash) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
