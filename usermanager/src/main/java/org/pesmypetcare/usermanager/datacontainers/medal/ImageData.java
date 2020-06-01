package org.pesmypetcare.usermanager.datacontainers.medal;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Oriol Catalán
 */
public class ImageData {
    private String uid;
    private String imgName;
    private byte[] img;

    /**
     * Default constructor.
     */
    public ImageData() {
    }

    /**
     * Creates an image data with an uid and an image.
     * @param uid The uid for the image
     * @param img The image as a byte array
     */
    public ImageData(@NonNull String uid, byte[] img) {
        this.uid = uid;
        this.img = Arrays.copyOf(img, img.length);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public byte[] getImg() {
        return Arrays.copyOf(img, img.length);
    }

    public void setImg(byte[] img) {
        this.img = Arrays.copyOf(img, img.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageData imageData = (ImageData) o;
        return getUid().equals(imageData.getUid()) && Objects.equals(getImgName(), imageData.getImgName()) && Arrays
                .equals(getImg(), imageData.getImg());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getUid(), getImgName());
        result = 31 * result + Arrays.hashCode(getImg());
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "ImageData{" + "uid='" + uid + '\'' + ", imageName='" + imgName + '\'' + ", img=" + Arrays.toString(
                img) + '}';
    }
}