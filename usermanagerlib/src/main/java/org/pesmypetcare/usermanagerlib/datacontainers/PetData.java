package org.pesmypetcare.usermanagerlib.datacontainers;

import java.util.Date;

public class PetData {
    private GenderType gender;
    private String breed;
    private Date birth;
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
    public Date getBirth() {
        return birth;
    }

    /**
     * The method that set the pet birth.
     * @param birth The pet's birth
     */
    public void setBirth(Date birth) {
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
}
