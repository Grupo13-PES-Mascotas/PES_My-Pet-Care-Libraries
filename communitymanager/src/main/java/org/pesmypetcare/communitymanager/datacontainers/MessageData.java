package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class MessageData {
    private String creator;
    private String publicationDate;
    private String text;
    private String imagePath;
    private boolean banned;
    private List<String> likedBy;

    public MessageData() { }

    public MessageData(@NonNull String creator, String text) {
        this.creator = creator;
        this.text = text;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(@NonNull String creator) {
        this.creator = creator;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    private void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    private void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isBanned() {
        return banned;
    }

    private void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    private void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return isBanned() == that.isBanned() &&
            getCreator().equals(that.getCreator()) &&
            Objects.equals(getPublicationDate(), that.getPublicationDate()) &&
            Objects.equals(getText(), that.getText()) &&
            Objects.equals(getImagePath(), that.getImagePath()) &&
            Objects.equals(getLikedBy(), that.getLikedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreator(), getPublicationDate(), getText(), getImagePath(), isBanned(), getLikedBy());
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageData{" +
            "creator='" + creator + '\'' +
            ", publicationDate='" + publicationDate + '\'' +
            ", text='" + text + '\'' +
            ", imagePath='" + imagePath + '\'' +
            ", banned=" + banned +
            ", likedBy=" + likedBy +
            '}';
    }
}
