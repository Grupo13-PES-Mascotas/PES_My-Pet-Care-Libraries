package org.pesmypetcare.usermanagerlib;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private final String USERS_PATH = "users/";
    private final String EMAIL_KEY = "email";
    private TaskManager taskManager;

    public UserManagerClient() {
        taskManager = TaskManager.getInstance();
    }

    public void signUp(String username, String password, String email) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("username", username);
        reqData.put(EMAIL_KEY, email);
        taskManager.setTaskId(0);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "signup?password=" + password);
    }

    public UserData getUser(String username) throws ExecutionException, InterruptedException {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(USERS_PATH).append(username);
        taskManager.setTaskId(1);
        StringBuilder json = taskManager.execute(url.toString()).get();
        Gson gson = new Gson();
        UserData user = gson.fromJson(json.toString(), UserData.class);
        return user;
    }

    public void deleteUser(String username) {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(USERS_PATH).append(username).append("/delete");
        System.out.println(url.toString());
        taskManager.setTaskId(2);
        taskManager.execute(url.toString());
    }

    public void updatePassword(String username, String newPassword) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("password", newPassword);
        taskManager.setTaskId(-1);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/password");
    }

    public void updateEmail(String username, String newEmail) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put(EMAIL_KEY, newEmail);
        taskManager.setTaskId(-1);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/email");
    }
}