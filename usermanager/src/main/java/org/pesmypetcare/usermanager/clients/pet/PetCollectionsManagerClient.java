package org.pesmypetcare.usermanager.clients.pet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.pet.Exercise;
import org.pesmypetcare.usermanager.datacontainers.pet.ExerciseData;
import org.pesmypetcare.usermanager.datacontainers.pet.Illness;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessData;
import org.pesmypetcare.usermanager.datacontainers.pet.Meal;
import org.pesmypetcare.usermanager.datacontainers.pet.MealData;
import org.pesmypetcare.usermanager.datacontainers.pet.Medication;
import org.pesmypetcare.usermanager.datacontainers.pet.MedicationData;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.Vaccination;
import org.pesmypetcare.usermanager.datacontainers.pet.VaccinationData;
import org.pesmypetcare.usermanager.datacontainers.pet.VetVisit;
import org.pesmypetcare.usermanager.datacontainers.pet.VetVisitData;
import org.pesmypetcare.usermanager.datacontainers.pet.Wash;
import org.pesmypetcare.usermanager.datacontainers.pet.WashData;
import org.pesmypetcare.usermanager.datacontainers.pet.Weight;
import org.pesmypetcare.usermanager.datacontainers.pet.WeightData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marc Simó & Santiago Del Rey
 */
public class PetCollectionsManagerClient {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String PETS_PATH = "pet/";
    private static final String EXERCISES_PATH = "/collection/exercises";
    private static final String ILLNESSES_PATH = "/collection/illnesses";
    private static final String MEALS_PATH = "/collection/meals/";
    private static final String WASHES_PATH = "/collection/washes";
    private static final String WEIGHTS_PATH = "/collection/weights";
    private static final String MEDICATIONS_PATH = "/collection/medications";
    private static final String VACCINATIONS_PATH = "/collection/vaccinations";
    private static final String VET_VISITS_PATH = "/collection/vet_visits";
    private static final String SLASH = "/";
    private static final String TOKEN_HEADER = "token";
    private final Gson gson = new Gson();
    private HttpClient httpClient;
    private Map<String, String> httpHeaders;

    public PetCollectionsManagerClient() {
        httpClient = new HttpClient();
        httpHeaders = new HashMap<>();
    }

