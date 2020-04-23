package org.pesmypetcare.usermanagerlib.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Kcal {
    private KcalData body;

    public Kcal(KcalData body) {
        this.body = body;
    }

    /**
     * Returns the Kcal Data.
     * @return Kcal Data
     */
    public KcalData getBody() {
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
        if (obj instanceof Kcal) {
            return ((Kcal) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
