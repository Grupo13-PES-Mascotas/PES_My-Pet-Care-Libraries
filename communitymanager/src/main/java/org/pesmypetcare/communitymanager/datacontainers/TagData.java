package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

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

    /**
     * Creates a tag with the specified lists of groups or forums.
     * @param names The list of groups or forums that use it
     * @param isGroupList Whether the list is a list of groups or forums
     */
    public TagData(List<String> names, boolean isGroupList) {
        if (isGroupList) {
            this.groups = names;
        } else {
            this.forums = names;
        }
    }

    /**
     * Creates a tag with the specified lists of groups and forums.
     * @param groups The list of groups that use it
     * @param forums The list of forums that use it
     */
    public TagData(List<String> groups, List<String> forums) {
        this.groups = groups;
        this.forums = forums;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getForums() {
        return forums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TagData tagData = (TagData) o;
        return Objects.equals(getGroups(), tagData.getGroups()) && Objects.equals(getForums(), tagData.getForums());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroups(), getForums());
    }

    @NonNull
    @Override
    public String toString() {
        return "TagData{" + "groups=" + groups + ", forums=" + forums + '}';
    }
}
