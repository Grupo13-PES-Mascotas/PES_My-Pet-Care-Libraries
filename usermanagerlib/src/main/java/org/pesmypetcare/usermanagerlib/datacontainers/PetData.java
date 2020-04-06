package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PetData {
    private GenderType gender;
    private String breed;
    private String birth;
    private Double weight;
    private String pathologies;
    private Double recommendedKcal;
    private int washFreq;

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
     * The method that returns the pet weight.
     * @return The pet's weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * The method that set the pet weight.
     * @param weight The pet's weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
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

    /**
     * The method that returns the pet wash frequency.
     * @return The pet's wash frequency
     */
    public int getWashFreq() {
        return washFreq;
    }

    /**
     * The method that set the pet wash frequency.
     * @param washFreq The pet's wash frequency
     */
    public void setWashFreq(int washFreq) {
        this.washFreq = washFreq;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "gender=" + gender
            + ", breed='" + breed + '\''
            + ", birth=" + birth
            + ", weight=" + weight
            + ", pathologies='" + pathologies + '\''
            + ", recommendedKcal=" + recommendedKcal
            + ", washFreq=" + washFreq
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PetData) {
            return ((PetData) obj).getBirth().equals(this.getBirth()) &&
                ((PetData) obj).getBreed().equals(this.getBreed()) &&
                ((PetData) obj).getGender() == this.getGender() &&
                ((PetData) obj).getPathologies().equals(this.getPathologies()) &&
                ((PetData) obj).getRecommendedKcal().equals(this.getRecommendedKcal()) &&
                ((PetData) obj).getWashFreq() == this.getWashFreq() &&
                ((PetData) obj).getWeight().equals(this.getWeight());
        }
        return false;
    }
}
