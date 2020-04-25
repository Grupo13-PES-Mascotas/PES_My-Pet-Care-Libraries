package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Medication {
    private String date;
    private String name;
    private MedicationData body;

    public Medication(String date, String name, MedicationData body) {
        this.date = date;
        this.name = name;
        this.body = body;
    }

    /**
     * Returns the date of the Medication.
     * @return Medication date.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Returns the name of the Medication.
     * @return Meadication name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets a new date.
     * @param date medication date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets a new name.
     * @param name medication name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Medication Data.
     * @return Medication Data.
     */
    public MedicationData getBody() {
        return body;
    }

    /**
     * Sets a new Medication Data.
     * @param body Medication Data.
     */
    public void setBody(MedicationData body) {
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
                + "date='" + this.date + '\''
                + "name='" + this.name + '\''
                + ", body=" + body
                + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Medication) {
            return ((Medication) obj).getDate().equals(this.getDate())
                    && ((Medication) obj).getName().equals(this.getName())
                    && ((Medication) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
