package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Santiago Del Rey
 */
public class PetData {
    private GenderType gender;
    private String breed;
    private String birth;
    private String pathologies;
    private String needs;
    private Double recommendedKcal;

    /**
     * The method that returns the pet gender.
     * @return The pet's gender
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * The method that set the pet gender.
     * @param gender The pet's gender
     */
    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    /**
     * The method that returns the pet breed.
     * @return The pet's breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * The method that set the pet breed.
     * @param breed The pet's breed
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * The method that returns the pet birth.
     * @return The pet's birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * The method that set the pet birth.
     * @param birth The pet's birth
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * The method that returns the pet needs.
     * @return The pet's needs
     */
    public String getNeeds() {
        return needs;
    }

    /**
     * The method that set the pet needs.
     * @param needs The pet's needs
     */
    public void setNeeds(String needs) {
        this.needs = needs;
    }

    /**
     * The method that returns the pet pathologies.
     * @return The pet's pathologies
     */
    public String getPathologies() {
        return pathologies;
    }

    /**
     * The method that set the pet pathologies.
     * @param pathologies The pet's pathologies
     */
    public void setPathologies(String pathologies) {
        this.pathologies = pathologies;
    }

    /**
     * The method that returns the pet recommended kcal.
     * @return The pet's recommended kcal
     */
    public Double getRecommendedKcal() {
        return recommendedKcal;
    }

    /**
     * The method that set the pet recommended kcal.
     * @param recommendedKcal The pet's recommended kcal
     */
    public void setRecommendedKcal(Double recommendedKcal) {
        this.recommendedKcal = recommendedKcal;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "gender=" + gender
            + ", breed='" + breed + '\''
            + ", birth=" + birth
            + ", pathologies='" + pathologies + '\''
            + ", needs='" + needs + '\''
            + ", recommendedKcal=" + recommendedKcal
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PetData) {
            return ((PetData) obj).getBirth().equals(this.getBirth())
                && ((PetData) obj).getBreed().equals(this.getBreed())
                && ((PetData) obj).getGender() == this.getGender()
                && ((PetData) obj).getPathologies().equals(this.getPathologies())
                && ((PetData) obj).getRecommendedKcal().equals(this.getRecommendedKcal())
                && ((PetData) obj).getNeeds().equals(this.getNeeds());
        }
        return false;
    }
}
