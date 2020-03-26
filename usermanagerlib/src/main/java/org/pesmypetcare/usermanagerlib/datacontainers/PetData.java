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

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getPathologies() {
        return pathologies;
    }

    public void setPathologies(String pathologies) {
        this.pathologies = pathologies;
    }

    public Double getRecommendedKcal() {
        return recommendedKcal;
    }

    public void setRecommendedKcal(Double recommendedKcal) {
        this.recommendedKcal = recommendedKcal;
    }

    public int getWashFreq() {
        return washFreq;
    }

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
