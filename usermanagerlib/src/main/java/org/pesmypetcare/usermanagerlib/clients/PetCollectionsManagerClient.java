package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Exercise;
import org.pesmypetcare.usermanagerlib.datacontainers.ExerciseData;
import org.pesmypetcare.usermanagerlib.datacontainers.Illness;
import org.pesmypetcare.usermanagerlib.datacontainers.IllnessData;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.datacontainers.Medication;
import org.pesmypetcare.usermanagerlib.datacontainers.MedicationData;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;
import org.pesmypetcare.usermanagerlib.datacontainers.Vaccination;
import org.pesmypetcare.usermanagerlib.datacontainers.VaccinationData;
import org.pesmypetcare.usermanagerlib.datacontainers.VetVisit;
import org.pesmypetcare.usermanagerlib.datacontainers.VetVisitData;
import org.pesmypetcare.usermanagerlib.datacontainers.Wash;
import org.pesmypetcare.usermanagerlib.datacontainers.WashData;
import org.pesmypetcare.usermanagerlib.datacontainers.Weight;
import org.pesmypetcare.usermanagerlib.datacontainers.WeightData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Marc Simó
 */
public class PetCollectionsManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private static final String PETS_PATH = "pet/";
    private static final String PUT = "PUT";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String SLASH = "/";
    private final Gson GSON = new Gson();
    private TaskManager taskManager;

    public PetCollectionsManagerClient() {
        taskManager = new TaskManager();
    }

    /**
     * Gets the data from all the specified exercises from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the exercises from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Exercise> getAllExercises(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/exercises", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{\"body\"");
        List<Exercise> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Exercise.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{\"body\"" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Exercise.class));
        }
        return result;
    }

    /**
     * Gets the data from all the exercises done by the pet between the initial and final date including both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The exercises between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Exercise> getExercisesBetween(String accessToken, String username, String petName, String key1,
                                              String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/exercises/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{\"body\"");
        List<Exercise> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Exercise.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{\"body\"" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Exercise.class));
        }
        return result;
    }

    /**
     * Gets a exercise identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the exercise was done
     * @return The ExerciseData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public ExerciseData getExercise(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/exercises/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), ExerciseData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified illnesses from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the illnesses from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Illness> getAllIllnesses(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/illnesses", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Illness> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Illness.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Illness.class));
        }
        return result;
    }

    /**
     * Gets the data from all the illnesses acquired by the pet between the initial and final date including both of
     * them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The illnesses between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Illness> getIllnessesBetween(String accessToken, String username, String petName, String key1,
                                             String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/illnesses/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Illness> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Illness.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Illness.class));
        }
        return result;
    }

    /**
     * Gets an illness identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the illness was acquired
     * @return The IllnessData identified by its pet and date
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public IllnessData getIllness(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/illnesses/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), IllnessData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified meals from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the meals from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Meal> getAllMeals(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/meals", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Meal> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Meal.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Meal.class));
        }
        return result;
    }

    /**
     * Gets the data from all the meals eaten by the pet between the initial and final date including both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The meals between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Meal> getMealsBetween(String accessToken, String username, String petName, String key1, String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/meals/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Meal> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Meal.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Meal.class));
        }
        return result;
    }

    /**
     * Gets a meal identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the meal was eaten
     * @return The MealData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public MealData getMeal(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/meals/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), MealData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified washes from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the washes from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Wash> getAllWashes(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/washes", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Wash> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Wash.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Wash.class));
        }
        return result;
    }

    /**
     * Gets the data from all the washes eaten by the pet between the initial and final date including both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The washes between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Wash> getWashesBetween(String accessToken, String username, String petName, String key1, String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/washes/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Wash> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Wash.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Wash.class));
        }
        return result;
    }

    /**
     * Gets a wash identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the wash was done
     * @return The WashData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public WashData getWash(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/washes/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), WashData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified weights from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the weights from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Weight> getAllWeights(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/weights", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Weight> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Weight.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Weight.class));
        }
        return result;
    }

    /**
     * Gets the data from all the weights added between the initial and final date including both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The weights between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Weight> getWeightsBetween(String accessToken, String username, String petName, String key1, String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/weights/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Weight> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Weight.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Weight.class));
        }
        return result;
    }

    /**
     * Gets a weight identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the weight was added
     * @return The WeightData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public WeightData getWeight(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/weights/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), WeightData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified medications from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the medications from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Medication> getAllMedications(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/medications", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Medication> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Medication.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Medication.class));
        }
        return result;
    }

    /**
     * Gets the data from all the medications consumed by the pet between the initial and final date including
     * both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one not included)
     * @return The medications between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Medication> getMedicationsBetween(String accessToken, String username, String petName, String key1,
                                                  String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        DateTime date2 = DateTime.Builder.buildFullString(key2);
        date2.addSecond();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/medications/" + key1 + SLASH + date2.toString(), accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Medication> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Medication.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Medication.class));
        }
        return result;
    }

    /**
     * Gets a medication identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the medication was consumed
     * @return The MedicationData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public MedicationData getMedication(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDatePlusNameFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/medications/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), MedicationData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified vaccinations from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the vaccinations from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Vaccination> getAllVaccinations(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/vaccinations", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Vaccination> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Vaccination.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Vaccination.class));
        }
        return result;
    }

    /**
     * Gets the data from all the vaccinations done to the pet between the initial and final date including both of
     * them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The vaccinations between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Vaccination> getVaccinationsBetween(String accessToken, String username, String petName, String key1,
                                                    String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/vaccinations/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<Vaccination> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Vaccination.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Vaccination.class));
        }
        return result;
    }

    /**
     * Gets a vaccination identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the vaccination was done
     * @return The VaccinationData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public VaccinationData getVaccination(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/vaccinations/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), VaccinationData.class);
        }
        return null;
    }

    /**
     * Gets the data from all the specified vet visits from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the vet visits from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<VetVisit> getAllVetVisits(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/vet_visits", accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<VetVisit> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], VetVisit.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], VetVisit.class));
        }
        return result;
    }

    /**
     * Gets the data from all the vet visits done by the pet between the initial and final date including both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The vet visits between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<VetVisit> getVetVisitsBetween(String accessToken, String username, String petName, String key1,
                                              String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/vet_visits/" + key1 + SLASH + key2, accessToken).get();
        if (responseNullOrEmpty(response)) {
            return new ArrayList<>();
        }
        String[] objectArray = splitResponse(response);
        List<VetVisit> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], VetVisit.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], VetVisit.class));
        }
        return result;
    }

    /**
     * Gets a vet visit identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the vet visit was done
     * @return The VetVisitData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public VetVisitData getVetVisit(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/vet_visits/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), VetVisitData.class);
        }
        return null;
    }

    /**
     * Splits the Json response obtained from a get collection.
     * @param response StringBuilder containing the response
     * @return A basic array containing the response split
     */
    private String[] splitResponse(StringBuilder response) {
        String jsonArray = response.substring(1, response.length() - 1);
        return jsonArray.split(",\\{");
    }

    /**
     * Check wether the Json response obtained is null or empty.
     * @param response Json response obtained
     * @return True if response is null or empty, false otherwise
     */
    private boolean responseNullOrEmpty(StringBuilder response) {
        if (response == null) {
            return true;
        }
        return response.length() <= 2;
    }
}
