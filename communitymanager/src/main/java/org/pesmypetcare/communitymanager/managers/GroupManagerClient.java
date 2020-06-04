package org.pesmypetcare.communitymanager.managers;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.communitymanager.BuildConfig;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;
import org.pesmypetcare.communitymanager.datacontainers.ImageData;
import org.pesmypetcare.communitymanager.datacontainers.TagData;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class GroupManagerClient {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String IMAGE_STORAGE_BASE_URL = BASE_URL + "storage/image/";
    private static final String COMMUNITY_BASE_URL = BASE_URL + "community";
    private static final String GROUP_KEY = "group";
    private static final String TAGS_PATH = "/tags";
    private static final String USERNAME_PARAMETER = "username";
    private static final String TOKEN_HEADER = "token";
    private static final String GROUP_ICON_SUFIX = "-icon";
    private final Gson gson;
    private HttpClient httpClient;
    private Map<String, String> httpHeaders;

    /**
     * Default constructor.
     */
    public GroupManagerClient() {
        gson = new Gson();
        httpClient = new HttpClient();
        httpHeaders = new HashMap<>();
    }

    /**
     * Creates a new group.
     *
     * @param accessToken The user's access token
     * @param group The group data
     * @throws MyPetCareException When the request fails
     */
    public void createGroup(String accessToken, GroupData group) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        httpClient.post(COMMUNITY_BASE_URL, null, httpHeaders, gson.toJson(group));
    }

    /**
     * Deletes a group.
     *
     * @param accessToken The user's access token
     * @param groupName The group name
     * @throws MyPetCareException When the request fails
     */
    public void deleteGroup(String accessToken, String groupName) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(GROUP_KEY, groupName);
        httpClient.delete(COMMUNITY_BASE_URL, params, httpHeaders, null);
    }

    /**
     * Retrieves a group information.
     *
     * @param groupName The group name
     * @return The group data
     * @throws MyPetCareException When the request fails
     */
    public GroupData getGroup(String groupName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(GROUP_KEY, groupName);
        HttpResponse response = httpClient.get(COMMUNITY_BASE_URL, params, null, null);
        return gson.fromJson(response.asString(), GroupData.class);
    }

    /**
     * Retrieves all groups information.
     *
     * @return All groups data
     * @throws MyPetCareException When the request fails
     */
    public List<GroupData> getAllGroups() throws MyPetCareException {
        HttpResponse response = httpClient.get(COMMUNITY_BASE_URL, null, null, null);
        Type listType = TypeToken.getParameterized(List.class, GroupData.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Gets all the existing tags in the application.
     *
     * @return A map with the tag name as the key and its data
     * @throws MyPetCareException When the request fails
     */
    public Map<String, TagData> getAllTags() throws MyPetCareException {
        HttpResponse resp = httpClient.get(COMMUNITY_BASE_URL + TAGS_PATH, null, null, null);
        Type mapType = TypeToken.getParameterized(Map.class, String.class, TagData.class).getType();
        return gson.fromJson(resp.asString(), mapType);
    }

    /**
     * Updates a field of a group.
     *
     * @param accessToken The user's access token
     * @param groupName The group name
     * @param field The field to update
     * @param newValue The new field value
     * @throws MyPetCareException When the request fails
     */
    public void updateField(String accessToken, String groupName, String field, String newValue) throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(GROUP_KEY, groupName);
        params[1] = new HttpParameter("field", field);
        Map<String, String> map = new HashMap<>();
        map.put("value", newValue);
        httpClient.put(COMMUNITY_BASE_URL, params, httpHeaders, gson.toJson(map));
    }

    /**
     * Updates the tags of a group.
     *
     * @param accessToken The user's access token
     * @param groupName The group name
     * @param deletedTags The list of deleted tags
     * @param newTags The list of new tags
     * @throws MyPetCareException When the request fails
     */
    public void updateGroupTags(String accessToken, String groupName, List<String> deletedTags, List<String> newTags)
            throws MyPetCareException {
        httpHeaders.put(TOKEN_HEADER, accessToken);
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(GROUP_KEY, groupName);
        Map<String, List<String>> newValue = new HashMap<>();
        newValue.put("deleted", deletedTags);
        newValue.put("new", newTags);
        httpClient.put(COMMUNITY_BASE_URL + TAGS_PATH, params, httpHeaders, gson.toJson(newValue));
    }

    /**
     * Subscribes a user to a group.
     *
     * @param token The user's personal access token
     * @param groupName The group name
     * @param username The user's username
     * @throws MyPetCareException When the request fails
     */
    public void subscribe(String token, String groupName, String username) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(GROUP_KEY, groupName);
        params[1] = new HttpParameter(USERNAME_PARAMETER, username);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        httpClient.post(COMMUNITY_BASE_URL + "/subscribe", params, headers, null);
    }

    /**
     * Unsubscribes a user to a group.
     *
     * @param token The user's personal access token
     * @param groupName The group name
     * @param username The user's username
     * @throws MyPetCareException When the request fails
     */
    public void unsubscribe(String token, String groupName, String username) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(GROUP_KEY, groupName);
        params[1] = new HttpParameter(USERNAME_PARAMETER, username);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        httpClient.delete(COMMUNITY_BASE_URL + "/unsubscribe", params, headers, null);
    }

    /**
     * Updates the icon of a group.
     *
     * @param token The user's personal access token
     * @param groupName The group name
     * @param image The new icon
     * @throws MyPetCareException When the request fails
     */
    public void updateGroupIcon(String token, String groupName, byte[] image) throws MyPetCareException {
        ImageData imageData = new ImageData(groupName, image);
        imageData.setImgName(groupName + GROUP_ICON_SUFIX);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        httpClient.put(IMAGE_STORAGE_BASE_URL + HttpParameter.encode(groupName), null, headers, gson.toJson(imageData));
    }

    /**
     * Gets the icon of a group.
     *
     * @param groupName The group name
     * @return The icon as a byte array
     * @throws MyPetCareException When the request fails
     */
    public byte[] getGroupIcon(String groupName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("name", groupName + GROUP_ICON_SUFIX);
        HttpResponse response = httpClient
                .get(IMAGE_STORAGE_BASE_URL + "groups/" + HttpParameter.encode(groupName), params, null, null);
        return Base64.decode(response.asString(), Base64.DEFAULT);
    }
}
