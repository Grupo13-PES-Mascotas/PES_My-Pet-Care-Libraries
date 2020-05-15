package org.pesmypetcare.usermanager.clients.pet;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.RequestMethod;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.pet.Pet;
import org.pesmypetcare.usermanager.datacontainers.pet.PetCollectionField;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Marc Simó & Oriol Catalán
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
    private static final String DELETE = "DELETE";
    private static final String SLASH = "/";
    private static final String NAME_FIELD = "name";
    private final Gson GSON = new Gson();
    private TaskManager taskManager;

    public PetManagerClient() {
        taskManager = new TaskManager();
    }

    /**
     * Creates a pet entry in the data base for the user specified.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param pet The pet's name
     */
    public void createPetSync(String accessToken, String username, Pet pet) throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put("token", accessToken);
        new HttpClient().request(RequestMethod.POST,
                BASE_URL + SLASH + PETS_PATH + SLASH + HttpParameter.encode(username) + SLASH + HttpParameter
                        .encode(pet.getName()), null, headers, GSON.toJson(pet.getBody()));
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
        JSONObject reqJson = Pet.buildPetJsonObject(pet.getBody());
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
                + pet.getName(), accessToken).get();
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
        reqData.put(NAME_FIELD, petName);
        reqData.put(GENDER, gender);
        reqData.put(BREED, breed);
        reqData.put(BIRTH, birthday);
        reqData.put(PATHOLOGIES, pathologies);
        reqData.put(NEEDS, needs);
        reqData.put(RECOMMENDED_KCAL, Double.toString(recKcal));
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + PETS_PATH + username + SLASH + petName, accessToken);
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
        StringBuilder json = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH + petName,
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
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
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
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
                + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes all pets of the specified user.
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @return The response code
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int deleteAllPets(String accessToken, String username) throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets the value for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field to retrieve the value from
     * @return The value from the field on the database
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public Object getSimpleField(String accessToken, String username, String petName, String field)
        throws ExecutionException, InterruptedException {
        PetData.checkSimpleField(field);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
                + petName + "/simple/" + field, accessToken).get();
        if (response == null) {
            return null;
        }
        String[] split = response.toString().split("\"");
        if (split.length == 3) {
            return Double.parseDouble(split[2].split(":")[1].split("}")[0]);
        } else if (split.length == 1) {
            return split[0];
        } else if (split.length == 5) {
            return split[3];
        }
        return null;
    }

    /**
     * Updates de gender of a pet.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field The field to update
     * @param newValue The new field value
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int updateSimpleField(String accessToken, String username, String petName, String field,
                                 Object newValue)
        throws ExecutionException, InterruptedException {
        PetData.checkSimpleFieldAndValues(field, newValue);
        taskManager = taskManager.resetTaskManager();
        Map<String, Object> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newValue);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
                        + petName + "/simple/" + field, accessToken).get();
        return Integer.parseInt(response.toString());
    }


    /**
     * Deletes the map for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field to delete
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int deleteFieldCollection(String accessToken, String username, String petName, String field)
        throws ExecutionException, InterruptedException {
        PetData.checkCollectionField(field);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets the elements for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field to retrieve
     * @return The elements from the field on the database
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<PetCollectionField> getFieldCollection(String accessToken, String username, String petName,
                                                       String field) throws ExecutionException, InterruptedException {
        PetData.checkCollectionField(field);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field, accessToken).get();
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");

        List<PetCollectionField> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], PetCollectionField.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], PetCollectionField.class));
        }
        return result;
    }

    /**
     * Gets all the elements between the keys from the database for the specified field.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key1 Start key (This one included)
     * @param key2 End Key (This one included)
     * @return The elements between the keys
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<PetCollectionField> getFieldCollectionElementsBetweenKeys(String accessToken, String username,
                                                                           String petName, String field,
                                                                           String key1, String key2)
        throws ExecutionException, InterruptedException {
        PetData.checkCollectionField(field);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field + SLASH + key1 + SLASH + key2, accessToken).get();
        if (response == null) {
            return new ArrayList<>();
        } else if (response.length() <= 2) {
            return new ArrayList<>();
        }
        String jsonArray = response.substring(1, response.length() - 1);
        String[] objectArray = jsonArray.split(",\\{");
        List<PetCollectionField> result = new ArrayList<>();
        result.add(GSON.fromJson(objectArray[0], PetCollectionField.class));
        for (int i = 1; i < objectArray.length; i++) {
            objectArray[i] = "{" + objectArray[i];
            result.add(GSON.fromJson(objectArray[i], PetCollectionField.class));
        }
        return result;
    }

    /**
     * Adds an element to the map for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the new element to be added
     * @param body Element to be added
     * @throws ExecutionException When the retrieval fails
     * @throws ExecutionException When the retrieval of the pets fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int addFieldCollectionElement(String accessToken, String username, String petName, String field,
                                          String key, Map<String, Object> body)
        throws ExecutionException, InterruptedException {
        PetData.checkCollectionKeyAndBody(field, key, body);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(new JSONObject(body));
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field + SLASH + key, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes an element from the map for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the element to delete
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int deleteFieldCollectionElement(String accessToken, String username, String petName,
                                             String field, String key) throws ExecutionException, InterruptedException {
        PetData.checkCollectionField(field);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field + SLASH + key, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Updates an element from the map for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the element to update
     * @param body Update of the element
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int updateFieldCollectionElement(String accessToken, String username, String petName, String field,
                                             String key, Map<String, Object> body)
        throws ExecutionException, InterruptedException {
        PetData.checkCollectionKeyAndBody(field, key, body);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(body));
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field + SLASH + key, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Gets an element from the map for the specified field of the pet on the database.
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the element
     * @return Element assigned to the key
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public Map<String, Object> getFieldCollectionElement(String accessToken, String username, String petName,
                                                         String field, String key)
        throws ExecutionException, InterruptedException {
        PetData.checkCollectionField(field);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + PETS_PATH + username + SLASH
            + petName + "/collection/" + field + SLASH + key, accessToken).get();
        if (response != null) {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            return GSON.fromJson(response.toString(), type);
        }
        return null;
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
        if (json == null) return new HashMap<>();
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
}