    /**
     * Gets the data from all the specified exercises from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the exercises from the pet
     */
    public List<Exercise> getAllExercises(String accessToken, String username, String petName)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + EXERCISES_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Exercise.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the exercises done by the pet between the initial and final date including both of them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The exercises between the dates
     */
    public List<Exercise> getExercisesBetween(String accessToken, String username, String petName, String key1,
            String key2) throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + EXERCISES_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2), null,
                        httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Exercise.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a exercise identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the exercise was done
     * @return The ExerciseData identified by its pet and date.
     */
    public ExerciseData getExercise(String accessToken, String username, String petName, String key)
            throws MyPetCareException {
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + EXERCISES_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), ExerciseData.class);
    }

    /**
     * Deletes all the exercises with a date previous to the specified one.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param date Specified date (This one not included)
     */
    public void deleteExercisesPreviousToDate(String accessToken, String username, String petName, String date)
            throws MyPetCareException {
        PetData.checkDateFormat(date);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.delete(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                          + "/fullcollection/exercises/" + HttpParameter.encode(date), null, httpHeaders, null);
    }

    /**
     * Gets the data from all the specified illnesses from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the illnesses from the pet
     */
    public List<Illness> getAllIllnesses(String accessToken, String username, String petName)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + ILLNESSES_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Illness.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the illnesses acquired by the pet between the initial and final date including both of
     * them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The illnesses between the dates
     */
    public List<Illness> getIllnessesBetween(String accessToken, String username, String petName, String key1,
            String key2) throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + ILLNESSES_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2), null,
                        httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Illness.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets an illness identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the illness was acquired
     * @return The IllnessData identified by its pet and date
     */
    public IllnessData getIllness(String accessToken, String username, String petName, String key)
            throws MyPetCareException {
        PetData.checkDateFormat(key);
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + ILLNESSES_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), IllnessData.class);
    }

    /**
     * Gets the data from all the specified meals from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the meals from the pet
     */
    public List<Meal> getAllMeals(String accessToken, String username, String petName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + MEALS_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Meal.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the meals eaten by the pet between the initial and final date including both of them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The meals between the dates
     */
    public List<Meal> getMealsBetween(String accessToken, String username, String petName, String key1, String key2)
            throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + MEALS_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2), null,
                        httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Meal.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a meal identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the meal was eaten
     * @return The MealData identified by its pet and date.
     */
    public MealData getMeal(String accessToken, String username, String petName, String key) throws MyPetCareException {
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + MEALS_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), MealData.class);
    }

    /**
     * Gets the data from all the specified washes from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the washes from the pet
     */
    public List<Wash> getAllWashes(String accessToken, String username, String petName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + WASHES_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Wash.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the washes eaten by the pet between the initial and final date including both of them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The washes between the dates
     */
    public List<Wash> getWashesBetween(String accessToken, String username, String petName, String key1, String key2)
            throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + WASHES_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2), null,
                        httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Wash.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a wash identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the wash was done
     * @return The WashData identified by its pet and date.
     */
    public WashData getWash(String accessToken, String username, String petName, String key) throws MyPetCareException {
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + WASHES_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), WashData.class);
    }

    /**
     * Gets the data from all the specified weights from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the weights from the pet
     */
    public List<Weight> getAllWeights(String accessToken, String username, String petName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + WEIGHTS_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Weight.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the weights added between the initial and final date including both of them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The weights between the dates
     */
    public List<Weight> getWeightsBetween(String accessToken, String username, String petName, String key1, String key2)
            throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + WEIGHTS_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2), null,
                        httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Weight.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a weight identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the weight was added
     * @return The WeightData identified by its pet and date.
     */
    public WeightData getWeight(String accessToken, String username, String petName, String key)
            throws MyPetCareException {
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + WEIGHTS_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), WeightData.class);
    }

    /**
     * Gets the data from all the specified medications from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the medications from the pet
     */
    public List<Medication> getAllMedications(String accessToken, String username, String petName)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + MEDICATIONS_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Medication.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the medications consumed by the pet between the initial and final date including
     * both of them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The medications between the dates
     */
    public List<Medication> getMedicationsBetween(String accessToken, String username, String petName, String key1,
            String key2) throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        DateTime date2 = DateTime.Builder.buildFullString(key2);
        date2.addSecond();
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + MEDICATIONS_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter
                             .encode(date2.toString()), null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Medication.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a medication identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the medication was consumed
     * @return The MedicationData identified by its pet and date.
     */
    public MedicationData getMedication(String accessToken, String username, String petName, String key)
            throws MyPetCareException {
        PetData.checkDatePlusNameFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + MEDICATIONS_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), MedicationData.class);
    }

    /**
     * Gets the data from all the specified vaccinations from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the vaccinations from the pet
     */
    public List<Vaccination> getAllVaccinations(String accessToken, String username, String petName)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + VACCINATIONS_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Vaccination.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the vaccinations done to the pet between the initial and final date including both of
     * them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The vaccinations between the dates
     */
    public List<Vaccination> getVaccinationsBetween(String accessToken, String username, String petName, String key1,
            String key2) throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + VACCINATIONS_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2),
                        null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, Vaccination.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a vaccination identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the vaccination was done
     * @return The VaccinationData identified by its pet and date.
     */
    public VaccinationData getVaccination(String accessToken, String username, String petName, String key)
            throws MyPetCareException {
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + VACCINATIONS_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), VaccinationData.class);
    }

    /**
     * Gets the data from all the specified vet visits from the database identified by its pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the vet visits from the pet
     */
    public List<VetVisit> getAllVetVisits(String accessToken, String username, String petName)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + VET_VISITS_PATH, null, httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, VetVisit.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets the data from all the vet visits done by the pet between the initial and final date including both of them.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The vet visits between the dates
     */
    public List<VetVisit> getVetVisitsBetween(String accessToken, String username, String petName, String key1,
            String key2) throws MyPetCareException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + VET_VISITS_PATH + SLASH + HttpParameter.encode(key1) + SLASH + HttpParameter.encode(key2), null,
                        httpHeaders, null);

        Type listType = TypeToken.getParameterized(List.class, VetVisit.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets a vet visit identified by its pet and date.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the vet visit was done
     * @return The VetVisitData identified by its pet and date.
     */
    public VetVisitData getVetVisit(String accessToken, String username, String petName, String key)
            throws MyPetCareException {
        PetData.checkDateFormat(key);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                     + VET_VISITS_PATH + SLASH + HttpParameter.encode(key), null, httpHeaders, null);

        return gson.fromJson(response.asString(), VetVisitData.class);
    }
}
