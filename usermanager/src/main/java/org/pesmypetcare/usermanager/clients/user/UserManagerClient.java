package org.pesmypetcare.usermanager.clients.user;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.user.UserData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Oriol Catalán
 */
public class UserManagerClient {
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private static final String BASE_URL = BuildConfig.URL;
    private static final String USERS_PATH = "users/";
    private static final String IMAGES_PATH = "storage/image/";
    private static final String UID_FIELD = "uid";
    private static final String TOKEN_HEADER = "token";
    private static final String USER_PROFILE_IMAGE_NAME = "profile-image";
    private HttpClient httpClient;
    private Map<String, String> httpHeaders;
    private Gson gson;

    /**
     * Default constructor.
     */
    public UserManagerClient() {
        httpClient = new HttpClient();
        httpHeaders = new HashMap<>();
        gson = new Gson();
    }

    /**
     * Method called by the client to sign up a new user.
     *
     * @param uid The user's unique identifier
     * @param data The user data object that contains the user's username, email and password
     * @throws MyPetCareException When the request fails
     */
    public void createUser(String uid, UserData data) throws MyPetCareException {
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(UID_FIELD, uid);
        reqBody.put("user", gson.toJson(data));
        httpClient.post(BASE_URL + "signup", null, null, gson.toJson(reqBody));
    }

    /**
     * Checks if a username is already in use.
     *
     * @param username The username to check
     * @return True if the username is already in use
     * @throws MyPetCareException When the request fails
     */
    public boolean usernameAlreadyExists(String username) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(USERNAME, username);
        HttpResponse response = httpClient.get(BASE_URL + "usernames", params, null, null);
        Type mapType = new TypeToken<HashMap<String, Boolean>>() {
        }.getType();
        Map<String, Boolean> map = gson.fromJson(response.asString(), mapType);
        return Objects.requireNonNull(map.get("exists"));
    }

    /**
     * Method called by the client to get a user.
     *
     * @param accessToken The personal access token for the account
     * @param uid The user uid of which we want the information
     * @return Json that contains all the info of the user
     * @throws MyPetCareException When the request fails
     */
    public UserData getUser(String accessToken, String uid) throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + USERS_PATH + HttpParameter.encode(uid), null, httpHeaders, null);
        return gson.fromJson(response.asString(), UserData.class);
    }

    /**
     * Method called by the client to delete user completely.
     *
     * @param accessToken The personal access token for the account
     * @param uid The user uid of which we want to delete
     * @throws MyPetCareException When the request fails
     */
    public void deleteUser(String accessToken, String uid) throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        httpClient.delete(BASE_URL + USERS_PATH + HttpParameter.encode(uid), null, httpHeaders, null);
    }

    /**
     * Method called by the client to delete user from database.
     *
     * @param accessToken The personal access token for the account
     * @param uid The user uid of which we want to delete
     * @throws MyPetCareException When the request fails
     */
    public void deleteUserFromDatabase(String accessToken, String uid) throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("db", true);
        httpClient.delete(BASE_URL + USERS_PATH + HttpParameter.encode(uid), params, httpHeaders, null);
    }

    /**
     * Method called by the client to update the user's password.
     *
     * @param accessToken The personal access token for the account
     * @param username The username of which we want to update
     * @param field The field to update
     * @param newValue The new field value
     * @return The response code
     * @throws MyPetCareException When the request fails
     */
    public void updateField(String accessToken, String username, String field, String newValue)
            throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(field, newValue);
        httpClient.put(BASE_URL + USERS_PATH + HttpParameter.encode(username), params, httpHeaders, null);
    }

    /**
     * Saves the image given as the profile image.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @param image The image to save
     * @throws MyPetCareException When the request fails
     */
    public void saveProfileImage(String accessToken, String username, byte[] image) throws MyPetCareException {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put(UID_FIELD, username);
        reqData.put("imgName", USER_PROFILE_IMAGE_NAME);
        reqData.put("img", image);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, accessToken);
        httpClient.put(BASE_URL + IMAGES_PATH, null, headers, gson.toJson(reqData));
    }

    /**
     * Downloads the profile image of the specified user.
     *
     * @param accessToken The personal access token for the account
     * @param username The user's username
     * @return The profile image as a byte array
     * @throws MyPetCareException When the request fails
     */
    public byte[] downloadProfileImage(String accessToken, String username) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("name", USER_PROFILE_IMAGE_NAME);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, accessToken);
        HttpResponse resp = httpClient
                .get(BASE_URL + IMAGES_PATH + HttpParameter.encode(username), params, headers, null);
        String encodedImage = resp.asString();
        return Base64.decode(encodedImage, Base64.DEFAULT);
    }

    /**
     * Gets the user group subscriptions.
     *
     * @param token The user's personal access token
     * @param username The user's username
     * @return A list with groups which the user is subscribed to
     * @throws MyPetCareException When the request fails
     */
    public List<String> getUserSubscriptions(String token, String username) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(USERNAME, username);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpResponse res = httpClient.get(BASE_URL + USERS_PATH + "subscriptions", params, headers, null);
        Type listType = TypeToken.getParameterized(List.class, String.class).getType();
        return gson.fromJson(res.asString(), listType);
    }

    /**
     * Sends a token to the server.
     *
     * @param authToken The user's authentication token
     * @param messagingToken The user's messaging token
     * @throws MyPetCareException When the request fails
     */
    public void sendTokenToServer(String authToken, String messagingToken) throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, authToken);
        headers.put("fcmToken", messagingToken);
        httpClient.put(BASE_URL + "users", null, headers, null);
    }
}
