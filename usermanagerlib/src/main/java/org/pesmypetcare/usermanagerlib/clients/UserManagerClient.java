package org.pesmypetcare.usermanagerlib.clients;


import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserManagerClient {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private final String USERS_PATH = "users/";
    private final String IMAGES_PATH = "storage/image/";
    private final String PUT = "PUT";
    private final String GET = "GET";
    private TaskManager taskManager;

    public UserManagerClient() {
        taskManager = new TaskManager();
    }

    /**
     * Method called by the client to sign up a new user.
     * @param username The username of the new user
     * @param password The password of the new user
     * @param email The email of the new user
     */
    public void signUp(String username, String password, String email) throws ExecutionException, InterruptedException {
        taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("username", username);
        reqData.put(EMAIL, email);
        taskManager.setTaskId("POST");
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "signup?password=" + password, "").get();
    }

    /**
     * Method called by the client to get a user.
     * @param accessToken The personal access token for the account
     * @param username The username of which we want the information
     * @return Json that contains all the info of the user
     * @throws ExecutionException When the retrieval of the user fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public UserData getUser(String accessToken, String username) throws ExecutionException, InterruptedException {
        taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + USERS_PATH + username, accessToken).get();
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), UserData.class);
    }

    /**
     * Method called by the client to delete user.
     * @param accessToken The personal access token for the account
     * @param username The username of which we want to delete
     */
    public void deleteUser(String accessToken, String username) throws ExecutionException, InterruptedException {
        taskManager.resetTaskManager();
        taskManager.setTaskId("DELETE");
        taskManager.execute(BASE_URL + USERS_PATH + username + "/delete", accessToken).get();
    }

    /**
     * Method called by the client to update the user's password.
     * @param accessToken The personal access token for the account
     * @param username The username of which we want to update
     * @param field The field to update
     * @param newValue The new field value
     */
    public void updateField(String accessToken, String username, String field, String newValue)
        throws ExecutionException, InterruptedException {
        taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(field, newValue);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/" + field, accessToken).get();
    }

    /**
     * Method called by the client to update the user's password.
     * @param accessToken The personal access token for the account
     * @param username The username of which we want to update
     * @param newPassword The new value of password
     */
    @Deprecated
    public void updatePassword(String accessToken, String username, String newPassword) {
        taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(PASSWORD, newPassword);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/password", accessToken);
    }

    /**
     *  Method called by the client to update the user's email.
     * @param accessToken The personal access token for the account
     * @param username The username of which we want to update
     * @param newEmail The new value of email
     */
    @Deprecated
    public void updateEmail(String accessToken, String username, String newEmail) {
        taskManager.resetTaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(EMAIL, newEmail);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/email", accessToken);
    }

    /**
     * Saves the image given as the profile image.
     * @param accessToken The personal access token for the account
     * @param userId The user unique identifier
     * @param image The image to save
     */
    public void saveProfileImage(String accessToken, String userId, byte[] image)
        throws ExecutionException, InterruptedException {
        taskManager.resetTaskManager();
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("uid", userId);
        reqData.put("imgName", "profile-image.png");
        reqData.put("img", image);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + IMAGES_PATH, accessToken).get();
    }

    /**
     * Downloads the profile image of the specified user.
     * @param accessToken The personal access token for the account
     * @param userId The user unique identifier
     * @return The profile image as a byte array
     * @throws ExecutionException When the retrieval of the user fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public byte[] downloadProfileImage(String accessToken,
                                       String userId) throws ExecutionException, InterruptedException {
        taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(BASE_URL + IMAGES_PATH + userId + "?name=profile-image.png",
            accessToken).get();
        return Base64.decode(json.toString(), Base64.DEFAULT);
    }
}
