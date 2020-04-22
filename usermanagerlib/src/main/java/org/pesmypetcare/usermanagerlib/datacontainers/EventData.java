package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class EventData {
    public static final String LAVENDER = "1";
    public static final String SAGE = "2";
    public static final String GRAPE = "3";
    public static final String FLAMINGO = "4";
    public static final String BANANA = "5";
    public static final String TANGERINE = "6";
    public static final String PEACKOCK = "7";
    public static final String GRAPHITE = "8";
    public static final String BLUEBERRY = "9";
    public static final String BASIL = "10";
    public static final String TOMATE = "11";
    private static final String[] COLORS = {LAVENDER, SAGE, GRAPE, FLAMINGO, BANANA, TANGERINE, PEACKOCK, GRAPHITE,
        BLUEBERRY, BASIL, TOMATE};
    private String id;
    private String summary;
    private String location;
    private String description;
    private String color;
    private Integer emailReminderMinutes;
    private Integer repetitionInterval;
    private String startDate;
    private String endDate;

    public EventData() { }

    public EventData(String id, String summary, String location, String description, String color,
                     Integer emailReminderMinutes, Integer repetitionInterval, String startDate, String endDate) {
        checkCorrectDateFormat(startDate);
        checkCorrectDateFormat(endDate);
        DateTime date = DateTime.Builder.buildFullString(startDate);
        System.out.println(date.toString());
        checkCorrectColor(color);
        this.id = id;
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.color = color;
        this.emailReminderMinutes = emailReminderMinutes;
        this.repetitionInterval = repetitionInterval;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        checkCorrectColor(color);
        this.color = color;
    }

    public Integer getEmailReminderMinutes() {
        return emailReminderMinutes;
    }

    public void setEmailReminderMinutes(Integer emailReminderMinutes) {
        this.emailReminderMinutes = emailReminderMinutes;
    }

    public Integer getRepetitionInterval() {
        return repetitionInterval;
    }

    public void setRepetitionInterval(Integer repetitionInterval) {
        this.repetitionInterval = repetitionInterval;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        checkCorrectDateFormat(startDate);
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        checkCorrectDateFormat(endDate);
        this.endDate = endDate;
    }

    /**
     * Checks that the color value is amongst the possible ones (LAVENDER, SAGE, GRAPE, FLAMINGO, BANANA, TANGERINE,
     * PEACKOCK, GRAPHITE, BLUEBERRY, BASIL or TOMATE)
     * @param color Value of the color to check
     */
    private void checkCorrectColor(String color) {
        List<String> colors = Arrays.asList(COLORS);
        if (!colors.contains(color)) {
            throw new IllegalArgumentException("Google Calendar Event color must be one of the specified");
        }
    }

    /**
     * Checks that a date value has the correct format for an event
     * @param date Value of the date to check
     */
    private void checkCorrectDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        sdf.setLenient(false);
        try {
            Date javaDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Google Calendar Event Date must follow the format: \"yyyy-MM-dd'T'HH:mm:ss\"");
        }
    }

    /**
     * Creates a meal json object.
     * @return A JSON Object with the meal data
     */
    public JSONObject buildJson() {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("id", id);
        reqData.put("summary", summary);
        reqData.put("location", location);
        reqData.put("description", description);
        reqData.put("color", color);
        reqData.put("emailReminderMinutes", Integer.toString(emailReminderMinutes));
        reqData.put("repetitionInterval", Integer.toString(repetitionInterval));
        reqData.put("startDate", startDate);
        reqData.put("endDate", endDate);
        return new JSONObject(reqData);
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "id='" + id + '\''
            + "summary='" + summary + '\''
            + "location='" + location + '\''
            + "description='" + description + '\''
            + "color='" + color + '\''
            + "emailReminderMinutes='" + emailReminderMinutes + '\''
            + "repetitionInterval='" + repetitionInterval + '\''
            + "startDate='" + startDate + '\''
            + "endDate='" + endDate
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof EventData) {
            return ((EventData) obj).getId().equals(this.getId())
                && ((EventData) obj).getSummary().equals(this.getSummary())
                && ((EventData) obj).getLocation().equals(this.getLocation())
                && ((EventData) obj).getDescription().equals(this.getDescription())
                && ((EventData) obj).getColor().equals(this.getColor())
                && ((EventData) obj).getEmailReminderMinutes().equals(this.getEmailReminderMinutes())
                && ((EventData) obj).getRepetitionInterval().equals(this.getRepetitionInterval())
                && ((EventData) obj).getStartDate().equals(this.getStartDate())
                && ((EventData) obj).getEndDate().equals(this.getEndDate());
        }
        return false;
    }
}
