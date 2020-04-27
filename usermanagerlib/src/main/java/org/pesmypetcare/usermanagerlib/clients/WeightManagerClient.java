package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Weight;
import org.pesmypetcare.usermanagerlib.datacontainers.WeightData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WeightManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/weight/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static final String SLASH = "/";
    private static final String BRACKET = "{";
    private static final String BRACKET_SLASH = ",\\{";
    private static final Gson GSON = new Gson();
    private TaskManager taskManager;

    public WeightManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a weight of a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param weight The weight entity that contains the attributes of the weight
     * @param date The date of creation
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createWeight(String accessToken, String owner, String petName, Weight weight,
                            DateTime date) throws ExecutionException, InterruptedException {
        JSONObject reqJson = weight.getBody().buildWeightJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH + date,
            accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet's weight with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the weight was created
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteByDate(String accessToken, String owner, String petName, DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH + date,
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes all the weights of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllWeight(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a weight identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the weight was created
     * @return The WeightData identified by the data
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public WeightData getWeightData(String accessToken, String owner, String petName, DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH + date,
                accessToken).get();
        return GSON.fromJson(json.toString(), WeightData.class);
    }

    /**
     * Gets the data from all the specified weights from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the weights from the pet
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<Weight> getAllWeightData(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        List<Weight> weightList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] weights = jsonArray.split(BRACKET_SLASH);
            weightList.add(GSON.fromJson(weights[0], Weight.class));
            for (int i = 1; i < weights.length; i++) {
                weights[i] = BRACKET + weights[i];
                weightList.add(GSON.fromJson(weights[i], Weight.class));
            }
        }
        return weightList;
    }

    /**
     * Gets the data from all the weights of the pet between the initial and final
     * date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the weights eaten by the pet in the specified time
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<Weight> getAllWeightsBetween(String accessToken, String owner, String petName,
                                             DateTime initialDate, DateTime finalDate)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + "/between/"
                        + initialDate + SLASH + finalDate, accessToken).get();
        List<Weight> weightList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] weights = jsonArray.split(BRACKET_SLASH);
            weightList.add(GSON.fromJson(weights[0], Weight.class));
            for (int i = 1; i < weights.length; i++) {
                weights[i] = BRACKET + weights[i];
                weightList.add(GSON.fromJson(weights[i], Weight.class));
            }
        }
        return weightList;
    }

    /**
     * Updates the weight's value.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the weight was created
     * @param value Value the weight will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateWeightField(String accessToken, String owner, String petName, DateTime date,
                               double value) throws ExecutionException, InterruptedException {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("value", value);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH + date,
            accessToken).get();
        return Integer.parseInt(response.toString());
    }

}
