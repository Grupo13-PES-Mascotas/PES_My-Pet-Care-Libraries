package org.pesmypetcare.usermanager.clients.medal;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.user.UserData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author Oriol Catalán
 */
public class MedalManagerClient {
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private static final String BASE_URL = BuildConfig.URL;
    private static final String USERS_PATH = "users/";
    private static final String IMAGES_PATH = "storage/image/";
    private static final String PUT = "PUT";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String UID_FIELD = "uid";
    private static final String TOKEN_HEADER = "token";
    private static final String USER_PROFILE_IMAGE_NAME = "profile-image";
    private TaskManager taskManager;
    private HttpClient httpClient;
    private Gson gson;

    /**
     * Default constructor.
     */
    public MedalManagerClient() {
        taskManager = new TaskManager();
        httpClient = new HttpClient();
        gson = new Gson();
    }


}
