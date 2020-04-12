package org.pesmypetcare.usermanagerlib.clients;


import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Medication;
import org.pesmypetcare.usermanagerlib.datacontainers.MedicationData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MedicationManagerClient {
    public static final String MedicationNAME = "MedicationName";
    public static final String KCAL = "kcal";
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/Medication/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static Gson GSON = new Gson();
    private TaskManager taskManager;

    public MedicationManagerClient() {
        taskManager = new TaskManager();
    }


    /**
     * Creates a Medication eaten by a pet on the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param Medication The Medication entity that contains the attributes of the Medication eaten by the pet
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createMedication(String accessToken, String owner, String petName, Medication Medication) throws ExecutionException,
            InterruptedException {
        JSONObject reqJson = Medication.getBody().buildMedicationJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName + "/" + Medication.getDate(),
                accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes the pet with the specified owner and name from the database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the Medication was eaten
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
     * Deletes all the Medications of the specified pet from database.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @return The response code
     * @throws ExecutionException When the deletion fails
     * @throws InterruptedException When the deletion is interrupted
     */
    public int deleteAllMedications(String accessToken, String owner, String petName) throws ExecutionException,
            InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets a Medication identified by its pet and date.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the Medication was eaten
     * @return The MedicationData identified by the data
     */
    public MedicationData getMedicationData(String accessToken, String owner, String petName, DateTime date) throws
            ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + owner + "/" + petName + "/" + date, accessToken).get();
        return GSON.fromJson(json.toString(), MedicationData.class);
    }

    /**
     * Gets the data from all the specified Medications from the database identified by its pet.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @return The List containing all the Medications from the pet
     */
    public List<Medication> getAllMedicationData(String accessToken, String owner, String petName) throws ExecutionException,
            InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        List<Medication> MedicationList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] Medications = jsonArray.split(",\\{");
            MedicationList.add(GSON.fromJson(Medications[0], Medication.class));
            for (int i = 1; i < Medications.length; i++) {
                Medications[i] = "{" + Medications[i];
                MedicationList.add(GSON.fromJson(Medications[i], Medication.class));
            }
        }
        return MedicationList;
    }

    /**
     * Gets the data from all the Medications eaten by the pet between the initial and final date not including them.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pets
     * @param petName Name of the pet
     * @param initialDate Initial Date
     * @param finalDate Final Date
     * @return The List containing all the Medications eaten by the pet in the specified time
     */
    public List<Medication> getAllMedicationsBetween(String accessToken, String owner, String petName, DateTime initialDate,
                                         DateTime finalDate) throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response =
                taskManager.execute(BASE_URL + owner + "/" + petName + "/between/" + initialDate + "/" + finalDate,
                        accessToken).get();
        List<Medication> MedicationList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] Medications = jsonArray.split(",\\{");
            MedicationList.add(GSON.fromJson(Medications[0], Medication.class));
            for (int i = 1; i < Medications.length; i++) {
                Medications[i] = "{" + Medications[i];
                MedicationList.add(GSON.fromJson(Medications[i], Medication.class));
            }
        }
        return MedicationList;
    }

    /**
     * Updates the Medication's field.
     * @param accessToken The personal access token for the account
     * @param owner Username of the owner of the pet
     * @param petName Name of the pet
     * @param date Date the Medication was eaten
     * @param field Name of the field to update
     * @param value Value the field will have
     * @return The response code
     * @throws ExecutionException When the update fails
     * @throws InterruptedException When the update is interrupted
     */
    public int updateMedicationField(String accessToken, String owner, String petName, DateTime date, String field,
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
        if (field.equals(MedicationNAME) && !(value instanceof String)) {
            throw new IllegalArgumentException("New value must be a String");
        }
        if (field.equals(KCAL) && !(value instanceof Double)) {
            throw new IllegalArgumentException("New value must be a Double");
        }

    }


}
