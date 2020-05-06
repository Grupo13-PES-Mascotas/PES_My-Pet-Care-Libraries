package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;
import org.pesmypetcare.usermanagerlib.datacontainers.Training;
import org.pesmypetcare.usermanagerlib.datacontainers.TrainingData;
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
    private final Gson GSON = new Gson();
    private TaskManager taskManager;
    private static final String PUT = "PUT";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String SLASH = "/";

    public PetCollectionsManagerClient() {
        taskManager = new TaskManager();
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
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
     * Gets the data from all the specified trainings from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @return The List containing all the trainings from the pet
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Training> getAllTrainings(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/trainings", accessToken).get();
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
        List<Training> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Training.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Training.class));
        }
        return result;
    }

    /**
     * Gets the data from all the trainings done by the pet between the initial and final date including both of them.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key1 Start date (This one included)
     * @param key2 End date (This one included)
     * @return The trainings between the dates
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Training> getTrainingsBetween(String accessToken, String username, String petName, String key1,
                                           String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key1);
        PetData.checkDateFormat(key2);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/trainings/" + key1 + SLASH + key2, accessToken).get();
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
        List<Training> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], Training.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], Training.class));
        }
        return result;
    }

    /**
     * Gets a training identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param key Date the training was done
     * @return The TrainingData identified by its pet and date.
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public TrainingData getTraining(String accessToken, String username, String petName, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkDateFormat(key);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/trainings/" + key, accessToken).get();
        if (response != null) {
            return GSON.fromJson(response.toString(), TrainingData.class);
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
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
}
