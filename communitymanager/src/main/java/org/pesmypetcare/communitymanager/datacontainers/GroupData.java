package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class GroupData {
    @NonNull
    private String name;
    @NonNull
    private String creator;
    private String creationDate;
    private Map<String, String> icon;
    private String description;
    private List<String> tags;
    private Map<String, String> members;

    /**
     * Creates a group data with the name group, its creator username and its description.
     *
     * @param name The group name
     * @param creator The creator's username
     * @param description The group description
     */
    public GroupData(@NonNull String name, @NonNull String creator, String description) {
        this.name = name;
        this.creator = creator;
        this.description = description;
    }

    /**
     * Creates a group data with the name group, its creator username and its tags.
     *
     * @param name The group name
     * @param creator The creator's username
     * @param tags The group tags
     */
    public GroupData(@NonNull String name, @NonNull String creator, List<String> tags) {
        this.name = name;
        this.creator = creator;
        this.tags = tags;
    }

    /**
     * Creates a group data with the name group, its creator username, description and tags.
     *
     * @param name The group name
     * @param creator The creator's username
     * @param description The group description
     * @param tags The group tags
     */
    public GroupData(@NonNull String name, @NonNull String creator, String description, List<String> tags) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.tags = tags;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getCreator() {
        return creator;
    }

    public void setCreator(@NonNull String creator) {
        this.creator = creator;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Map<String, String> getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getMembers() {
        return members;
    }

    public String getLastModificationDateOfIcon() {
        return icon.get("lastModified");
    }

    public String getIconPath() {
        return icon.get("path");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupData groupData = (GroupData) o;
        return getName().equals(groupData.getName()) && getCreator().equals(groupData.getCreator()) && Objects.equals(
                getCreationDate(), groupData.getCreationDate()) && Objects.equals(getIcon(), groupData.getIcon())
                && Objects.equals(getDescription(), groupData.getDescription()) && Objects.equals(getTags(),
                groupData.getTags()) && Objects.equals(getMembers(), groupData.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCreator(), getCreationDate(), getIcon(), getDescription(), getTags(),
                getMembers());
    }

    @NonNull
    @Override
    public String toString() {
        return "GroupData{" + "name='" + name + '\'' + ", creator='" + creator + '\'' + ", creationDate='"
                + creationDate + '\'' + ", icon='" + icon + '\'' + ", description='" + description + '\'' + ", tags="
                + tags + ", members=" + members + '}';
    }
}
