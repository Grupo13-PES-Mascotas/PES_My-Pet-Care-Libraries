package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Santiago Del Rey
 */
public class MessageDisplay extends Message {
    private byte[] image;

    /**
     * Default constructor.
     */
    public MessageDisplay() {
    }

    /**
     * Creates a message from message data.
     *
     * @param messageReceiveData The message data
     */
    public MessageDisplay(MessageReceiveData messageReceiveData) {
        super(messageReceiveData);
        this.image = messageReceiveData.buildImage();
    }

    public String getCreator() {
        return super.getCreator();
    }

    public String getPublicationDate() {
        return super.getPublicationDate();
    }

    public String getText() {
        return super.getText();
    }

    public boolean isBanned() {
        return super.isBanned();
    }


    public List<String> getReportedList() {
        return super.getReportedList();
    }

    public List<String> getLikedBy() {
        return super.getLikedBy();
    }

    @Nullable
    public byte[] getImage() {
        if (image == null) {
            return null;
        }
        return Arrays.copyOf(image, image.length);
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
        MessageDisplay that = (MessageDisplay) o;
        return Arrays.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(getImage());
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageDisplay{" + "creator='" + getCreator() + '\'' + ", publicationDate='" + getPublicationDate()
                + '\'' + ", " + "text='" + getText() + '\'' + ", banned=" + isBanned() + ", likedBy=" + getLikedBy()
                + ", image=" + Arrays.toString(image) + '}';
    }
}
