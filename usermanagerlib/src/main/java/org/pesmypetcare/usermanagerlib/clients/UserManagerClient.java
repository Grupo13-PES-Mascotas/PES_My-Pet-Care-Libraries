package org.pesmypetcare.usermanagerlib.clients;


import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserManagerClient {
    //private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private static final String BASE_URL = "http://10.4.41.170:8081/";
    private final String USERS_PATH = "users/";
    private final String STORAGE_PATH = "storage/";
    private final String EMAIL_KEY = "email";
    private final String PUT = "PUT";
    private final String GET = "GET";
    private TaskManager taskManager;

    /**
     * Method called by the client to sign up a new user.
     * @param username The username of the new user
     * @param password The password of the new user
     * @param email The email of the new user
     */
    public void signUp(String username, String password, String email) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("username", username);
        reqData.put(EMAIL_KEY, email);
        taskManager.setTaskId("POST");
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "signup?password=" + password);
    }

    /**
     * Method called by the client to get a user.
     * @param username The username of which we want the information
     * @return Json that contains all the info of the user
     * @throws ExecutionException When the retrieval of the user fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public UserData getUser(String username) throws ExecutionException, InterruptedException {
        taskManager = new TaskManager();
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(USERS_PATH).append(username);
        taskManager.setTaskId(GET);
        StringBuilder json = taskManager.execute(url.toString()).get();
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), UserData.class);
    }

    /**
     * Method called by the client to delete user.
     * @param username The username of which we want to delete
     */
    public void deleteUser(String username) {
        taskManager = new TaskManager();
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(USERS_PATH).append(username).append("/delete");
        taskManager.setTaskId("DELETE");
        taskManager.execute(url.toString());
    }

    /**
     * Method called by the client to update the user's password.
     * @param username The username of which we want to update
     * @param newPassword The new value of password
     */
    public void updatePassword(String username, String newPassword) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("password", newPassword);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/password");
    }

    /**
     *  Method called by the client to update the user's email.
     * @param username The username of which we want to update
     * @param newEmail The new value of email
     */
    public void updateEmail(String username, String newEmail) {
        taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(EMAIL_KEY, newEmail);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/email");
    }

    public void saveProfileImage(String userId, byte[] image) {
        taskManager = new TaskManager();
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("uid", userId);
        reqData.put("imgName", "profile-image");
        reqData.put("img", image);
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + STORAGE_PATH + "/image");
    }

    public byte[] downloadProfileImage(String userId) throws ExecutionException, InterruptedException {
        taskManager = new TaskManager();
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("path", userId);
        reqData.put("imageName", "profile-image");
        taskManager.setTaskId(GET);
        taskManager.setReqBody(new JSONObject(reqData));
        StringBuilder json = taskManager.execute(BASE_URL + STORAGE_PATH + "/image").get();
        return json.toString().getBytes();
    }
}
