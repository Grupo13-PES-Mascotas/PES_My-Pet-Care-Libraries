package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;

public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private int seconds;

    /**
     * Constructs a Date with the specified parameters
     * @param year Year
     * @param month Month
     * @param day Day
     * @param hour Hour
     * @param minutes Minutes
     * @param seconds Seconds
     */
    public Date(int year, int month, int day, int hour, int minutes, int seconds) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @NonNull
    @Override
    public String toString(){
        return year + "-" + month + "-" + day + "T" + hour + ":" + minutes + ":" + seconds;
    }
}
