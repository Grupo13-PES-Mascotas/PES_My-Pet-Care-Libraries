package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.Blob;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class MessageReceiveData extends Message {
    private Blob image;

    public MessageReceiveData() {
    }

    /**
     * Creates a message with creator and text.
     *
     * @param creator The creator's username
     * @param text The text
     */
    public MessageReceiveData(@NonNull String creator, @NonNull String text) throws MyPetCareException {
        super(creator, text);
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    /**
     * Builds the message image from its blobs.
     * @return The message image as a byte array
     */
    @Nullable
    byte[] buildImage() {
        if (image != null) {
            return image.toBytes();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MessageReceiveData that = (MessageReceiveData) o;
        return Objects.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getImage());
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageReceiveData{" + "creator='" + getCreator() + '\'' + ", publicationDate='" + getPublicationDate()
                + '\'' + ", " + "text='" + getText() + '\'' + ", banned=" + isBanned() + ", likedBy=" + getLikedBy()
                + ", image=" + image + '}';
    }
}
