package org.pesmypetcare.pes_my_pet_care_apis.usermanagerlibrary;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PetManagerLibrary {
    private static TaskManager taskManager;
    private static String BASE_URL = "https://pes-my-pet-care.herokuapp.com/pet/";

    private static String dash = "/";
    private static String usernameField = "username";
    private static String nameField = "name";
    private static String sexField = "gender";
    private static String birthdayField = "birth";
    private static String raceField = "breed";
    private static String weightField = "weight";

    public PetManagerLibrary() {
        taskManager = TaskManager.getInstance();
    }

    public void signUpPet(String username, String nameValuePost, String sexValuePost, String
            raceValuePost, String birthdayValuePost, double weightValuePost) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(usernameField, username);
        reqData.put(nameField, nameValuePost);
        reqData.put(sexField, sexValuePost);
        reqData.put(raceField, raceValuePost);
        reqData.put(birthdayField, birthdayValuePost);
        reqData.put(weightField, Double.toString(weightValuePost));
        taskManager.setTaskId(0);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + nameValuePost);
    }

    public String getPet(String username, String petName) throws ExecutionException, InterruptedException {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(username).append(dash).append(petName);
        taskManager.setTaskId(1);
        StringBuilder response = taskManager.execute(url.toString()).get();
        return response.toString();
    }

    public void deletePet(String username, String petName) {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(username).append(dash).append(petName);
        System.out.println(url.toString());
        taskManager.setTaskId(2);
        taskManager.execute(url.toString());
    }

    public void updateSex(String username, String petName, String newSex) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newSex);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + sexField);
    }

    public void updateBirthday(String username, String petName, String newBirthday) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newBirthday);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + birthdayField);
    }

    public void updateRace(String username, String petName, String newRace) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newRace);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + raceField);
    }

    public void updateWeight(String username, String petName, double newWeight) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", String.valueOf(newWeight));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + weightField);
    }
}