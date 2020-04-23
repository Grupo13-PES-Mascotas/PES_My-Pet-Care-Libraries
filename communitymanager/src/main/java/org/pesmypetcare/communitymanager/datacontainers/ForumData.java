package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class ForumData {
    private String name;
    private String creator;
    private String creationDate;
    private List<String> tags;

    public ForumData(String name, String creator, List<String> tags) {
        this.name = name;
        this.creator = creator;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForumData forumData = (ForumData) o;
        return Objects.equals(getName(), forumData.getName()) &&
            Objects.equals(getCreator(), forumData.getCreator()) &&
            Objects.equals(getCreationDate(), forumData.getCreationDate()) &&
            Objects.equals(getTags(), forumData.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCreator(), getCreationDate(), getTags());
    }

    @NonNull
    @Override
    public String toString() {
        return "ForumData{" +
            "name='" + name + '\'' +
            ", creator='" + creator + '\'' +
            ", creationDate='" + creationDate + '\'' +
            ", tags=" + tags +
            '}';
    }
}
