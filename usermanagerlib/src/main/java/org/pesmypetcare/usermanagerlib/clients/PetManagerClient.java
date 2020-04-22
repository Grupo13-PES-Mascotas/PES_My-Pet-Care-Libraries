package org.pesmypetcare.usermanagerlib.clients;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;
import org.pesmypetcare.usermanagerlib.datacontainers.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Oriol Catalán
 */
public class PetManagerClient {
    public static final String GENDER = "gender";
    public static final String BIRTH = "birth";
    public static final String BREED = "breed";
    public static final String PATHOLOGIES = "pathologies";
    public static final String RECOMMENDED_KCAL = "recommendedKcal";
    public static final String NEEDS = "needs";
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private static final String PETS_PATH = "pet/";
    private static final String IMAGES_PATH = "storage/image/";
    private static final String PETS_PICTURES_PATH = "/pets/";
    private static final String PROFILE_IMAGE_NAME = "-profile-image.png";
    private static final String VALUE_KEY = "value";
    private static final String PUT = "PUT";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static String usernameField = "username";
    private static String nameField = "name";
    private final Gson GSON = new Gson();
    private TaskManager taskManager;

    public PetManagerClient() {
        taskManager = new TaskManager();
    }

    /**
     * Creates a pet entry in the data base for the user specified.
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param pet The pet's name
     * @return The response code
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createPet(String accessToken, String username, Pet pet)
        throws ExecutionException, InterruptedException {
        JSONObject reqJson = buildPetJson(pet.getBody());
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + "/" +
                        pet.getName(), accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Creates a pet entry in the data base for the user specified.
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param petName The pet's name
     * @param gender The pet's gender
     * @param breed The pet's breed
     * @param birthday The pet's birthday
     * @param needs The pet's needs
     * @param pathologies The pet's pathologies
     * @param recKcal The pet's recommended Kcal
     */
    @Deprecated
    public void createPet(String accessToken, String username, String petName, String gender, String
            breed, String birthday, String needs, String pathologies, double recKcal) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(usernameField, username);
        reqData.put(nameField, petName);
        reqData.put(GENDER, gender);
        reqData.put(BREED, breed);
        reqData.put(BIRTH, birthday);
        reqData.put(PATHOLOGIES, pathologies);
        reqData.put(NEEDS, needs);
        reqData.put(RECOMMENDED_KCAL, Double.toString(recKcal));
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName, accessToken);
    }

    /**
     * Returns the data of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner
     * @param petName The pet's name
     * @return The pet data
     * @throws ExecutionException When the retrieval of the pet fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public PetData getPet(String accessToken, String username,
                          String petName) throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName,
                accessToken).get();
        if (json != null) {
            return GSON.fromJson(json.toString(), PetData.class);
        }
        return null;
    }

    /**
     * Returns all the pets of the user.
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @return All the pets of the user
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<Pet> getAllPets(String accessToken, String username) throws ExecutionException,
            InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username,
                accessToken).get();
        if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] pets = jsonArray.split(",\\{");
        List<Pet> petsList = new ArrayList<>();
        petsList.add(GSON.fromJson(pets[0], Pet.class));
        for (int i = 1; i < pets.length; i++) {
            pets[i] = "{" + pets[i];
            petsList.add(GSON.fromJson(pets[i], Pet.class));
        }
        return petsList;
    }

    /**
     * Deletes a pet of the specified user.
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param petName The pet's name
     * @return The response code
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int deletePet(String accessToken, String username, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId("DELETE");
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + "/"
                + petName, accessToken)
            .get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Updates de gender of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field The field to update
     * @param newValue The new field value
     * @return The response code
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int updateField(String accessToken, String username, String petName, String field,
                           Object newValue)
        throws ExecutionException, InterruptedException {
        checkCorrectType(field, newValue);
        taskManager = taskManager.resetTaskManager();
        Map<String, Object> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newValue);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + "/"
                        + petName + "/" + field,
            accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Updates de gender of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newGender The new gender for the pet
     */
    @Deprecated
    public void updateGender(String accessToken, String username, String petName,
                             String newGender) {
        taskManager = taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newGender);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName + "/" + GENDER,
                accessToken);
    }

    /**
     * Updates the birthday of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newBirthday The new birthday for the pet
     */
    @Deprecated
    public void updateBirthday(String accessToken, String username, String petName,
                               String newBirthday) {
        taskManager = taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newBirthday);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName + "/" + BIRTH,
                accessToken);
    }

    /**
     * Updates the breed of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newBreed The new breed for the pet
     */
    @Deprecated
    public void updateBreed(String accessToken, String username, String petName, String newBreed) {
        taskManager = taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newBreed);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName + "/" + BREED,
                accessToken);
    }

    /**
     * Updates the breed of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newNeed The new need for the pet
     */
    @Deprecated
    public void updateNeeds(String accessToken, String username, String petName, String newNeed) {
        taskManager = taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, String.valueOf(newNeed));
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName + "/" + NEEDS,
                accessToken);
    }

    /**
     * Updates the pathologies of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newPathologies The new pathologies for the pet
     */
    @Deprecated
    public void updatePathologies(String accessToken, String username, String petName,
                                  String newPathologies) {
        taskManager = taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newPathologies);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName + "/" + PATHOLOGIES,
                accessToken);
    }

    /**
     * Updates the recommended Kcal for a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param newKcal The new recommended Kcal for the pet
     */
    @Deprecated
    public void updateRecKcal(String accessToken, String username, String petName, double newKcal) {
        taskManager = taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, Double.toString(newKcal));
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + "/" + petName + "/"
                + RECOMMENDED_KCAL, accessToken);
    }

    /**
     * Saves the image given as the profile image.
     * @param accessToken The personal access token for the account
     * @param userId The user unique identifier
     * @param petName The pet's name
     * @param image The image to save
     * @return The response code
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int saveProfileImage(String accessToken, String userId, String petName, byte[] image)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("uid", userId);
        reqData.put("imgName", petName + PROFILE_IMAGE_NAME);
        reqData.put("img", image);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + IMAGES_PATH + userId
                        + PETS_PICTURES_PATH, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Downloads profile image of the specified user.
     * @param accessToken The personal access token for the account
     * @param userId The owner's unique identifier
     * @param petName The pet's name
     * @return The profile image as a byte array
     * @throws ExecutionException When the retrieval of the user fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public byte[] downloadProfileImage(String accessToken,
                                       String userId, String petName)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager
            .execute(BASE_URL + IMAGES_PATH + userId + PETS_PICTURES_PATH + petName
                    + PROFILE_IMAGE_NAME, accessToken).get();
        return Base64.decode(json.toString(), Base64.DEFAULT);
    }

    /**
     * Downloads all profile images of the user's pets.
     * @param accessToken The personal access token for the account
     * @param userId The owner's unique identifier
     * @return A map with all profile images of the user's pets
     * @throws ExecutionException When the retrieval of the user fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public Map<String, byte[]> downloadAllProfileImages(String accessToken, String userId)
            throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager
            .execute(BASE_URL + IMAGES_PATH + userId + PETS_PICTURES_PATH, accessToken)
            .get();
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() { }.getType();
        Map<String, String> response = gson.fromJson(json.toString(), mapType);
        Map<String, byte[]> result = new HashMap<>();
        for (String key : response.keySet()) {
            byte[] img = Base64.decode(response.get(key), Base64.DEFAULT);
            result.put(key, img);
        }
        return result;
    }

    /**
     * Creates the pet's json object.
     * @param petData The pet data container
     * @return A JSONObject with te required data to make the request
     */
    private JSONObject buildPetJson(PetData petData) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(GENDER, petData.getGender().toString());
        reqData.put(BREED, petData.getBreed());
        reqData.put(BIRTH, petData.getBirth());
        reqData.put(NEEDS, petData.getNeeds());
        reqData.put(PATHOLOGIES, petData.getPathologies());
        reqData.put(RECOMMENDED_KCAL, Double.toString(petData.getRecommendedKcal()));
        return new JSONObject(reqData);
    }

    /**
     * Checks that the new value is of the correct type.
     * @param field The field to update
     * @param newValue The new value
     * @throws IllegalArgumentException When an invalid field value is passed
     */
    private void checkCorrectType(String field, Object newValue) {
        if ((field.equals(BIRTH) || field.equals(BREED) || field.equals(PATHOLOGIES)
                || field.equals(NEEDS)) && !(newValue instanceof String)) {
            throw new IllegalArgumentException("New value must be a String");
        } else if (field.equals(RECOMMENDED_KCAL) && !(newValue instanceof Double)) {
            throw new IllegalArgumentException("New value must be a Double");
        } else if (field.equals(GENDER) && !(newValue instanceof GenderType)) {
            throw new IllegalArgumentException("New value must be a GenderType");
        }
    }
}
