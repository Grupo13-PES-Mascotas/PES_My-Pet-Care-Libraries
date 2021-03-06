package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;

import java.util.List;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class Message {
    private String creator;
    private String publicationDate;
    private String text;
    private boolean banned;
    private List<String> reportedBy;
    private List<String> likedBy;

    /**
     * Default constructor.
     */
    Message() {
    }

    /**
     * Creates a message with a creator.
     *
     * @param creator The creator's username
     */
    Message(@NonNull String creator) {
        this.creator = creator;
    }

    /**
     * Creates a message with a creator and text. If the text is empty the creation will fail.
     *
     * @param creator The creator's username
     * @param text The text
     * @throws MyPetCareException When the text is empty
     */
    Message(@NonNull String creator, @NonNull String text) throws MyPetCareException {
        this(creator);
        if (text.isEmpty()) {
            throw new MyPetCareException("Text must not be empty");
        }
        this.text = text;
    }

    /**
     * Creates a message from a MessageReceiveData.
     *
     * @param messageReceiveData The MessageReceiveData
     */
    Message(@NonNull MessageReceiveData messageReceiveData) {
        this.creator = messageReceiveData.getCreator();
        this.text = messageReceiveData.getText();
        this.publicationDate = DateTime.convertUTCtoLocalString(messageReceiveData.getPublicationDate());
        this.banned = messageReceiveData.isBanned();
        this.reportedBy = messageReceiveData.getReportedList();
        this.likedBy = messageReceiveData.getLikedBy();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(@NonNull String creator) {
        this.creator = creator;
    }

    public String getText() {
        return text;
    }

    /**
     * Sets the message text. It will fail if the message is empty.
     *
     * @param text The text
     * @throws MyPetCareException When the text is empty
     */
    public void setText(String text) throws MyPetCareException {
        if (text != null) {
            if (text.isEmpty()) {
                throw new MyPetCareException("Text must not be empty");
            }
        }
        this.text = text;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public boolean isBanned() {
        return banned;
    }

    public List<String> getReportedList() {
        return reportedBy;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return isBanned() == message.isBanned() && getCreator().equals(message.getCreator()) && Objects.equals(
                getPublicationDate(), message.getPublicationDate()) && Objects.equals(getText(), message.getText())
                && Objects.equals(getLikedBy(), message.getLikedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreator(), getPublicationDate(), getText(), isBanned(), getLikedBy());
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" + "creator='" + creator + '\'' + ", publicationDate='" + publicationDate + '\'' + ", text='"
                + text + '\'' + ", banned=" + banned + ", likedBy=" + likedBy + '}';
    }
}
