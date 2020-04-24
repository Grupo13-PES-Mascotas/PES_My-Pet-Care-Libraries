package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class ForumData {
    @NonNull
    private String name;
    @NonNull
    private String creator;
    private String creationDate;
    private List<String> tags;

    public ForumData(@NonNull String name, @NonNull String creator, List<String> tags) {
        this.name = name;
        this.creator = creator;
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
        return getName().equals(forumData.getName()) &&
            getCreator().equals(forumData.getCreator()) &&
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
