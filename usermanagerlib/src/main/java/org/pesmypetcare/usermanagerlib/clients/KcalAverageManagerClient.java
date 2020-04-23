package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.KcalAverage;
import org.pesmypetcare.usermanagerlib.datacontainers.KcalAverageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class KcalAverageManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/kcalAverage/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static final String SLASH = "/";
    private static final String BRACKET = "{";
    private static final String BRACKET_SLASH = ",\\{";
    private static final Gson GSON = new Gson();
    private TaskManager taskManager;

    public KcalAverageManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a kcalAverage of a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param kcalAverage The kcalAverage entity that contains the attributes of the kcalAverage
     * @param date The date of creation
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createKcalAverage(String accessToken, String owner, String petName,
                                 KcalAverage kcalAverage, DateTime date)
            throws ExecutionException, InterruptedException {
        JSONObject reqJson = kcalAverage.getBody().buildKcalAverageJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                + date, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet's kcalAverage with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the kcalAverage was created
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteByDate(String accessToken, String owner, String petName, DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                + date, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes all the kcalAverages of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllKcalAverage(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a kcalAverage identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the kcalAverage was created
     * @return The KcalAverageData identified by the data
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public KcalAverageData getKcalAverageData(String accessToken, String owner, String petName,
                                              DateTime date)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH + date,
                accessToken).get();
        return GSON.fromJson(json.toString(), KcalAverageData.class);
    }

    /**
     * Gets the data from all the specified kcalAverages from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the kcalAverages from the pet
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<KcalAverage> getAllKcalAverageData(String accessToken, String owner, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName,
                accessToken).get();
        List<KcalAverage> kcalAverageList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] kcalAverages = jsonArray.split(BRACKET_SLASH);
            kcalAverageList.add(GSON.fromJson(kcalAverages[0], KcalAverage.class));
            for (int i = 1; i < kcalAverages.length; i++) {
                kcalAverages[i] = BRACKET + kcalAverages[i];
                kcalAverageList.add(GSON.fromJson(kcalAverages[i], KcalAverage.class));
            }
        }
        return kcalAverageList;
    }

    /**
     * Gets the data from all the kcalAverages of the pet between the initial and final
     * date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the kcalAverages eaten by the pet in the specified time
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public List<KcalAverage> getAllKcalAveragesBetween(String accessToken, String owner,
                                                       String petName, DateTime initialDate,
                                                       DateTime finalDate)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName
                + "/between/" + initialDate + SLASH + finalDate, accessToken).get();
        List<KcalAverage> kcalAverageList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] kcalAverages = jsonArray.split(BRACKET_SLASH);
            kcalAverageList.add(GSON.fromJson(kcalAverages[0], KcalAverage.class));
            for (int i = 1; i < kcalAverages.length; i++) {
                kcalAverages[i] = BRACKET + kcalAverages[i];
                kcalAverageList.add(GSON.fromJson(kcalAverages[i], KcalAverage.class));
            }
        }
        return kcalAverageList;
    }

    /**
     * Updates the kcalAverage's value.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the kcalAverage was created
     * @param value Value the kcalAverage will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateKcalAverageField(String accessToken, String owner, String petName,
                                      DateTime date, double value)
            throws ExecutionException, InterruptedException {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("value", value);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + owner + SLASH + petName + SLASH
                + date, accessToken).get();
        return Integer.parseInt(response.toString());
    }

}
