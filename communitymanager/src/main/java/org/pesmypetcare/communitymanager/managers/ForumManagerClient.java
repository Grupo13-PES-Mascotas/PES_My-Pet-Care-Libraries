package org.pesmypetcare.communitymanager.managers;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.communitymanager.BuildConfig;
import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.communitymanager.datacontainers.MessageSendData;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class ForumManagerClient {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String COMMUNITY_BASE_URL = BASE_URL + "community/";
    private static final String TOKEN_HEADER = "token";
    private final String FORUM_PARAMETER = "forum";
    private final String CREATOR_PARAMETER = "creator";
    private final String DATE_PARAMETER = "date";
    private final String REPORTER_PARAMETER = "reporter";
    private HttpClient httpClient;
    private final Gson gson;

    /**
     * Default constructor.
     */
    public ForumManagerClient() {
        httpClient = new HttpClient();
        gson = new Gson();
    }

    /**
     * Creates a new forum.
     *
     * @param parentGroup The parent group name
     * @param forum The forum data
     * @throws MyPetCareException When the request fails
     */
    public void createForum(String parentGroup, ForumData forum) throws MyPetCareException {
        httpClient.post(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup), null, null,
            gson.toJson(forum));
    }

    /**
     * Deletes a forum.
     *
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @throws MyPetCareException When the request fails
     */
    public void deleteForum(String parentGroup, String forumName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(FORUM_PARAMETER, forumName);
        httpClient.delete(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup), params, null, null);
    }

    /**
     * Retrieves a forum information.
     *
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @return The forum data
     * @throws MyPetCareException When the request fails
     */
    public ForumData getForum(String parentGroup, String forumName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(FORUM_PARAMETER, forumName);
        HttpResponse response = httpClient
                .get(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup), params, null, null);
        return gson.fromJson(response.asString(), ForumData.class);
    }

    /**
     * Retrieves all forums information from a group.
     *
     * @param groupName The group name
     * @return All data of the forums belonging to the group
     * @throws MyPetCareException When the request fails
     */
    public List<ForumData> getAllForums(String groupName) throws MyPetCareException {
        HttpResponse response = httpClient.get(COMMUNITY_BASE_URL + HttpParameter.encode(groupName), null,
            null, null);
        Type listType = TypeToken.getParameterized(List.class, ForumData.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Updates the forum name.
     *
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param newName The new name
     * @throws MyPetCareException When the request fails
     */
    public void updateName(String parentGroup, String forumName, String newName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("newName", newName);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.put(COMMUNITY_BASE_URL + group + "/" + forum, params, null, null);
    }

    /**
     * Updates the forum tags.
     *
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param deletedTags The deleted tags
     * @param newTags The new tags
     * @throws MyPetCareException When the request fails
     */
    public void updateTags(String parentGroup, String forumName, List<String> deletedTags, List<String> newTags)
            throws MyPetCareException {
        Map<String, List<String>> newValue = new HashMap<>();
        newValue.put("deleted", deletedTags);
        newValue.put("new", newTags);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.put(COMMUNITY_BASE_URL + "/tags/" + group + "/" + forum, null, null,
            gson.toJson(newValue));
    }

    /**
     * Posts a message in a forum.
     *
     * @param token The user's personal access token
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param message The message data
     * @throws MyPetCareException When the request fails
     */
    public void postMessage(String token, String parentGroup, String forumName, MessageSendData message)
            throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.post(COMMUNITY_BASE_URL + group + "/" + forum, null, headers, gson.toJson(message));
    }

    /**
     * Deletes a message from a forum.
     *
     * @param token The user's personal access token
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param creatorName The creator's username
     * @param date The message creation date
     * @throws MyPetCareException When the request fails
     */
    public void deleteMessage(String token, String parentGroup, String forumName, String creatorName, String date)
        throws MyPetCareException {
        date = DateTime.convertLocalToUTCString(date);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(CREATOR_PARAMETER, creatorName);
        params[1] = new HttpParameter(DATE_PARAMETER, date);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.delete(COMMUNITY_BASE_URL + group + "/" + forum, params, headers, null);
    }

    /**
     * Reports a message from a forum.
     *
     * @param token The user's personal access token
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param creatorName The creator's username
     * @param reporterName The reporter's username
     * @param date The message creation date
     * @throws MyPetCareException When the request fails
     */
    public void reportMessage(String token, String parentGroup, String forumName, String creatorName,
                              String reporterName, String date)
        throws MyPetCareException {
        date = DateTime.convertLocalToUTCString(date);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpParameter[] params = new HttpParameter[3];
        params[0] = new HttpParameter(CREATOR_PARAMETER, creatorName);
        params[1] = new HttpParameter(DATE_PARAMETER, date);
        params[2] = new HttpParameter(REPORTER_PARAMETER, reporterName);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.put(COMMUNITY_BASE_URL + group + "/" + forum + "/report_message", params, headers, null);
    }

    /**
     * Unbans a message from a forum.
     *
     * @param token The user's personal access token
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param creatorName The creator's username
     * @param date The message creation date
     * @throws MyPetCareException When the request fails
     */
    public void unbanMessage(String token, String parentGroup, String forumName, String creatorName, String date)
        throws MyPetCareException {
        date = DateTime.convertLocalToUTCString(date);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(CREATOR_PARAMETER, creatorName);
        params[1] = new HttpParameter(DATE_PARAMETER, date);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.put(COMMUNITY_BASE_URL + group + "/" + forum + "/unban_message", params, headers, null);
    }

    /**
     * Gets all post images from a forum.
     *
     * @param token The user's personal access token
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @return A map with the image path and the image
     * @throws MyPetCareException When the request fails
     */
    public Map<String, byte[]> getAllPostsImagesFromForum(String token, String parentGroup, String forumName)
            throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        HttpResponse response = httpClient.get(BASE_URL + "storage/image/" + group + "/" + forum, null,
            headers, null);
        Type mapType = TypeToken.getParameterized(Map.class, String.class, String.class).getType();
        Map<String, String> responseMap = gson.fromJson(response.asString(), mapType);
        Map<String, byte[]> images = new HashMap<>();
        for (String key : responseMap.keySet()) {
            images.put(key, Base64.decode(responseMap.get(key), Base64.DEFAULT));
        }
        return images;
    }

    /**
     * Adds or removes a like from a message.
     *
     * @param token The user's personal access token
     * @param username The user's username
     * @param parentGroup The parent group name
     * @param forumName The forum name
     * @param creatorName The creator's name
     * @param date The publication date of the message
     * @param like If true adds a like else removes it
     * @throws MyPetCareException When the request fails
     */
    public void likeMessage(String token, String username, String parentGroup, String forumName, String creatorName,
            String date, boolean like) throws MyPetCareException {
        date = DateTime.convertLocalToUTCString(date);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, token);
        HttpParameter[] params = new HttpParameter[4];
        params[0] = new HttpParameter("username", username);
        params[1] = new HttpParameter(CREATOR_PARAMETER, creatorName);
        params[2] = new HttpParameter(DATE_PARAMETER, date);
        params[3] = new HttpParameter("like", like);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        httpClient.put(COMMUNITY_BASE_URL + group + "/" + forum + "/messages", params, headers, null);
    }
}
