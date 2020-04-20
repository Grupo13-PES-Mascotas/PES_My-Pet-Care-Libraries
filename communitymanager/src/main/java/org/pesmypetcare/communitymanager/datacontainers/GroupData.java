package org.pesmypetcare.communitymanager.datacontainers;

import java.util.List;

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

    public GroupData(String name, String creatorUid, String creationDate, String description, List<String> tags) {
        this.name = name;
        this.creator = creatorUid;
        this.creationDate = creationDate;
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

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
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

    @Override
    public String toString() {
        return "GroupData{" +
            "name='" + name + '\'' +
            ", creator='" + creator + '\'' +
            ", creationDate='" + creationDate + '\'' +
            ", icon='" + icon + '\'' +
            ", description='" + description + '\'' +
            ", tags=" + tags +
            '}';
    }
}
