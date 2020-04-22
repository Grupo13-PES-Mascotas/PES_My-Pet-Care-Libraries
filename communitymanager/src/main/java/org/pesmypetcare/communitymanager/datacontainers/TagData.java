package org.pesmypetcare.communitymanager.datacontainers;

import java.util.List;

/**
 * @author Santiago Del Rey
 */
public class TagData {
    private List<String> groups;
    private List<String> forums;

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getForums() {
        return forums;
    }

    @Override
    public String toString() {
        return "TagData{" +
            "groups=" + groups +
            ", forums=" + forums +
            '}';
    }
}
