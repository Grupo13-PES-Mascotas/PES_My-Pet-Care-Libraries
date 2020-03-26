package org.pesmypetcare.usermanagerlib.clients;


import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private final String USERS_PATH = "users/";
    private final String EMAIL_KEY = "email";

    /*
     * Method called by the client to sign up a new user.
     * Password must contain numbers and letters
     * @return void.
     * */
    public void signUp(String username, String password, String email) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("username", username);
        reqData.put(EMAIL_KEY, email);
        taskManager.setTaskId(0);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + "signup?password=" + password);
    }

    /*
     * Method called by the client to get a user username.
     * @return Json containint all the info of the user username.
     * */
    public UserData getUser(String username) throws ExecutionException, InterruptedException {
        TaskManager taskManager = new TaskManager();
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(USERS_PATH).append(username);
        taskManager.setTaskId(1);
        StringBuilder json = taskManager.execute(url.toString()).get();
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), UserData.class);
    }

    /*
    *Method called by the client to delete user username
    * @return void
     */
    public void deleteUser(String username) {
        TaskManager taskManager = new TaskManager();
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append(USERS_PATH).append(username).append("/delete");
        taskManager.setTaskId(2);
        taskManager.execute(url.toString());
    }

    /*
     *Method called by the client to update the password of the user username
     * Password must contain numbers and letters.
     * @return void
     */
    public void updatePassword(String username, String newPassword) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put("password", newPassword);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/password");
    }

    /*
     *Method called by the client to update the Email of the user username
     * @return void
     */
    public void updateEmail(String username, String newEmail) {
        TaskManager taskManager = new TaskManager();
        Map<String, String> reqData = new HashMap<>();
        reqData.put(EMAIL_KEY, newEmail);
        taskManager.setTaskId(3);
        taskManager.setReqBody(new JSONObject(reqData));
        taskManager.execute(BASE_URL + USERS_PATH + username + "/update/email");
    }
}
