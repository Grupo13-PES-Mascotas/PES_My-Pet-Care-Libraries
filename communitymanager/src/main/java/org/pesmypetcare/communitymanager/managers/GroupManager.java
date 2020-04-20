package org.pesmypetcare.communitymanager.managers;

import com.google.gson.Gson;

import org.pesmypetcare.communitymanager.HttpClient;
import org.pesmypetcare.communitymanager.HttpParameter;
import org.pesmypetcare.communitymanager.HttpResponse;
import org.pesmypetcare.communitymanager.MyPetCareException;
import org.pesmypetcare.communitymanager.RequestMethod;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class GroupManager {
    private static final String BASE_URL = "https://image-branch-testing.herokuapp.com/community";
    private final Gson gson;

    public GroupManager() {
        gson = new Gson();
    }

    public HttpResponse createGroup(GroupData group) throws MyPetCareException {
        return new HttpClient().request(RequestMethod.POST, BASE_URL, null, null, gson.toJson(group));
    }

    public HttpResponse deleteGroup(String groupName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("group", groupName);
        HttpResponse response = new HttpClient().request(RequestMethod.DELETE, BASE_URL, params, null, null);
        return response;
    }

    public GroupData getGroup(String groupName) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("group", groupName);
        HttpResponse response = new HttpClient().request(RequestMethod.GET, BASE_URL, params, null, null);
        return gson.fromJson(response.asString(), GroupData.class);
    }

    public GroupData getAllGroups() throws MyPetCareException {
        HttpResponse response = new HttpClient().request(RequestMethod.GET, BASE_URL, null, null, null);
        return gson.fromJson(response.asString(), GroupData.class);
    }

    public HttpResponse updateField(String groupName, String field, String newValue)
        throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter("group", groupName);
        params[1] = new HttpParameter("field", field);
        Map<String, String> map = new HashMap<>();
        map.put("value", newValue);
        return new HttpClient().request(RequestMethod.PUT, BASE_URL, params, null, gson.toJson(map));
    }

    public HttpResponse updateGroupTags(String groupName, List<String> deletedTags, List<String> newTags) throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("group", groupName);
        Map<String, List<String>> newValue = new HashMap<>();
        newValue.put("deleted", deletedTags);
        newValue.put("new", newTags);
        return new HttpClient().request(RequestMethod.PUT, BASE_URL + "/tags", params, null,
            gson.toJson(newValue));
    }
}
