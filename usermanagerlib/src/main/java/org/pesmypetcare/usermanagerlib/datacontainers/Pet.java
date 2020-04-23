package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Santiago Del Rey
 */
public class Pet {
    private String name;
    private PetData body;

    /**
     * The method that returns the pet name.
     * @return The pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * The method that set the pet name.
     * @param name The pet's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method that returns the pet information.
     * @return The pet's information
     */
    public PetData getBody() {
        return body;
    }

    /**
     * The method that set the pet information.
     * @param body The pet's information
     */
    public void setBody(PetData body) {
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "name='" + name + '\''
            + ", body=" + body
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Pet) {
            return ((Pet) obj).getName().equals(this.getName()) && ((Pet) obj).getBody()
                    .equals(this.getBody());
        }
        return false;
    }
}
