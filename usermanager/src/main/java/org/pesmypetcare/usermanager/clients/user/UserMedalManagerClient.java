package org.pesmypetcare.usermanager.clients.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oriol Catalán
 */
public class UserMedalManagerClient {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String USERMEDAL_PATH = BASE_URL + "usermedal/";
    private static final String TOKEN_HEADER = "token";
    private static final String USERMEDAL_PARAMETER = "usermedal";

    private HttpClient httpClient;
    private Gson gson;

    /**
     * Default constructor.
     */
    public UserMedalManagerClient() {
        httpClient = new HttpClient();
        gson = new Gson();
    }

    /**
     * Gets one medal from user.
     * @param token The user's personal access token
     * @param owner Name of the user's medal.
     * @param medalName Name of the medal.
     * @return The medal information
     * @throws MyPetCareException When the request fails
     */
    public UserMedalData getMedal(String token, String owner, String medalName)
            throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(USERMEDAL_PARAMETER, medalName);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpResponse response = httpClient
                .get(USERMEDAL_PATH + HttpParameter.encode(owner) + "/"
                        + HttpParameter.encode(medalName), params, headers, null);
        return gson.fromJson(response.asString(), UserMedalData.class);
    }

    /**
     * Gets all medals from one user.
     * @param token The user's personal access token
     * @param owner Name of user.
     * @return List with all medals.
     * @throws MyPetCareException When the request fails
     */
    public List<UserMedalData> getAllMedals(String token, String owner) throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpResponse response = httpClient.get(USERMEDAL_PATH + HttpParameter.encode(owner),
                null, headers, null);
        Type listType = TypeToken.getParameterized(List.class, UserMedalData.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Update a field from one medal.
     * @param token The user's personal access token
     * @param owner The user's name
     * @param medalName The medal's name
     * @param field The field-medal's name
     * @param value The new value of the field
     * @throws MyPetCareException When the request fails
     */
    public void updateField(String token, String owner, String medalName, String field,
                           Object value) throws MyPetCareException {
        UserMedalData.checkFieldAndValues(field, value);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        String body = "{\n"
                + "  \"value\":" + value + "\n"
                + "} ";
        String user = HttpParameter.encode(owner);
        String medal = HttpParameter.encode(medalName);
        String fieldHttp = HttpParameter.encode(field);
        httpClient.put(USERMEDAL_PATH + user + "/" + medal + "/" + fieldHttp, null,
                headers, body);
    }

    /**
     * Return the value of one field requested from a medal.
     * @param token The user's personal access token
     * @param owner The user's name
     * @param medalName The medal's name
     * @param field The field-medal's name
     * @return The value of the field requested
     * @throws MyPetCareException When the request fails
     */
    public Object getField(String token, String owner, String medalName, String field)
            throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        String user = HttpParameter.encode(owner);
        String medal = HttpParameter.encode(medalName);
        String fieldHttp = HttpParameter.encode(field);
        HttpResponse response = httpClient
                .get(USERMEDAL_PATH + user + "/" + medal + "/" + fieldHttp, null,
                        headers, null);
        return gson.fromJson(response.asString(), Object.class);
    }
}
