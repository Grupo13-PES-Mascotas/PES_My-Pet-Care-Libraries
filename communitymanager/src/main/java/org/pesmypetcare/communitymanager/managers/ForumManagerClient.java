package org.pesmypetcare.communitymanager.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.communitymanager.datacontainers.MessageData;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.httptools.RequestMethod;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class ForumManagerClient {
    private static final String BASE_URL = "https://image-branch-testing.herokuapp.com/community/";
    private final Gson gson;

    public ForumManagerClient() {
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
        new HttpClient().request(RequestMethod.POST, BASE_URL + HttpParameter.encode(parentGroup), null, null, gson.toJson(forum));
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
        params[0] = new HttpParameter("forum", forumName);
        new HttpClient().request(RequestMethod.DELETE, BASE_URL + HttpParameter.encode(parentGroup), params, null, null);
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
        params[0] = new HttpParameter("forum", forumName);
        HttpResponse response = new HttpClient().request(RequestMethod.GET, BASE_URL + HttpParameter.encode(parentGroup), params, null, null);
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
        HttpResponse response = new HttpClient().request(RequestMethod.GET, BASE_URL + HttpParameter.encode(groupName), null, null, null);
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
    public void updateName(String parentGroup, String forumName, String newName)
        throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("newName", newName);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        new HttpClient().request(RequestMethod.PUT, BASE_URL + group + "/" + forum, params, null, null);
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
    public void updateTags(String parentGroup, String forumName, List<String> deletedTags, List<String> newTags) throws MyPetCareException {
        Map<String, List<String>> newValue = new HashMap<>();
        newValue.put("deleted", deletedTags);
        newValue.put("new", newTags);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        new HttpClient().request(RequestMethod.PUT, BASE_URL + "/tags/" + group + "/" + forum, null, null, gson.toJson(newValue));
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
    public void postMessage(String token, String parentGroup, String forumName, MessageData message) throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put("token", token);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        new HttpClient().request(RequestMethod.POST, BASE_URL + group + "/" + forum, null, headers, gson.toJson(message));
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
    public void deleteMessage(String token, String parentGroup, String forumName, String creatorName, String date) throws MyPetCareException {
        Map<String, String> headers = new HashMap<>();
        headers.put("token", token);
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter("creator", creatorName);
        params[1] = new HttpParameter("date", date);
        String group = HttpParameter.encode(parentGroup);
        String forum = HttpParameter.encode(forumName);
        new HttpClient().request(RequestMethod.DELETE, BASE_URL + group + "/" + forum, params, headers, null);
    }
}
