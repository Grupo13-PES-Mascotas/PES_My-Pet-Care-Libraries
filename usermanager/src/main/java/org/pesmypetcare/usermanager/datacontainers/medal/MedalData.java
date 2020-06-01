package org.pesmypetcare.usermanager.datacontainers.medal;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Oriol Catalán
 */
public class MedalData {
    private String name;
    private List<Double> levels;
    private String description;
    private ImageData medalIcon;

    /**
     * Default constructor.
     */
    public MedalData() {
    }

    /**
     * Creates a MedalData object with the specified name, levels, description and medalIcon.
     * @param name MedalData's name
     * @param levels Goals of the medal's levels
     * @param description MedalData's description
     * @param medalIcon MedalData's icon
     */
    public MedalData(String name, List<Double> levels, String description, ImageData medalIcon) {
        this.name = name;
        this.levels = levels;
        this.description = description;
        this.medalIcon = medalIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getLevels() {
        return levels;
    }

    public void setLevels(List<Double> levels) {
        this.levels = levels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageData getMedalIcon() {
        return medalIcon;
    }

    public void setMedalIcon(ImageData medalIcon) { this.medalIcon = medalIcon;}

    /**
     * Creates a medal data JSONObject.
     *
     * @return The JSONObject for the medal data
     * @throws JSONException Thrown to indicate a problem with the JSON API
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("levels", levels);
        json.put("description", description);
        json.put("medalIcon", medalIcon);
        return json;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserData{" + "name='" + name + '\'' + ", levels='" + levels + '\''
                + ", description='" + description + '\'' + ", medalIcon=" + medalIcon + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MedalData) {
            return ((MedalData) obj).getName().equals(this.name) && ((MedalData) obj).getLevels()
                    .equals(this.levels) && ((MedalData) obj).getDescription()
                    .equals(this.description) && ((MedalData) obj)
                    .getMedalIcon().equals(this.medalIcon);
        }
        return false;
    }
}
