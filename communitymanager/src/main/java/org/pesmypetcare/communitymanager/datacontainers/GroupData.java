package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class GroupData {
    private String name;
    private String creator;
    private String creationDate;
    private String icon;
    private String description;
    private List<String> tags;
    private Map<String, String> members;

    public GroupData(String name, String creator, String description, List<String> tags) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreationDate() {
        return creationDate;
    }

    private void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getIcon() {
        return icon;
    }

    private void setIcon(String icon) {
        this.icon = icon;
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

    private void setMembers(Map<String, String> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return Objects.equals(getName(), groupData.getName()) &&
            Objects.equals(getCreator(), groupData.getCreator()) &&
            Objects.equals(getCreationDate(), groupData.getCreationDate()) &&
            Objects.equals(getIcon(), groupData.getIcon()) &&
            Objects.equals(getDescription(), groupData.getDescription()) &&
            Objects.equals(getTags(), groupData.getTags()) &&
            Objects.equals(getMembers(), groupData.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCreator(), getCreationDate(), getIcon(), getDescription(), getTags(), getMembers());
    }

    @NonNull
    @Override
    public String toString() {
        return "GroupData{" +
            "name='" + name + '\'' +
            ", creator='" + creator + '\'' +
            ", creationDate='" + creationDate + '\'' +
            ", icon='" + icon + '\'' +
            ", description='" + description + '\'' +
            ", tags=" + tags +
            ", members=" + members +
            '}';
    }
}
