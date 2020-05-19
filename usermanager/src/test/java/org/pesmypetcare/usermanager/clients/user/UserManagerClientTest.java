package org.pesmypetcare.usermanager.clients.user;

import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.user.UserData;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Santiago Del Rey
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class UserManagerClientTest {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String USERS_PATH = "users/";
    private static final String IMAGES_PATH = "storage/image/";
    private static final String EMAIL = "user@email.com";
    private static final String UID = "312eeAD";
    private static final String USERNAME = "John";
    private static final String PASSWORD = "123456";
    private static final String ACCESS_TOKEN = "my-token";
    private static final String EMAIL_FIELD = "email";
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String TOKEN_HEADER = "token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private final int expectedResponseCode = 200;
    private UserData user;
    private StringBuilder json;
    private UserData expected;
    private byte[] image;
    private Map<String, String> headers;
    private Gson gson;

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;
    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private UserManagerClient client = new UserManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        gson = new Gson();
        user = new UserData(USERNAME, EMAIL, PASSWORD);
        json = new StringBuilder(gson.toJson(user));
        expected = gson.fromJson(json.toString(), UserData.class);
        image = json.toString().getBytes();
        headers = new HashMap<>();
        headers.put(TOKEN_HEADER, ACCESS_TOKEN);
    }

    @Test
    public void signUp() throws ExecutionException, InterruptedException, JSONException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(BASE_URL + "signup", "")).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.createUser(UID, user);
        verify(taskManager).setTaskId("POST");
        verify(taskManager).setReqBody(isA(JSONObject.class));
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
    }

    @Test
    public void usernameAlreadyExists() throws MyPetCareException {
        given(httpClient.get(anyString(), any(HttpParameter[].class), isNull(), isNull())).willReturn(httpResponse);
        Map<String, Boolean> map = new HashMap<>();
        map.put("exists", true);
        String json = gson.toJson(map);
        given(httpResponse.asString()).willReturn(json);
        boolean response = client.usernameAlreadyExists(USERNAME);
        assertTrue("Should return true when username exists", response);
    }

    @Test
    public void getUser() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(BASE_URL + USERS_PATH + USERNAME, ACCESS_TOKEN)).willReturn(taskManager);
        given(taskManager.get()).willReturn(json);
        UserData response = client.getUser(ACCESS_TOKEN, USERNAME);
        verify(taskManager).setTaskId(GET);
        assertEquals("Should return the user data", expected, response);
    }

    @Test(expected = ExecutionException.class)
    public void shouldThrowAnExceptionWhenTaskExecutionFails() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.getUser(ACCESS_TOKEN, USERNAME);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenTaskExecutionInterrupted() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(InterruptedException.class).given(taskManager).get();
        client.getUser(ACCESS_TOKEN, USERNAME);
    }

    @Test
    public void deleteUser() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(BASE_URL + USERS_PATH + USERNAME, ACCESS_TOKEN)).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deleteUser(ACCESS_TOKEN, USERNAME);
        verify(taskManager).setTaskId(DELETE);
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
    }

    @Test
    public void deleteUserFromDatabase() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(BASE_URL + USERS_PATH + USERNAME + "?db=true", ACCESS_TOKEN)).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deleteUserFromDatabase(ACCESS_TOKEN, USERNAME);
        verify(taskManager).setTaskId(DELETE);
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
    }

    @Test
    public void updateField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(BASE_URL + USERS_PATH + USERNAME, ACCESS_TOKEN)).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateField(ACCESS_TOKEN, USERNAME, EMAIL_FIELD, "user01@email.com");
        verify(taskManager).setTaskId(PUT);
        verify(taskManager).setReqBody(isA(JSONObject.class));
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
    }

    @Test
    public void saveProfileImage() throws MyPetCareException {
        given(httpClient.put(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("uid", USERNAME);
        reqData.put("imgName", "profile-image");
        reqData.put("img", image);
        client.saveProfileImage(ACCESS_TOKEN, USERNAME, image);
        verify(httpClient).put(eq(BASE_URL + IMAGES_PATH), isNull(), eq(headers), eq(gson.toJson(reqData)));
    }

    @Test
    public void downloadProfileImage() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("name", "profile-image");
        given(httpClient.get(anyString(), any(HttpParameter[].class), anyMap(), isNull())).willReturn(httpResponse);
        String encodedImage = "icQUSDd7";
        given(httpResponse.asString()).willReturn(encodedImage);
        mockStatic(Base64.class);
        given(Base64.decode(encodedImage, Base64.DEFAULT)).willReturn(encodedImage.getBytes());

        byte[] response = client.downloadProfileImage(ACCESS_TOKEN, USERNAME);
        verify(httpClient)
                .get(eq(BASE_URL + IMAGES_PATH + HttpParameter.encode(USERNAME)), eq(params), eq(headers), isNull());
        assertEquals("Should return the profile image of the user", Base64.decode(encodedImage, Base64.DEFAULT),
                response);
    }

    @Test
    public void getUserSubscriptions() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("username", USERNAME);
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_HEADER, ACCESS_TOKEN);
        given(httpClient.get(eq(BASE_URL + USERS_PATH + "subscriptions"), eq(params), eq(headers), isNull()))
                .willReturn(httpResponse);
        List<String> subscriptions = Collections.singletonList("Dogs");
        given(httpResponse.asString()).willReturn(gson.toJson(subscriptions));

        List<String> result = client.getUserSubscriptions(ACCESS_TOKEN, USERNAME);
        assertEquals("Should return the user subscriptions.", subscriptions, result);
    }

    @Test
    public void sendTokenToServer() throws MyPetCareException {
        given(httpClient.put(anyString(), isNull(), anyMap(), isNull())).willReturn(httpResponse);

        String messagingToken = "sdj3nm9dak";
        headers.put("fcmToken", messagingToken);
        client.sendTokenToServer(ACCESS_TOKEN, messagingToken);
        verify(httpClient).put(eq(BASE_URL + "users"), isNull(), eq(headers), isNull());
    }
}
