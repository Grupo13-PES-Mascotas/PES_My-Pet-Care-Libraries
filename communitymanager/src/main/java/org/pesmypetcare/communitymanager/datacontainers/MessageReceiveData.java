package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.Blob;

import org.pesmypetcare.httptools.MyPetCareException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MessageReceiveData extends Message {
    private List<Blob> image;

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

    public List<Blob> getImage() {
        return image;
    }

    public void setImage(List<Blob> image) {
        this.image = image;
    }

    /**
     * Builds the message image from its blobs.
     * @return The message image as a byte array
     * @throws IOException When the reconstruction of the image fails.
     */
    @Nullable
    byte[] buildImage() throws IOException {
        if (image != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (Blob blob : image) {
                outputStream.write(blob.toBytes());
            }
            outputStream.close();
            return outputStream.toByteArray();
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
