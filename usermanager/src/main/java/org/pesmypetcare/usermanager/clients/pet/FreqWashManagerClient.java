package org.pesmypetcare.usermanager.clients.pet;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.pet.FreqWash;
import org.pesmypetcare.usermanager.datacontainers.pet.FreqWashData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.pesmypetcare.httptools.utilities.DateTime.convertLocaltoUTC;

public class FreqWashManagerClient {
    private static final String BASE_URL = BuildConfig.URL + "freqWash/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static final String SLASH = "/";
    private static final String BRACKET = "{";
    private static final String BRACKET_SLASH = ",\\{";
    private static final Gson GSON = new Gson();
    private TaskManager taskManager;

    public FreqWashManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a freqWash of a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param freqWash The freqWash entity that contains the attributes of the freqWash
     * @param date The date of creation
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createFreqWash(String accessToken, String owner, String petName, FreqWash freqWash,
                            DateTime date) throws ExecutionException, InterruptedException {
        JSONObject reqJson = freqWash.getBody().buildFreqWashJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                        + convertLocaltoUTC(date), accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet's freqWash with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the freqWash was created
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
     * Deletes all the freqWashs of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllFreqWash(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a freqWash identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the freqWash was created
     * @return The FreqWashData identified by the data
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public FreqWashData getFreqWashData(String accessToken, String owner, String petName,
                                        DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                + convertLocaltoUTC(date), accessToken).get();
        return GSON.fromJson(json.toString(), FreqWashData.class);
    }

    /**
     * Gets the data from all the specified freqWashs from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the freqWashs from the pet
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<FreqWash> getAllFreqWashData(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        List<FreqWash> freqWashList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] freqWashs = jsonArray.split(BRACKET_SLASH);
            freqWashList.add(GSON.fromJson(freqWashs[0], FreqWash.class));
            for (int i = 1; i < freqWashs.length; i++) {
                freqWashs[i] = BRACKET + freqWashs[i];
                freqWashList.add(GSON.fromJson(freqWashs[i], FreqWash.class));
            }
        }
        return freqWashList;
    }

    /**
     * Gets the data from all the freqWashs of the pet between the initial and final
     * date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the freqWashs eaten by the pet in the specified time
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<FreqWash> getAllFreqWashsBetween(String accessToken, String owner, String petName,
                                             DateTime initialDate, DateTime finalDate)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName
                + "/between/" + convertLocaltoUTC(initialDate) + SLASH
                + convertLocaltoUTC(finalDate), accessToken).get();
        List<FreqWash> freqWashList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] freqWashs = jsonArray.split(BRACKET_SLASH);
            freqWashList.add(GSON.fromJson(freqWashs[0], FreqWash.class));
            for (int i = 1; i < freqWashs.length; i++) {
                freqWashs[i] = BRACKET + freqWashs[i];
                freqWashList.add(GSON.fromJson(freqWashs[i], FreqWash.class));
            }
        }
        return freqWashList;
    }

    /**
     * Updates the freqWash's value.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the freqWash was created
     * @param value Value the freqWash will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateFreqWashField(String accessToken, String owner, String petName, DateTime date,
                               double value) throws ExecutionException, InterruptedException {
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
