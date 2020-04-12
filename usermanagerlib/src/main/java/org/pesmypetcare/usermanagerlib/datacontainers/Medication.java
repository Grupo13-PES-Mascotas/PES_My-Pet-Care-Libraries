package org.pesmypetcare.usermanagerlib.datacontainers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Medication {
    private String dateName;
    private MedicationData body;

    public Medication(String dateName, MedicationData body) {
        this.dateName = dateName;
        this.body = body;
    }

    /**
     * Returns the dateName of the Medication
     * @return Meal date
     */
    public String getDateName() {
        return this.dateName;
    }

    /**
     * Sets a new dateName.
     * @param dateName medication date+Name
     */
    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    /**
     * Returns the Medication Data.
     * @return Medication Data
     */
    public MedicationData getBody() {
        return body;
    }

    /**
     * Sets a new Medication Data.
     * @param body Medication Data
     */
    public void setBody(MedicationData body) {
        this.body = body;
    }

    public String[] getDateNameSeparated(){ return this.dateName.split("%"); }

    public String joinDateName(String date, String Name){ return date + '%' + Name; }


    @NonNull
    @Override
    public String toString() {
        String[] aux = this.getDateNameSeparated();
        return "{"
                + "date='" + aux[0] + '\''
                + "name='" + aux[1] + '\''
                + ", body=" + body
                + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Medication) {
            return ((Medication) obj).getDateName().equals(this.getDateName())
                    && ((Medication) obj).getBody().equals(this.getBody());
        }
        return false;
    }
}
