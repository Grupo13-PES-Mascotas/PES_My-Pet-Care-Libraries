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
    private static String BASE_URL = "https://pes-my-pet-care.herokuapp.com/pet/";

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

    /*
    * Method called by the client to sign up a pet nameValuePost with the atributes ValuePost to a username.
    * Gender MUST be "Male", "Female" or "Other". Warning: case-sensitive.
    * Birthday is a string and it follows the format "YYYY-MM-DD"
    * @return void.
    * */
    public void signUpPet(String username, String nameValuePost, String sexValuePost, String
            breedValuePost, String birthdayValuePost, double weightValuePost, String
            pathologiesValuePost, double recKcalValuePost, int washFreqPost) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(usernameField, username);
        reqData.put(nameField, nameValuePost);
        reqData.put(genderField, sexValuePost);
        reqData.put(breedField, breedValuePost);
        reqData.put(birthdayField, birthdayValuePost);
        reqData.put(weightField, Double.toString(weightValuePost));
        reqData.put(pathologiesField, pathologiesValuePost);
        reqData.put(recommendedKcalField, Double.toString(recKcalValuePost));
        reqData.put(washFreqField, Integer.toString(washFreqPost));
        taskManager.setTaskId(0);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + nameValuePost);
    }

    /*
     * Method called by the client to get a pet with the name petName from the user username.
     * @return Json of the pet data.
     * */
    public PetData getPet(String username, String petName) throws ExecutionException, InterruptedException {
        TaskManager taskManager = new TaskManager();
        taskManager.setTaskId(1);
        StringBuilder json = taskManager.execute(BASE_URL + username + dash + petName).get();
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), PetData.class);
    }

    /*
    *Method called by the client to get all the pets of the user username.
    * @return List of gson's corresponding to the pets.
     */
    public List<Pet> getAllPets(String username) throws ExecutionException, InterruptedException {
        TaskManager taskManager = new TaskManager();
        taskManager.setTaskId(1);
        StringBuilder response = taskManager.execute(BASE_URL + username).get();
        System.out.println("Primer string: " + response.toString());
        String jsonArray = response.substring(1, response.length() - 1);
        String[] pets = jsonArray.split(",\\{");
        System.out.println("Segundo string: " + jsonArray);
        List<Pet> petsList = new ArrayList<>();
        Gson gson = new Gson();
        petsList.add(gson.fromJson(pets[0], Pet.class));
        for (int i = 1; i < pets.length; i++) {
            pets[i] = "{" + pets[i];
            System.out.println("Pet " + i + ": " + pets[i]);
            petsList.add(gson.fromJson(pets[i], Pet.class));
        }
        return petsList;
    }

    /*
     *Method called by the client to delete pet petName from the user username.
     * @return None.
     */
    public void deletePet(String username, String petName) {
        TaskManager taskManager = new TaskManager();
        taskManager.setTaskId(2);
        taskManager.execute(BASE_URL + username + dash + petName);
    }

    /*
     *Method called by the client to update the Gender of the pet PetName from the user username
     * Gender provided must be "Male", "Female" or "Other". Warning: case-sensitive.
     * @return None
     */
    public void updateGender(String username, String petName, String newGender) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newGender);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + genderField);
    }

    /*
     *Method called by the client to update the Birthday of the pet PetName from the user username.
     * Birthday is a string and it follows the format "YYYY-MM-DD"
     * @return None
     */
    public void updateBirthday(String username, String petName, String newBirthday) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newBirthday);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + birthdayField);
    }

    /*
     *Method called by the client to update the Breed of the pet PetName from the user username.
     * @return None
     */
    public void updateBreed(String username, String petName, String newBreed) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newBreed);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + breedField);
    }

    /*Method called by the client to update the Weight of the pet PetName from the user username.
     * @return None
     */
    public void updateWeight(String username, String petName, double newWeight) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", String.valueOf(newWeight));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + weightField);
    }

    /*Method called by the client to update the Pathologies of the pet PetName from the user username.
     * @return None
     */
    public void updatePathologies(String username, String petName, String newPatologies) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", newPatologies);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + pathologiesField);
    }

    /*Method called by the client to update the RecommendedKcal of the pet PetName from the user username.
     * @return None
     */
    public void updateRecKcal(String username, String petName, double newKcal) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", Double.toString(newKcal));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + recommendedKcalField);
    }

    /*Method called by the client to update the WashFrequency of the pet PetName from the user username.
     * @return None
     */
    public void updateWashFreq(String username, String petName, int newWashFreq) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("value", String.valueOf(newWashFreq));
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + username + dash + petName + dash + washFreqField);
    }
}
