package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Marc Simó
 */
public class Point {
    private Double latitude;
    private Double longitude;

    /**
     * Point constructor.
     */
    public Point() { }

    /**
     * Point constructor specifying coordinates.
     * @param latitude Point latitude
     * @param longitud Point longitude
     */
    public Point(Double latitude, Double longitud) {
        this.latitude = latitude;
        this.longitude = longitud;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "latitude=" + latitude
            + ", longitude=" + longitude
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Point) {
            return ((Point) obj).getLatitude().equals(this.getLatitude())
                && ((Point) obj).getLongitude().equals(this.getLongitude());
        }
        return false;
    }
}
