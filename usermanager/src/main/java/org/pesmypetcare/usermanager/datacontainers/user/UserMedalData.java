package org.pesmypetcare.usermanager.datacontainers.user;


import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Oriol Catalán
 */
public class UserMedalData {
    private String name;
    private List<Double> levels;
    private String description;
    private byte[] medalIcon;
    private Double progress;
    private Double currentLevel;
    private List<String> completedLevelsDate;

    /**
     * Default constructor.
     */
    public UserMedalData() {
    }

    /**
     * Creates a UserMedalData object with the specified attributes.
     * @param name UserMedalData's name
     * @param levels Goals of the UserMedalData's levels
     * @param description UserMedalData's description
     * @param medalIcon UserMedalData's icon
     * @param progress UserMedalData's progress
     * @param currentLevel UserMedalData's current level of the medal
     * @param completedLevelsDate UserMedalData's array with the completed levels dates.
     */
    public UserMedalData(String name, List<Double> levels, String description,
                         byte[] medalIcon, Double progress, Double currentLevel,
                         List<String> completedLevelsDate) {
        this.name = name;
        this.levels = levels;
        this.description = description;
        this.medalIcon = medalIcon;
        this.progress = progress;
        this.currentLevel = currentLevel;
        this.completedLevelsDate = completedLevelsDate;
    }

    public String getName() {
        return name;
    }

    public List<Double> getLevels() {
        return levels;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getMedalIcon() {
        return medalIcon;
    }

    public Double getProgress() {
        return progress;
    }

    public Double getCurrentLevel() {
        return currentLevel;
    }

    public List<String> getCompletedLevelsDate() {
        return completedLevelsDate;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserMedalData{" + "name='" + name + '\'' + ", levels=" + levels
                + ", description='" + description + '\''
                + ", medalIcon=" + Arrays.toString(medalIcon) + ", progress=" + progress
                + ", currentLevel=" + currentLevel
                + ", completedLevelsDate=" + completedLevelsDate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMedalData that = (UserMedalData) o;
        return Objects.equals(name, that.name)
                && Objects.equals(levels, that.levels)
                && Objects.equals(description, that.description)
                && Arrays.equals(medalIcon, that.medalIcon)
                && Objects.equals(progress, that.progress)
                && Objects.equals(currentLevel, that.currentLevel)
                && Objects.equals(completedLevelsDate, that.completedLevelsDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, levels, description, progress, currentLevel, completedLevelsDate);
        result = 31 * result + Arrays.hashCode(medalIcon);
        return result;
    }
}
