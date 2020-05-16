package org.pesmypetcare.usermanager.datacontainers.pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author Marc Simó & Santiago Del Rey
 */
public class PetData {
    public static final String MEALS = "meals";
    public static final String WEIGHTS = "weights";
    public static final String EXERCISES = "exercises";
    public static final String WASHES = "washes";
    public static final String VACCINATIONS = "vaccinations";
    public static final String ILLNESSES = "illnesses";
    public static final String MEDICATIONS = "medications";
    public static final String VET_VISITS = "vet_visits";
    public static final String GENDER = "gender";
    public static final String BIRTH = "birth";
    public static final String BREED = "breed";
    public static final String PATHOLOGIES = "pathologies";
    public static final String RECOMMENDED_KCAL = "recommendedKcal";
    public static final String NEEDS = "needs";
    private static final String DESCRIPTION_KEY = "description";
    private static final String END_DATE_TIME_KEY = "endDateTime";
    private static final String DURATION_KEY = "duration";
    private GenderType gender;
    private String breed;
    private String birth;
    private String pathologies;
    private String needs;
    private Double recommendedKcal;

    public PetData() { }

    public PetData(GenderType gender, String breed, String birth, String pathologies, String needs,
                   Double recommendedKcal) {
        this.gender = gender;
        this.breed = breed;
        this.birth = birth;
        this.pathologies = pathologies;
        this.needs = needs;
        this.recommendedKcal = recommendedKcal;
    }

    /**
     * The method that returns the pet gender.
     * @return The pet's gender
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * The method that set the pet gender.
     * @param gender The pet's gender
     */
    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    /**
     * The method that returns the pet breed.
     * @return The pet's breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * The method that set the pet breed.
     * @param breed The pet's breed
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * The method that returns the pet birth.
     * @return The pet's birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * The method that set the pet birth.
     * @param birth The pet's birth
     */
    public void setBirth(String birth) {
        checkDateFormat(birth);
        this.birth = birth;
    }

    /**
     * The method that returns the pet needs.
     * @return The pet's needs
     */
    public String getNeeds() {
        return needs;
    }

    /**
     * The method that set the pet needs.
     * @param needs The pet's needs
     */
    public void setNeeds(String needs) {
        this.needs = needs;
    }

    /**
     * The method that returns the pet pathologies.
     * @return The pet's pathologies
     */
    public String getPathologies() {
        return pathologies;
    }

    /**
     * The method that set the pet pathologies.
     * @param pathologies The pet's pathologies
     */
    public void setPathologies(String pathologies) {
        this.pathologies = pathologies;
    }

    /**
     * The method that returns the pet recommended kcal.
     * @return The pet's recommended kcal
     */
    public Double getRecommendedKcal() {
        return recommendedKcal;
    }

    /**
     * The method that set the pet recommended kcal.
     * @param recommendedKcal The pet's recommended kcal
     */
    public void setRecommendedKcal(Double recommendedKcal) {
        this.recommendedKcal = recommendedKcal;
    }

    /**
     * Checks that field has the correct format for a Pet simple attribute.
     * @param field Name of the attribute.
     */
    public static void checkSimpleField(String field) {
        if (!GENDER.equals(field) && !BREED.equals(field) && !BIRTH.equals(field)
            && !PATHOLOGIES.equals(field) && !NEEDS.equals(field) && !RECOMMENDED_KCAL.equals(field)) {
            throw new IllegalArgumentException("Field does not exists");
        }
    }

    /**
     * Checks that the field and the new value for this field have the correct format for a Pet simple attribute.
     * @param field Name of the attribute.
     * @param newValue Value of the attribute.
     */
    public static void checkSimpleFieldAndValues(String field, Object newValue) {
        if ((field.equals(BIRTH) || field.equals(BREED) || field.equals(PATHOLOGIES)
            || field.equals(NEEDS)) && !(newValue instanceof String)) {
            throw new IllegalArgumentException("New value must be a String");
        } else if (field.equals(RECOMMENDED_KCAL) && !(newValue instanceof Double)) {
            throw new IllegalArgumentException("New value must be a Double");
        } else if (field.equals(GENDER) && !"Male".equals(newValue) && !"Female".equals(newValue)
            && !"Other".equals(newValue)) {
            throw new IllegalArgumentException("New value must be a GenderType");
        }
    }

