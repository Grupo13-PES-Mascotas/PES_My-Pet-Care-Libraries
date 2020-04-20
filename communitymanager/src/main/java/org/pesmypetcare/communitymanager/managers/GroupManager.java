package org.pesmypetcare.communitymanager.managers;

import com.google.gson.Gson;

import org.pesmypetcare.communitymanager.HttpClient;
import org.pesmypetcare.communitymanager.HttpParameter;
import org.pesmypetcare.communitymanager.HttpResponse;
import org.pesmypetcare.communitymanager.MyPetCareException;
import org.pesmypetcare.communitymanager.RequestMethod;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;

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
        String json = gson.toJson(group);
        System.out.println(json);
        HttpResponse response = new HttpClient().request(RequestMethod.POST, BASE_URL, null, null, json);
        return response;
    }

    public HttpResponse deleteGroup(String groupName) throws MyPetCareException {
        HttpParameter[] parameters = new HttpParameter[1];
        parameters[0] = new HttpParameter("group", groupName);
        HttpResponse response = new HttpClient().request(RequestMethod.DELETE, BASE_URL, parameters, null, null);
        return response;
    }

    public String getGroup(String groupName) throws MyPetCareException {
        HttpParameter[] parameters = new HttpParameter[1];
        parameters[0] = new HttpParameter("group", groupName);
        HttpResponse response = new HttpClient().request(RequestMethod.GET, BASE_URL, parameters, null, null);
        return response.asString();
    }
}
