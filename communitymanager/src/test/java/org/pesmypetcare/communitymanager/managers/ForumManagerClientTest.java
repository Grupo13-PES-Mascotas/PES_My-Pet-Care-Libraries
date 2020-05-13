package org.pesmypetcare.communitymanager.managers;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.httptools.RequestMethod;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ForumManagerClientTest {
    private static final String BASE_URL = "https://image-branch-testing.herokuapp.com/";
    private static final String COMMUNITY_BASE_URL = "https://image-branch-testing.herokuapp.com/community/";
    private String parentGroup;
    private String forumName;
    private String creator;
    private ForumData forum;
    private Gson gson;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;

    @InjectMocks
    private ForumManagerClient client = new ForumManagerClient();

    @Before
    public void setUp() {
        gson = new Gson();
        parentGroup = "Dogs";
        forumName = "Huskies";
        creator = "John";
        forum = new ForumData(forumName, creator, null);
    }

    @Test
    public void createForum() throws MyPetCareException {
        given(httpClient.request(any(RequestMethod.class), anyString(), isNull(), isNull(), anyString())).willReturn(
                httpResponse);

        client.createForum(parentGroup, forum);
        verify(httpClient).request(same(RequestMethod.POST), eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)),
                isNull(), isNull(), eq(gson.toJson(forum)));
    }

    @Test
    public void deleteForum() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("forum", forumName);
        given(httpClient.request(any(RequestMethod.class), anyString(), any(HttpParameter[].class), isNull(), isNull()))
                .willReturn(httpResponse);

        client.deleteForum(parentGroup, forumName);
        verify(httpClient).request(same(RequestMethod.DELETE),
                eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)), eq(params), isNull(), isNull());
    }

    @Test
    public void getForum() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("forum", forumName);
        given(httpClient.request(same(RequestMethod.GET), eq(COMMUNITY_BASE_URL + HttpParameter.encode(parentGroup)),
                eq(params), isNull(), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(forum));

        ForumData forumData = client.getForum(parentGroup, forumName);
        assertEquals("Should return the requested forum data.", forum, forumData);

    }

    @Test
    public void getAllForums() {
    }

    @Test
    public void updateName() {
    }

    @Test
    public void updateTags() {
    }

    @Test
    public void postMessage() {
    }

    @Test
    public void deleteMessage() {
    }
}
