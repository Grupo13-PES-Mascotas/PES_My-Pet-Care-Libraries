package org.pesmypetcare.usermanagerlib;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserManagerClient {
    private static TaskManager taskManager;
    private static String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";

    public UserManagerClient() {
        taskManager = TaskManager.getInstance();
    }

    public void signUp(String username, String password, String email) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("username", username);
        reqData.put("email", email);
        taskManager.setTaskId(0);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "signup?password=" + password);
    }

    public String getUser(String username) throws ExecutionException, InterruptedException {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append("users/").append(username);
        taskManager.setTaskId(1);
        StringBuilder response = taskManager.execute(url.toString()).get();
        return response.toString();
    }

    public void deleteUser(String username) {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append("users/").append(username).append("/delete");
        System.out.println(url.toString());
        taskManager.setTaskId(2);
        taskManager.execute(url.toString());
    }

    public void updatePassword(String username, String newPassword) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("password", newPassword);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "users/" + username + "/update/password");
    }

    public void updateEmail(String username, String newEmail) {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("email", newEmail);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "users/" + username + "/update/email");
    }
}
