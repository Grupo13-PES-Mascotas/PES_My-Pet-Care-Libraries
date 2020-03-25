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
    private static String genderField = "gender";
    private static String birthdayField = "birth";
    private static String breedField = "breed";
    private static String weightField = "weight";
    private static String patologiesField = "patologies";
    private static String recommendedKcalField = "recommendedKcal";
    private static String washFreqField = "washFreq";

    public PetManagerLibrary() {
        taskManager = TaskManager.getInstance();
    }

    public void signUpPet(String username, String nameValuePost, String sexValuePost, String
            breedValuePost, String birthdayValuePost, double weightValuePost, String
            patologiesValuePost, double reckcalValuePost, int washFreqPost) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(usernameField, username);
        reqData.put(nameField, nameValuePost);
        reqData.put(genderField, sexValuePost);
        reqData.put(breedField, breedValuePost);
        reqData.put(birthdayField, birthdayValuePost);
        reqData.put(weightField, Double.toString(weightValuePost));
        reqData.put(patologiesField, patologiesValuePost);
        reqData.put(recommendedKcalField, Double.toString(reckcalValuePost));
        reqData.put(washFreqField, Integer.toString(washFreqPost));
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

    public String getAllPets(String username) throws ExecutionException, InterruptedException {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(username);
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

    public void updateGender(String username, String petName, String newGender) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newGender);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + genderField);
    }

    public void updateBirthday(String username, String petName, String newBirthday) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newBirthday);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + birthdayField);
    }

    public void updateBreed(String username, String petName, String newBreed) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newBreed);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + breedField);
    }

    public void updateWeight(String username, String petName, double newWeight) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", String.valueOf(newWeight));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + weightField);
    }

    public void updatePatologies(String username, String petName, String newPatologies) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newPatologies);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + patologiesField);
    }

    public void updateRecKcal(String username, String petName, double newKcal) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", Double.toString(newKcal));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + recommendedKcalField);
    }

    public void updateWashFreq(String username, String petName, int newWashFreq) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", String.valueOf(newWashFreq));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + washFreqField);
    }
}