package org.pesmypetcare.communitymanager.managers;

import android.util.Base64;

import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.communitymanager.datacontainers.MessageSendData;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ForumManagerClientTest {
    private static final String BASE_URL = "https://image-branch-testing.herokuapp.com/";
    private static final String COMMUNITY_BASE_URL = "https://image-branch-testing.herokuapp.com/community/";
    private static final String FORUM_PARAMETER = "forum";
    private static final String CREATOR_PARAMETER = "creator";
    private static final String DATE_PARAMETER = "date";
    private static String parentGroup;
    private static String forumName;
    private static String creator;
    private static String token;
    private static String groupNameEncoded;
    private static String forumNameEncoded;
    private static String date;
    private static ForumData forum;
    private static Gson gson;
    private static Map<String, String> headers;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;

    @InjectMocks
    private ForumManagerClient client = new ForumManagerClient();

    @BeforeClass
    public static void setUpClass() {
        gson = new Gson();
        parentGroup = "Dogs";
        forumName = "Huskies";
        creator = "John";
        token = "token";
        groupNameEncoded = HttpParameter.encode(parentGroup);
        forum = new ForumData(forumName, creator, null);
        forumNameEncoded = HttpParameter.encode(forumName);
        headers = new HashMap<>();
        headers.put(token, token);
        date = "2020-05-12";
    }

    @Test
    public void createForum() throws MyPetCareException {
        given(httpClient.post(anyString(), isNull(), isNull(), anyString())).willReturn(httpResponse);

        client.createForum(parentGroup, forum);
        verify(httpClient).post(eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)), isNull(), isNull(),
                eq(gson.toJson(forum)));
    }

    @Test
    public void deleteForum() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(FORUM_PARAMETER, forumName);
        given(httpClient.delete(anyString(), any(HttpParameter[].class), isNull(), isNull())).willReturn(httpResponse);

        client.deleteForum(parentGroup, forumName);
        verify(httpClient)
                .delete(eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)), eq(params), isNull(), isNull());
    }

    @Test
    public void getForum() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(FORUM_PARAMETER, forumName);
        given(httpClient
                .get(eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)), eq(params), isNull(), isNull()))
                .willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(forum));

        ForumData forumData = client.getForum(parentGroup, forumName);
        assertEquals("Should return the requested forum data.", forum, forumData);
    }

    @Test
    public void getAllForums() throws MyPetCareException {
        given(httpClient.get(eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)), isNull(), isNull(), isNull()))
                .willReturn(httpResponse);
        List<ForumData> forums = new ArrayList<>();
        forums.add(forum);
        given(httpResponse.asString()).willReturn(gson.toJson(forums));
        List<ForumData> response = client.getAllForums(parentGroup);
        assertEquals("Should return all the existing forums in a group.", forums, response);
    }

    @Test
    public void updateName() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        String newName = "German Shepherds";
        params[0] = new HttpParameter("newName", newName);
        given(httpClient.put(anyString(), any(HttpParameter[].class), isNull(), isNull())).willReturn(httpResponse);

        client.updateName(parentGroup, forumName, newName);
        verify(httpClient).put(eq(COMMUNITY_BASE_URL + groupNameEncoded + "/" + forumNameEncoded), eq(params), isNull(),
                isNull());
    }

    @Test
    public void updateTags() throws MyPetCareException {
        List<String> tags = new ArrayList<>();
        tags.add("Dogs");
        Map<String, List<String>> newValue = new HashMap<>();
        newValue.put("deleted", tags);
        newValue.put("new", tags);
        given(httpClient.put(anyString(), isNull(), isNull(), anyString())).willReturn(httpResponse);

        client.updateTags(parentGroup, forumName, tags, tags);
        verify(httpClient)
                .put(eq(COMMUNITY_BASE_URL + "/tags/" + groupNameEncoded + "/" + forumNameEncoded), isNull(), isNull(),
                        eq(gson.toJson(newValue)));
    }

    @Test
    public void postMessage() throws MyPetCareException {
        given(httpClient.post(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        MessageSendData messageSendData = new MessageSendData(creator, "Hello World!");
        client.postMessage(token, parentGroup, forumName, messageSendData);
        verify(httpClient)
                .post(eq(COMMUNITY_BASE_URL + groupNameEncoded + "/" + forumNameEncoded), isNull(), eq(headers),
                        eq(gson.toJson(messageSendData)));
    }

    @Test
    public void deleteMessage() throws MyPetCareException {
        given(httpClient.delete(anyString(), any(HttpParameter[].class), anyMap(), isNull())).willReturn(httpResponse);

        client.deleteMessage(token, parentGroup, forumName, creator, date);
        HttpParameter[] params = new HttpParameter[2];
        params[0] = new HttpParameter(CREATOR_PARAMETER, creator);
        params[1] = new HttpParameter(DATE_PARAMETER, date);
        verify(httpClient)
                .delete(eq(COMMUNITY_BASE_URL + groupNameEncoded + "/" + forumNameEncoded), eq(params), eq(headers),
                        isNull());
    }

    @Test
    public void getAllPostsImagesFromForum() throws MyPetCareException {
        given(httpClient
                .get(BASE_URL + "storage/image/" + groupNameEncoded + "/" + forumNameEncoded, null, headers, null))
                .willReturn(httpResponse);
        Map<String, String> response = new HashMap<>();
        response.put("key", forumName);
        Map<String, byte[]> images = new HashMap<>();
        images.put("key", Base64.decode(forumName, Base64.DEFAULT));
        given(httpResponse.asString()).willReturn(gson.toJson(response));

        Map<String, byte[]> result = client.getAllPostsImagesFromForum(token, parentGroup, forumName);
        assertEquals("Should return all the post images of a forum.", images, result);
    }

    @Test
    public void likeMessage() throws MyPetCareException {
        given(httpClient.put(anyString(), any(HttpParameter[].class), anyMap(), isNull())).willReturn(httpResponse);

        client.likeMessage(token, creator, parentGroup, forumName, creator, date, true);
        HttpParameter[] params = new HttpParameter[4];
        params[0] = new HttpParameter("username", creator);
        params[1] = new HttpParameter(CREATOR_PARAMETER, creator);
        params[2] = new HttpParameter(DATE_PARAMETER, date);
        params[3] = new HttpParameter("like", true);
        verify(httpClient)
                .put(eq(COMMUNITY_BASE_URL + groupNameEncoded + "/" + forumNameEncoded + "/messages"), eq(params),
                        eq(headers), isNull());
    }
}