    /**
     * Checks that field has the correct format for a Pet collection attribute.
     * @param field Name of the attribute collection. Possible fields: meals, trainings, washes, weights
     */
    public static void checkCollectionField(String field) {
        if (!MEALS.equals(field) && !WEIGHTS.equals(field) && !EXERCISES.equals(field) && !WASHES.equals(field)
            && !VACCINATIONS.equals(field) && !ILLNESSES.equals(field) && !MEDICATIONS.equals(field)
            && !VET_VISITS.equals(field)) {
            throw new IllegalArgumentException("Field does not exists");
        }
    }

    /**
     * Checks that field, key and body have the correct format of a Pet attribute.
     * @param field Name of the attribute collection. Possible fields: meals, trainings, washes, weights
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkCollectionKeyAndBody(String field, String key, Map<String, Object> body) {
        switch (field) {
            case MEALS:
                checkMeals(key, body);
                break;
            case WEIGHTS:
                checkDateAndValueInteger(key, body);
                break;
            case EXERCISES:
                checkExercises(key, body);
                break;
            case WASHES:
                checkWashes(key, body);
                break;
            case VACCINATIONS:
                checkVaccinations(key, body);
                break;
            case ILLNESSES:
                checkIllnesses(key, body);
                break;
            case MEDICATIONS:
                checkMedications(key, body);
                break;
            case VET_VISITS:
                checkVetVisits(key, body);
                break;
            default:
                throw new IllegalArgumentException("Field does not exists");
        }
    }

    /**
     * Checks that key and body have the correct format for a meal.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkMeals(String key, Map<String, Object> body) {
        checkDateFormat(key);
        String kcalKey = "kcal";
        String mealNameKey = "mealName";
        if (body.size() != 2 || !body.containsKey(kcalKey) || !body.containsKey(mealNameKey)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if ((!(body.get(kcalKey) instanceof Double) && !(body.get(kcalKey) instanceof Integer))
            || !(body.get(mealNameKey) instanceof String)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
    }

    /**
     * Checks that key and body have the correct format for a date key and body with one element whose key is 'value'
     * and has an Object of type Integer.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkDateAndValueInteger(String key, Map<String, Object> body) {
        checkDateFormat(key);
        String valueKey = "value";
        if (body.size() != 1 || !body.containsKey(valueKey)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if (!(body.get(valueKey) instanceof Integer)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
    }

    /**
     * Checks that key and body have the correct format for an exercise.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkExercises(String key, Map<String, Object> body) {
        checkDateFormat(key);
        String nameKey = "name";
        String coordinatesKey = "coordinates";
        if (body.size() != 4 || !body.containsKey(nameKey) || !body.containsKey(DESCRIPTION_KEY)
            || !body.containsKey(END_DATE_TIME_KEY) || !body.containsKey(coordinatesKey)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if (!(body.get(nameKey) instanceof String) && !(body.get(DESCRIPTION_KEY) instanceof String)
            || !(body.get(coordinatesKey) instanceof List) || !(body.get(END_DATE_TIME_KEY) instanceof String)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }

        checkDateFormat((String) body.get(END_DATE_TIME_KEY));
    }

    /**
     * Checks that key and body have the correct format for a wash.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkWashes(String key, Map<String, Object> body) {
        checkDateFormat(key);
        if (body.size() != 2 || !body.containsKey(DESCRIPTION_KEY) || !body.containsKey(DURATION_KEY)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if (!(body.get(DESCRIPTION_KEY) instanceof String) || !(body.get(DURATION_KEY) instanceof Integer)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
    }

    /**
     * Checks that key and body have the correct format for a vaccination.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkVaccinations(String key, Map<String, Object> body) {
        checkDateFormat(key);
        if (body.size() != 1 || !body.containsKey(DESCRIPTION_KEY)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if (!(body.get(DESCRIPTION_KEY) instanceof String)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
    }

    /**
     * Checks that key and body have the correct format for an illness.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkIllnesses(String key, Map<String, Object> body) {
        checkDateFormat(key);
        String typeKey = "type";
        String severityKey = "severity";
        if (body.size() != 4 || !body.containsKey(END_DATE_TIME_KEY) || !body.containsKey(typeKey)
            || !body.containsKey(DESCRIPTION_KEY) || !body.containsKey(severityKey)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if (!(body.get(END_DATE_TIME_KEY) instanceof String) || !(body.get(typeKey) instanceof String)
            || !(body.get(DESCRIPTION_KEY) instanceof String) || !(body.get(severityKey) instanceof String)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        checkDateFormat((String) body.get(END_DATE_TIME_KEY));
        checkTypeValue((String) body.get(typeKey));
        checkSeverityValue((String) body.get(severityKey));
    }

    /**
     * Checks that key and body have the correct format for a medication.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkMedications(String key, Map<String, Object> body) {
        checkDatePlusNameFormat(key);
        String quantityKey = "quantity";
        String periodicityKey = "periodicity";
        if (body.size() != 3 || !body.containsKey(quantityKey) || !body.containsKey(DURATION_KEY)
            || !body.containsKey(periodicityKey)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if ((!(body.get(quantityKey) instanceof Double) && !(body.get(quantityKey) instanceof Integer))
            || !(body.get(DURATION_KEY) instanceof Integer) || !(body.get(periodicityKey) instanceof Integer)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
    }

    /**
     * Checks that key and body have the correct format for a vet visit.
     * @param key Key of the attribute
     * @param body Body of the attribute
     */
    public static void checkVetVisits(String key, Map<String, Object> body) {
        checkDateFormat(key);
        String reasonKey = "reason";
        String addressKey = "address";
        if (body.size() != 2 || !body.containsKey(reasonKey) || !body.containsKey(addressKey)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
        if (!(body.get(reasonKey) instanceof String) || !(body.get(addressKey) instanceof String)) {
            throw new IllegalArgumentException("Request body does not have a correct format");
        }
    }

    /**
     * Checks wether the severity value is valid or not.
     * @param severity Severity value
     */
    private static void checkSeverityValue(String severity) {
        if (!"Low".equals(severity) && !"Medium".equals(severity) && !"High".equals(severity)) {
            throw new IllegalArgumentException("Incorrect severity format");
        }
    }

    /**
     * Checks wether type value is valid or not.
     * @param type Type value
     */
    private static void checkTypeValue(String type) {
        if (!"Normal".equals(type) && !"Allergy".equals(type)) {
            throw new IllegalArgumentException("Incorrect severity format");
        }
    }

    /**
     * Checks that the string date follows the specified format.
     * @param date String that contains a date
     */
    public static void checkDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Incorrect date format");
        }
    }

    /**
     * Checks that the string key follows the specified format.
     * @param key String to checked
     */
    public static void checkDatePlusNameFormat(String key) {
        if (!key.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}-.*")) {
            throw new IllegalArgumentException("Incorrect date plus name format");
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "gender=" + gender
            + ", breed='" + breed + '\''
            + ", birth=" + birth
            + ", pathologies='" + pathologies + '\''
            + ", needs='" + needs + '\''
            + ", recommendedKcal=" + recommendedKcal
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PetData) {
            return ((PetData) obj).getBirth().equals(this.getBirth())
                && ((PetData) obj).getBreed().equals(this.getBreed())
                && ((PetData) obj).getGender() == this.getGender()
                && ((PetData) obj).getPathologies().equals(this.getPathologies())
                && ((PetData) obj).getRecommendedKcal().equals(this.getRecommendedKcal())
                && ((PetData) obj).getNeeds().equals(this.getNeeds());
        }
        return false;
    }
}
