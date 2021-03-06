package org.pesmypetcare.communitymanager.datacontainers;

import android.util.Base64;

import androidx.annotation.NonNull;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;

import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class MessageSendData extends Message {
    private String encodedImage;

    /**
     * Default constructor.
     */
    private MessageSendData() {
    }

    /**
     * Creates a message with a creator and text. If the text is empty the creation will fail.
     *
     * @param creator The creator's username
     * @param text The text
     * @throws MyPetCareException When the text is empty
     */
    public MessageSendData(@NonNull String creator, @NonNull String text) throws MyPetCareException {
        super(creator, text);
    }

    /**
     * Creates a message with a creator and an image.
     *
     * @param creator The creator's username
     * @param image The text
     */
    public MessageSendData(@NonNull String creator, @NonNull byte[] image) {
        super(creator);
        encodedImage = Base64.encodeToString(image, Base64.NO_WRAP);
    }

    /**
     * Creates a message with a creator and text. If the text is empty the creation will fail.
     *
     * @param creator The creator's username
     * @param text The text
     * @param image The image
     * @throws MyPetCareException When the text is empty
     */
    public MessageSendData(@NonNull String creator, @NonNull String text, @NonNull byte[] image)
            throws MyPetCareException {
        super(creator, text);
        encodedImage = Base64.encodeToString(image, Base64.NO_WRAP);
    }

    public void setCreator(@NonNull String creator) {
        super.setCreator(creator);
    }

    public void setText(@NonNull String text) throws MyPetCareException {
        super.setText(text);
    }

    public void setEncodedImage(@NonNull byte[] image) {
        encodedImage = Base64.encodeToString(image, Base64.NO_WRAP);
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
        MessageSendData that = (MessageSendData) o;
        return Objects.equals(encodedImage, that.encodedImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), encodedImage);
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageSendData{" + "creator='" + getCreator() + '\'' + ", publicationDate='" + getPublicationDate()
                + '\'' + ", " + "text='" + getText() + '\'' + ", banned=" + isBanned() + ", likedBy=" + getLikedBy()
                + ", encodedImage='" + encodedImage + '\'' + '}';
    }
}
