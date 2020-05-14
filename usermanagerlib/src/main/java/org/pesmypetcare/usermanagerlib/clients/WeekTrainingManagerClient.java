package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.WeekTraining;
import org.pesmypetcare.usermanagerlib.datacontainers.WeekTrainingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.pesmypetcare.usermanagerlib.datacontainers.DateTime.convertLocaltoUTC;

public class WeekTrainingManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/weekTraining/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static final String SLASH = "/";
    private static final String BRACKET = "{";
    private static final String BRACKET_SLASH = ",\\{";
    private static final Gson GSON = new Gson();
    private TaskManager taskManager;

    public WeekTrainingManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a weekTraining of a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param weekTraining The weekTraining entity that contains the attributes of the weekTraining
     * @param date The date of creation
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createWeekTraining(String accessToken, String owner, String petName,
                                  WeekTraining weekTraining, DateTime date)
            throws ExecutionException, InterruptedException {
        JSONObject reqJson = weekTraining.getBody().buildWeekTrainingJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                        + convertLocaltoUTC(date), accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet's weekTraining with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the weekTraining was created
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteByDate(String accessToken, String owner, String petName, DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                        + convertLocaltoUTC(date), accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes all the weekTrainings of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllWeekTraining(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a weekTraining identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the weekTraining was created
     * @return The WeekTrainingData identified by the data
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public WeekTrainingData getWeekTrainingData(String accessToken, String owner, String petName,
                                                DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                        + convertLocaltoUTC(date), accessToken).get();
        return GSON.fromJson(json.toString(), WeekTrainingData.class);
    }

    /**
     * Gets the data from all the specified weekTrainings from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the weekTrainings from the pet
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<WeekTraining> getAllWeekTrainingData(String accessToken, String owner,
                                                     String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        List<WeekTraining> weekTrainingList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] weekTrainings = jsonArray.split(BRACKET_SLASH);
            weekTrainingList.add(GSON.fromJson(weekTrainings[0], WeekTraining.class));
            for (int i = 1; i < weekTrainings.length; i++) {
                weekTrainings[i] = BRACKET + weekTrainings[i];
                weekTrainingList.add(GSON.fromJson(weekTrainings[i], WeekTraining.class));
            }
        }
        return weekTrainingList;
    }

    /**
     * Gets the data from all the weekTrainings of the pet between the initial and final
     * date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the weekTrainings eaten by the pet in the specified time
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<WeekTraining> getAllWeekTrainingsBetween(String accessToken, String owner,
                                                         String petName,
                                             DateTime initialDate, DateTime finalDate)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName
                + "/between/" + convertLocaltoUTC(initialDate) + SLASH
                + convertLocaltoUTC(finalDate), accessToken).get();
        List<WeekTraining> weekTrainingList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] weekTrainings = jsonArray.split(BRACKET_SLASH);
            weekTrainingList.add(GSON.fromJson(weekTrainings[0], WeekTraining.class));
            for (int i = 1; i < weekTrainings.length; i++) {
                weekTrainings[i] = BRACKET + weekTrainings[i];
                weekTrainingList.add(GSON.fromJson(weekTrainings[i], WeekTraining.class));
            }
        }
        return weekTrainingList;
    }

    /**
     * Updates the weekTraining's value.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the weekTraining was created
     * @param value Value the weekTraining will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateWeekTrainingField(String accessToken, String owner, String petName,
                                       DateTime date, double value)
            throws ExecutionException, InterruptedException {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("value", value);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                        + convertLocaltoUTC(date), accessToken).get();
        return Integer.parseInt(response.toString());
    }

}
