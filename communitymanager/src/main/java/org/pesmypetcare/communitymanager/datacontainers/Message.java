package org.pesmypetcare.communitymanager.datacontainers;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class Message {
    private ImageData image;
    private MessageData message;

    /**
     * Creates a message with text.
     * @param creator The creator's username
     * @param text The text
     */
    public Message(@NonNull String creator, String text) {
        this.message = new MessageData(creator, text);
    }

    /**
     * Creates a message with an image.
     * @param creator The creator's username
     * @param img The image as a byte array
     */
    public Message(@NonNull String creator, byte[] img) {
        this.message = new MessageData(creator);
        this.image = new ImageData(creator, img);
    }

    /**
     * Creates a message with text and an image.
     * @param creator The creator's username
     * @param text The text
     * @param img The image as a byte array
     */
    public Message(@NonNull String creator, String text, byte[] img) {
        this.message = new MessageData(creator, text);
        this.image = new ImageData(creator, img);
    }

    public String getCreator() {
        return message.getCreator();
    }

    public void setCreator(@NonNull String creator) {
        message.setCreator(creator);
        image.setUid(creator);
    }

    public String getPublicationDate() {
        return message.getPublicationDate();
    }

    public String getText() {
        return message.getText();
    }

    public void setText(String text) {
        message.setText(text);
    }

    public byte[] getImage() {
        return image.getImg();
    }

    public void setImage(byte[] img) {
        image.setImg(img);
    }

    public String getImagePath() {
        return message.getImagePath();
    }


    public boolean isBanned() {
        return message.isBanned();
    }

    public List<String> getLikedBy() {
        return message.getLikedBy();
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
        return Objects.equals(image, message.image) && Objects.equals(this.message, message.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, message);
    }

    @Override
    public String toString() {
        return "Message{" + "imageData=" + image + ", messageData=" + message + '}';
    }

    class ImageData {
        private String uid;
        private byte[] img;

        /**
         * Creates an image data with an uid and an image.
         * @param uid The uid for the image
         * @param img The image as a byte array
         */
        public ImageData(@NonNull String uid, byte[] img) {
            this.uid = uid;
            this.img = img;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public byte[] getImg() {
            return img;
        }

        public void setImg(byte[] img) {
            this.img = img;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ImageData image = (ImageData) o;
            return getUid().equals(image.getUid()) && Arrays.equals(getImg(), image.getImg());
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(getUid());
            result = 31 * result + Arrays.hashCode(getImg());
            return result;
        }

        @Override
        public String toString() {
            return "Image{" + "uid='" + uid + '\'' + ", img=" + Arrays.toString(img) + '}';
        }
    }
}
