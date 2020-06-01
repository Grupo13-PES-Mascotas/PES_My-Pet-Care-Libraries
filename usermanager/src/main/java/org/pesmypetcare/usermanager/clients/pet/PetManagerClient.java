package org.pesmypetcare.usermanager.clients.pet;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.pet.Pet;
import org.pesmypetcare.usermanager.datacontainers.pet.PetCollectionField;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marc Simó & Oriol Catalán
 */

public class PetManagerClient {
    public static final String RECOMMENDED_KCAL = "recommendedKcal";
    private static final String BASE_URL = BuildConfig.URL;
    private static final String PETS_PATH = "pet/";
    private static final String IMAGES_PATH = "storage/image/";
    private static final String PETS_PICTURES_PATH = "/pets/";
    private static final String PROFILE_IMAGE_NAME = "-profile-image.png";
    private static final String TOKEN_HEADER = "token";
    private static final String VALUE_KEY = "value";
    private static final String SLASH = "/";
    private static final Gson GSON = new Gson();
    private HttpClient httpClient;
    private Map<String, String> httpHeaders;

    public PetManagerClient() {
        httpClient = new HttpClient();
        httpHeaders = new HashMap<>();
    }

    /**
     * Creates a pet entry in the data base for the user specified.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param pet The pet's name
     * @throws MyPetCareException When the request fails
     */
    public void createPet(String accessToken, String username, Pet pet) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.post(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter
                .encode(pet.getName()), null, httpHeaders, GSON.toJson(pet.getBody()));
    }

    /**
     * Returns the data of a pet.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner
     * @param petName The pet's name
     * @return The pet data
     * @throws MyPetCareException When the request fails
     */
    public PetData getPet(String accessToken, String username, String petName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName),
                        null, httpHeaders, null);
        return GSON.fromJson(response.asString(), PetData.class);
    }

    /**
     * Returns all the pets of the user.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @return All the pets of the user
     * @throws MyPetCareException When the request fails
     */
    public List<Pet> getAllPets(String accessToken, String username) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse res = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username), null, httpHeaders, null);
        Type listType = TypeToken.getParameterized(List.class, Pet.class).getType();
        return GSON.fromJson(res.asString(), listType);
    }

    /**
     * Deletes a pet of the specified user.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param petName The pet's name
     * @throws MyPetCareException When the request fails
     */
    public void deletePet(String accessToken, String username, String petName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.delete(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName),
                null, httpHeaders, null);
    }

    /**
     * Deletes all pets of the specified user.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @throws MyPetCareException When the request fails
     */
    public void deleteAllPets(String accessToken, String username) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.delete(BASE_URL + PETS_PATH + HttpParameter.encode(username), null, httpHeaders, null);
    }

    /**
     * Gets the value for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field to retrieve the value from
     * @return The value from the field on the database
     * @throws MyPetCareException When the request fails
     */
    public Object getSimpleField(String accessToken, String username, String petName, String field)
            throws MyPetCareException {
        PetData.checkSimpleField(field);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                        + "/simple/" + HttpParameter.encode(field), null, httpHeaders, null);
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
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field The field to update
     * @param newValue The new field value
     * @throws MyPetCareException When the request fails
     */
    public void updateSimpleField(String accessToken, String username, String petName, String field, Object newValue)
            throws MyPetCareException {
        PetData.checkSimpleFieldAndValues(field, newValue);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        Map<String, Object> reqData = new HashMap<>();
        reqData.put(VALUE_KEY, newValue);
        httpClient.put(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                + "/simple/" + HttpParameter.encode(field), null, httpHeaders, GSON.toJson(reqData));
    }


    /**
     * Deletes the map for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field to delete
     * @throws MyPetCareException When the request fails
     */
    public void deleteFieldCollection(String accessToken, String username, String petName, String field)
            throws MyPetCareException {
        PetData.checkCollectionField(field);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.delete(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                + "/collection/" + HttpParameter.encode(field), null, httpHeaders, null);
    }

    /**
     * Gets the elements for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field to retrieve
     * @return The elements from the field on the database
     * @throws MyPetCareException When the request fails
     */
    public List<PetCollectionField> getFieldCollection(String accessToken, String username, String petName,
            String field) throws MyPetCareException {
        PetData.checkCollectionField(field);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                        + "/collection/" + HttpParameter.encode(field), null, httpHeaders, null);
        Type listType = TypeToken.getParameterized(List.class, PetCollectionField.class).getType();
        return GSON.fromJson(response.asString(), listType);
    }

    /**
     * Gets all the elements between the keys from the database for the specified field.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key1 Start key (This one included)
     * @param key2 End Key (This one included)
     * @return The elements between the keys
     * @throws MyPetCareException When the request fails
     */
    public List<PetCollectionField> getFieldCollectionElementsBetweenKeys(String accessToken, String username,
            String petName, String field, String key1, String key2) throws MyPetCareException {
        PetData.checkCollectionField(field);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                        + "/collection/" + HttpParameter.encode(field) + SLASH + HttpParameter.encode(key1) + SLASH
                        + HttpParameter.encode(key2), null, httpHeaders, null);
        Type listType = TypeToken.getParameterized(List.class, PetCollectionField.class).getType();
        return GSON.fromJson(response.asString(), listType);
    }

    /**
     * Adds an element to the map for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the new element to be added
     * @param body Element to be added
     * @throws MyPetCareException When the request fails
     */
    public void addFieldCollectionElement(String accessToken, String username, String petName, String field, String key,
            Map<String, Object> body) throws MyPetCareException {
        PetData.checkCollectionKeyAndBody(field, key, body);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.post(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                        + "/collection/" + HttpParameter.encode(field) + SLASH + HttpParameter.encode(key), null,
                httpHeaders,
                GSON.toJson(body));
    }

    /**
     * Deletes an element from the map for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the element to delete
     * @throws MyPetCareException When the request fails
     */
    public void deleteFieldCollectionElement(String accessToken, String username, String petName, String field,
            String key) throws MyPetCareException {
        PetData.checkCollectionField(field);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.delete(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                        + "/collection/" + HttpParameter.encode(field) + SLASH + HttpParameter.encode(key), null,
                httpHeaders,
                null);
    }

    /**
     * Updates an element from the map for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the element to update
     * @param body Update of the element
     * @throws MyPetCareException When the request fails
     */
    public void updateFieldCollectionElement(String accessToken, String username, String petName, String field,
            String key, Map<String, Object> body) throws MyPetCareException {
        PetData.checkCollectionKeyAndBody(field, key, body);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.put(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                        + "/collection/" + HttpParameter.encode(field) + SLASH + HttpParameter.encode(key), null,
                httpHeaders,
                GSON.toJson(body));
    }

    /**
     * Gets an element from the map for the specified field of the pet on the database.
     *
     * @param accessToken The personal access token for the account
     * @param username The pet's owner username
     * @param petName The pet's name
     * @param field Name of the field
     * @param key Key of the element
     * @return Element assigned to the key
     */
    public Map<String, Object> getFieldCollectionElement(String accessToken, String username, String petName,
            String field, String key) throws MyPetCareException {
        PetData.checkCollectionField(field);
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + PETS_PATH + HttpParameter.encode(username) + SLASH + HttpParameter.encode(petName)
                                + "/collection/" + HttpParameter.encode(field) + SLASH + HttpParameter.encode(key),
                        null,
                        httpHeaders, null);
        Type mapType = TypeToken.getParameterized(Map.class, String.class, Object.class).getType();
        return GSON.fromJson(response.toString(), mapType);
    }

    /**
     * Saves the image given as the profile image.
     *
     * @param accessToken The personal access token for the account
     * @param userId The user unique identifier
     * @param petName The pet's name
     * @param image The image to save
     * @throws MyPetCareException When the request fails
     */
    public void saveProfileImage(String accessToken, String userId, String petName, byte[] image)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("uid", userId);
        reqData.put("imgName", petName + PROFILE_IMAGE_NAME);
        reqData.put("img", image);
        httpClient.put(BASE_URL + IMAGES_PATH + HttpParameter.encode(userId) + PETS_PICTURES_PATH, null, httpHeaders,
                GSON.toJson(reqData));
    }

    /**
     * Downloads profile image of the specified user.
     *
     * @param accessToken The personal access token for the account
     * @param userId The owner's unique identifier
     * @param petName The pet's name
     * @return The profile image as a byte array
     * @throws MyPetCareException When the request fails
     */
    public byte[] downloadProfileImage(String accessToken, String userId, String petName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + IMAGES_PATH + HttpParameter.encode(userId) + PETS_PICTURES_PATH + HttpParameter
                        .encode(petName), null, httpHeaders, null);
        String encodedImage = response.asString();
        return Base64.decode(encodedImage, Base64.DEFAULT);
    }

    /**
     * Downloads all profile images of the user's pets.
     *
     * @param accessToken The personal access token for the account
     * @param userId The owner's unique identifier
     * @return A map with all profile images of the user's pets
     * @throws MyPetCareException When the request fails
     */
    public Map<String, byte[]> downloadAllProfileImages(String accessToken, String userId) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + IMAGES_PATH + HttpParameter.encode(userId) + PETS_PICTURES_PATH, null, httpHeaders,
                        null);
        Type mapType = TypeToken.getParameterized(Map.class, String.class, String.class).getType();
        Map<String, String> encodedImages = GSON.fromJson(response.toString(), mapType);
        Map<String, byte[]> result = new HashMap<>();
        for (String key : encodedImages.keySet()) {
            byte[] img = Base64.decode(encodedImages.get(key), Base64.DEFAULT);
            result.put(key, img);
        }
        return result;
    }
}
