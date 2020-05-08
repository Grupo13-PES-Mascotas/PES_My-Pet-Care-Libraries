package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Marc Simó
 */
public class MealManagerClient {
    public static final String MEALNAME = "mealName";
    public static final String KCAL = "kcal";
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/meal/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static Gson GSON = new Gson();
    private TaskManager taskManager;

    public MealManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a meal eaten by a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param meal The meal entity that contains the attributes of the meal eaten by the pet
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createMeal(String accessToken, String owner, String petName, Meal meal) throws ExecutionException,
        InterruptedException {
        JSONObject reqJson = meal.getBody().buildJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName + "/" + meal.getKey(),
            accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the meal was eaten
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteByDate(String accessToken, String owner, String petName, DateTime date) throws ExecutionException,
        InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName + "/" + date, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes all the meals of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllMeals(String accessToken, String owner, String petName) throws ExecutionException,
        InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a meal identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the meal was eaten
     * @return The MealData identified by the data
     */
    public MealData getMealData(String accessToken, String owner, String petName, DateTime date) throws
        ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + "/" + petName + "/" + date, accessToken).get();
        return GSON.fromJson(json.toString(), MealData.class);
    }

    /**
     * Gets the data from all the specified meals from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the meals from the pet
     */
    public List<Meal> getAllMealData(String accessToken, String owner, String petName) throws ExecutionException,
        InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        List<Meal> mealList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] meals = jsonArray.split(",\\{");
            mealList.add(GSON.fromJson(meals[0], Meal.class));
            for (int i = 1; i < meals.length; i++) {
                meals[i] = "{" + meals[i];
                mealList.add(GSON.fromJson(meals[i], Meal.class));
            }
        }
        return mealList;
    }

    /**
     * Gets the data from all the meals eaten by the pet between the initial and final date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the meals eaten by the pet in the specified time
     */
    public List<Meal> getAllMealsBetween(String accessToken, String owner, String petName, DateTime initialDate,
                                                 DateTime finalDate) throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response =
            taskManager.execute(BASE_URL + owner + "/" + petName + "/between/" + initialDate + "/" + finalDate,
                accessToken).get();
        List<Meal> mealList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] meals = jsonArray.split(",\\{");
            mealList.add(GSON.fromJson(meals[0], Meal.class));
            for (int i = 1; i < meals.length; i++) {
                meals[i] = "{" + meals[i];
                mealList.add(GSON.fromJson(meals[i], Meal.class));
            }
        }
        return mealList;
    }

    /**
     * Updates the meal's field.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the meal was eaten
     * @param field Name of the field to update
     * @param value Value the field will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateMealField(String accessToken, String owner, String petName, DateTime date, String field,
                               Object value) throws ExecutionException, InterruptedException {
        checkCorrectType(field, value);
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("value", value);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName + "/" + date + "/" + field,
            accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Checks that the new value is of the correct type.
     * @param field The field to update
     * @param value The new value
     * @throws IllegalArgumentException When an invalid field value is passed
     */
    private void checkCorrectType(String field, Object value) {
        if (field.equals(MEALNAME) && !(value instanceof String)) {
            throw new IllegalArgumentException("New value must be a String");
        }
        if (field.equals(KCAL) && !(value instanceof Double)) {
            throw new IllegalArgumentException("New value must be a Double");
        }

    }


}
