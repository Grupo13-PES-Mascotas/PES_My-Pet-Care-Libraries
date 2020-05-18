package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author Santiago Del Rey
 */
public class TagData {
    private List<String> groups;
    private List<String> forums;

    /**
     * Default constructor.
     */
    public TagData() {
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getForums() {
        return forums;
    }

    @NonNull
    @Override
    public String toString() {
        return "TagData{" + "groups=" + groups + ", forums=" + forums + '}';
    }
}
