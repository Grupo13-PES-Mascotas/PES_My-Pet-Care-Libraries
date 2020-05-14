package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.FreqTraining;
import org.pesmypetcare.usermanagerlib.datacontainers.FreqTrainingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.pesmypetcare.usermanagerlib.datacontainers.DateTime.convertLocaltoUTC;

public class FreqTrainingManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/freqTraining/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static final String SLASH = "/";
    private static final String BRACKET = "{";
    private static final String BRACKET_SLASH = ",\\{";
    private static final Gson GSON = new Gson();
    private TaskManager taskManager;

    public FreqTrainingManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a freqTraining of a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param freqTraining The freqTraining entity that contains the attributes of the freqTraining
     * @param date The date of creation
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createFreqTraining(String accessToken, String owner, String petName,
                                  FreqTraining freqTraining,
                            DateTime date) throws ExecutionException, InterruptedException {
        JSONObject reqJson = freqTraining.getBody().buildFreqTrainingJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                        + convertLocaltoUTC(date), accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet's freqTraining with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the freqTraining was created
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
     * Deletes all the freqTrainings of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllFreqTraining(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a freqTraining identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the freqTraining was created
     * @return The FreqTrainingData identified by the data
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public FreqTrainingData getFreqTrainingData(String accessToken, String owner, String petName,
                                                DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH + convertLocaltoUTC(date),
                accessToken).get();
        return GSON.fromJson(json.toString(), FreqTrainingData.class);
    }

    /**
     * Gets the data from all the specified freqTrainings from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the freqTrainings from the pet
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<FreqTraining> getAllFreqTrainingData(String accessToken, String owner,
                                                     String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        List<FreqTraining> freqTrainingList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] freqTrainings = jsonArray.split(BRACKET_SLASH);
            freqTrainingList.add(GSON.fromJson(freqTrainings[0], FreqTraining.class));
            for (int i = 1; i < freqTrainings.length; i++) {
                freqTrainings[i] = BRACKET + freqTrainings[i];
                freqTrainingList.add(GSON.fromJson(freqTrainings[i], FreqTraining.class));
            }
        }
        return freqTrainingList;
    }

    /**
     * Gets the data from all the freqTrainings of the pet between the initial and final
     * date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the freqTrainings eaten by the pet in the specified time
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<FreqTraining> getAllFreqTrainingsBetween(String accessToken, String owner,
                                                         String petName,
                                             DateTime initialDate, DateTime finalDate)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName
                + "/between/" + convertLocaltoUTC(initialDate) + SLASH + convertLocaltoUTC(finalDate),
                accessToken).get();
        List<FreqTraining> freqTrainingList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] freqTrainings = jsonArray.split(BRACKET_SLASH);
            freqTrainingList.add(GSON.fromJson(freqTrainings[0], FreqTraining.class));
            for (int i = 1; i < freqTrainings.length; i++) {
                freqTrainings[i] = BRACKET + freqTrainings[i];
                freqTrainingList.add(GSON.fromJson(freqTrainings[i], FreqTraining.class));
            }
        }
        return freqTrainingList;
    }

    /**
     * Updates the freqTraining's value.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the freqTraining was created
     * @param value Value the freqTraining will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateFreqTrainingField(String accessToken, String owner, String petName,
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
