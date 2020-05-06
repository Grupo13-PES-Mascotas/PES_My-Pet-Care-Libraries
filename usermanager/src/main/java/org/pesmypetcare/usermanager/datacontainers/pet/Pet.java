package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class Pet {
    public static final String GENDER = "gender";
    public static final String BIRTH = "birth";
    public static final String BREED = "breed";
    public static final String PATHOLOGIES = "pathologies";
    public static final String RECOMMENDED_KCAL = "recommendedKcal";
    public static final String NEEDS = "needs";
    private String name;
    private PetData body;

    public Pet() { }

    public Pet(String name, PetData body) {
        this.name = name;
        this.body = body;
    }

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

    /**
     * Creates the pet's json object.
     * @param petData The pet data container
     * @return A JSONObject with te required data to make the request
     */
    public static JSONObject buildPetJsonObject(PetData petData) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(GENDER, petData.getGender().toString());
        reqData.put(BREED, petData.getBreed());
        reqData.put(BIRTH, petData.getBirth());
        reqData.put(NEEDS, petData.getNeeds());
        reqData.put(PATHOLOGIES, petData.getPathologies());
        reqData.put(RECOMMENDED_KCAL, Double.toString(petData.getRecommendedKcal()));
        return new JSONObject(reqData);
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
            return ((Pet) obj).getName().equals(this.getName())
                && ((Pet) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
