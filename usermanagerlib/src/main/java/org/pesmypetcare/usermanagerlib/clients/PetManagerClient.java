package org.pesmypetcare.usermanagerlib.clients;


import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PetManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/pet/";

    private static String dash = "/";
    private static String usernameField = "username";
    private static String nameField = "name";
    private static String genderField = "gender";
    private static String birthdayField = "birth";
    private static String breedField = "breed";
    private static String weightField = "weight";
    private static String pathologiesField = "pathologies";
    private static String recommendedKcalField = "recommendedKcal";
    private static String washFreqField = "washFreq";
    private final String VALUE_KEY = "value";
    private final String PUT = "PUT";
    private TaskManager taskManager;

    /**
     * Creates a pet entry in the data base for the user specified.
     * @param username The user's username
     * @param petName The pet's name
     * @param gender The pet's gender
     * @param breed The pet's breed
     * @param birthday The pet's birthday
     * @param weight The pet's weight
     * @param pathologies The pet's pathologies
     * @param recKcal The pet's recommended Kcal
     * @param washFreq The pet's washing frequency
     */
    public void signUpPet(String username, String petName, String gender, String
            breed, String birthday, double weight, String
            pathologies, double recKcal, int washFreq) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(usernameField, username);
        reqData.put(nameField, petName);
        reqData.put(genderField, gender);
        reqData.put(breedField, breed);
        reqData.put(birthdayField, birthday);
        reqData.put(weightField, Double.toString(weight));
        reqData.put(pathologiesField, pathologies);
        reqData.put(recommendedKcalField, Double.toString(recKcal));
        reqData.put(washFreqField, Integer.toString(washFreq));
        taskManager = new TaskManager();
        taskManager.setTaskId("POST");
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName);
    }

    /**
     * Returns the data of a pet.
     * @param username The pet's owner
     * @param petName The pet's name
     * @return The pet data
     * @throws ExecutionException When the retrieval of the pet fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public PetData getPet(String username, String petName) throws ExecutionException, InterruptedException {
        taskManager = new TaskManager();
        taskManager.setTaskId("GET");
        StringBuilder json = taskManager.execute(BASE_URL + username + dash + petName).get();
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), PetData.class);
    }

    /**
     * Returns all the pets of the user.
     * @param username The user's username
     * @return All the pets of the user
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Pet> getAllPets(String username) throws ExecutionException, InterruptedException {
        taskManager = new TaskManager();
        taskManager.setTaskId("GET");
        StringBuilder response = taskManager.execute(BASE_URL + username).get();
        String jsonArray = response.substring(1, response.length() - 1);
        String[] pets = jsonArray.split(",\\{");
        List<Pet> petsList = new ArrayList<>();
        Gson gson = new Gson();
        petsList.add(gson.fromJson(pets[0], Pet.class));
        for (int i = 1; i < pets.length; i++) {
            pets[i] = "{" + pets[i];
            petsList.add(gson.fromJson(pets[i], Pet.class));
        }
        return petsList;
    }

    /**
     * Deletes a pet of the specified user.
     * @param username The user's username
     * @param petName The pet's name
     */
    public void deletePet(String username, String petName) {
        taskManager = new TaskManager();
        taskManager.setTaskId("DELETE");
        taskManager.execute(BASE_URL + username + dash + petName);
    }

    /**
     * Updates de gender of a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newGender The new gender for the pet
     */
    public void updateGender(String username, String petName, String newGender) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newGender);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + genderField);
    }

    /**
     * Updates the birthday of a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newBirthday The new birthday for the pet
     */
    public void updateBirthday(String username, String petName, String newBirthday) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newBirthday);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + birthdayField);
    }

    /**
     * Updates the breed of a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newBreed The new breed for the pet
     */
    public void updateBreed(String username, String petName, String newBreed) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newBreed);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + breedField);
    }

    /**
     * Updates the breed of a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newWeight The new weight for the pet
     */
    public void updateWeight(String username, String petName, double newWeight) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, String.valueOf(newWeight));
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + weightField);
    }

    /**
     * Updates the pathologies of a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newPathologies The new pathologies for the pet
     */
    public void updatePathologies(String username, String petName, String newPathologies) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newPathologies);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + pathologiesField);
    }

    /**
     * Updates the recommended Kcal for a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newKcal The new recommended Kcal for the pet
     */
    public void updateRecKcal(String username, String petName, double newKcal) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, Double.toString(newKcal));
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + recommendedKcalField);
    }

    /**
     * Updates the washing frequency of a pet.
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newWashFreq The new washing frequency for the pet
     */
    public void updateWashFreq(String username, String petName, int newWashFreq) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, String.valueOf(newWashFreq));
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + washFreqField);
    }
}
