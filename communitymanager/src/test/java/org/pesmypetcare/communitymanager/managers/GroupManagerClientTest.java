package org.pesmypetcare.communitymanager.managers;

import android.util.Base64;

import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pesmypetcare.communitymanager.BuildConfig;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;
import org.pesmypetcare.communitymanager.datacontainers.ImageData;
import org.pesmypetcare.communitymanager.datacontainers.TagData;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Santiago Del Rey
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class GroupManagerClientTest {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String IMAGE_STORAGE_BASE_URL = BASE_URL + "storage/image/";
    private static final String COMMUNITY_BASE_URL = BASE_URL + "community";
    private static final String GROUP_KEY = "group";
    private static final String TAGS_PATH = "/tags";
    private static final String USERNAME_PARAMETER = "username";
    private static final String TOKEN_HEADER = "token";
    private static final String GROUP_ICON_SUFIX = "-icon";
    private static String group;
    private static String creator;
    private static String token;
    private static String groupJson;
    private static String groupNameEncoded;
    private static String date;
    private static GroupData groupData;
    private static Gson gson;
    private static Map<String, String> tokenHeader;
    private static List<String> groupList;

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;

    @InjectMocks
    private GroupManagerClient client = new GroupManagerClient();

    @BeforeClass
    public static void setUp() {
        gson = new Gson();
        group = "Dogs";
        creator = "John";
        token = "token";
        groupNameEncoded = HttpParameter.encode(group);
        groupData = new GroupData(group, creator, "This is a group", Arrays.asList("dogs", "huskies"));
        tokenHeader = new HashMap<>();
        tokenHeader.put(token, token);
        date = "2020-05-12";
        groupJson = gson.toJson(groupData);
        groupList = Collections.singletonList("Dogs");
    }

    @Test
    public void createGroup() throws MyPetCareException {
        given(httpClient.post(anyString(), isNull(), isNull(), anyString())).willReturn(httpResponse);

        client.createGroup(groupData);
        verify(httpClient).post(eq(COMMUNITY_BASE_URL), isNull(), isNull(), eq(groupJson));
    }

    @Test
    public void deleteGroup() throws MyPetCareException {
        given(httpClient.delete(anyString(), any(HttpParameter[].class), isNull(), isNull())).willReturn(httpResponse);

        client.deleteGroup(group);

        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(GROUP_KEY, group);
        verify(httpClient).delete(eq(COMMUNITY_BASE_URL), eq(params), isNull(), isNull());
    }

    @Test
    public void getGroup() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(GROUP_KEY, group);
        given(httpClient.get(eq(COMMUNITY_BASE_URL), eq(params), isNull(), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(groupJson);

        GroupData result = client.getGroup(group);
        assertEquals("Should return the requested group data.", groupData, result);
    }

    @Test
    public void getAllGroups() throws MyPetCareException {
        given(httpClient.get(eq(COMMUNITY_BASE_URL), isNull(), isNull(), isNull())).willReturn(httpResponse);
        List<GroupData> groups = new ArrayList<>();
        groups.add(groupData);
        given(httpResponse.asString()).willReturn(gson.toJson(groups));

        List<GroupData> response = client.getAllGroups();
        assertEquals("Should return all the existing groups.", groups, response);
    }

    @Test
    public void getAllTags() throws MyPetCareException {
        given(httpClient.get(eq(COMMUNITY_BASE_URL + TAGS_PATH), isNull(), isNull(), isNull()))
                .willReturn(httpResponse);

        Map<String, TagData> tags = new HashMap<>();
        tags.put("dogs", new TagData(groupList, true));
        given(httpResponse.asString()).willReturn(gson.toJson(tags));
        Map<String, TagData> response = client.getAllTags();
        assertEquals("Should return all the existing tags.", tags, response);
    }

    @Test
    public void updateField() throws MyPetCareException {
        given(httpClient.put(anyString(), any(HttpParameter[].class), isNull(), anyString())).willReturn(httpResponse);

        String field = "name";
        String newName = "Cats";
        client.updateField(group, field, newName);

        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(GROUP_KEY, group);
        params[1] = new HttpParameter("field", field);
        Map<String, String> map = new HashMap<>();
        map.put("value", newName);
        verify(httpClient).put(eq(COMMUNITY_BASE_URL), eq(params), isNull(), eq(gson.toJson(map)));
    }

    @Test
    public void updateGroupTags() throws MyPetCareException {
        given(httpClient.put(anyString(), any(HttpParameter[].class), isNull(), anyString())).willReturn(httpResponse);

        client.updateGroupTags(group, groupList, groupList);

        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(GROUP_KEY, group);
        Map<String, List<String>> newValue = new HashMap<>();
        newValue.put("deleted", groupList);
        newValue.put("new", groupList);
        verify(httpClient).put(eq(COMMUNITY_BASE_URL + TAGS_PATH), eq(params), isNull(), eq(gson.toJson(newValue)));
    }

    @Test
    public void subscribe() throws MyPetCareException {
        given(httpClient.post(anyString(), any(HttpParameter[].class), anyMap(), isNull())).willReturn(httpResponse);

        client.subscribe(token, group, creator);

        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(GROUP_KEY, group);
        params[1] = new HttpParameter(USERNAME_PARAMETER, creator);
        verify(httpClient).post(eq(COMMUNITY_BASE_URL + "/subscribe"), eq(params), eq(tokenHeader), isNull());
    }

    @Test
    public void unsubscribe() throws MyPetCareException {
        given(httpClient.delete(anyString(), any(HttpParameter[].class), anyMap(), isNull())).willReturn(httpResponse);

        client.unsubscribe(token, group, creator);

        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(GROUP_KEY, group);
        params[1] = new HttpParameter(USERNAME_PARAMETER, creator);
        verify(httpClient).delete(eq(COMMUNITY_BASE_URL + "/unsubscribe"), eq(params), eq(tokenHeader), isNull());
    }

    @Test
    public void updateGroupIcon() throws MyPetCareException {
        given(httpClient.put(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.updateGroupIcon(token, group, group.getBytes());

        ImageData imageData = new ImageData(group, group.getBytes());
        imageData.setImgName(group + GROUP_ICON_SUFIX);
        verify(httpClient).put(eq(IMAGE_STORAGE_BASE_URL + groupNameEncoded), isNull(), eq(tokenHeader),
                eq(gson.toJson(imageData)));
    }

    @Test
    public void getGroupIcon() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("name", group + GROUP_ICON_SUFIX);
        given(httpClient.get(eq(IMAGE_STORAGE_BASE_URL + "groups/" + groupNameEncoded), eq(params), isNull(), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn("icQUSDd7");
        mockStatic(Base64.class);
        given(Base64.decode("icQUSDd7", Base64.DEFAULT)).willReturn(group.getBytes());

        byte[] img = client.getGroupIcon(group);
        assertArrayEquals("Should return the group icon.", group.getBytes(), img);
    }
}
