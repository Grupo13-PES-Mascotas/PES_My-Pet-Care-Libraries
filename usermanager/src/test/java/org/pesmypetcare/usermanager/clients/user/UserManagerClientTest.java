package org.pesmypetcare.usermanager.clients.user;

import android.util.Base64;

import com.google.gson.Gson;

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
import org.pesmypetcare.usermanager.datacontainers.user.UserData;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
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
    private static final String TOKEN_HEADER = "token";
    private UserData user;
    private byte[] image;
    private Map<String, String> headers;
    private Gson gson;

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;
    private String encodedUid;

    @InjectMocks
    private UserManagerClient client = new UserManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        gson = new Gson();
        user = new UserData(USERNAME, EMAIL, PASSWORD);
        image = user.toString().getBytes();
        headers = new HashMap<>();
        headers.put(TOKEN_HEADER, ACCESS_TOKEN);
        encodedUid = HttpParameter.encode(UID);
    }

    @Test
    public void createUser() throws MyPetCareException {
        given(httpClient.post(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.createUser(UID, user);

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("uid", UID);
        reqBody.put("user", gson.toJson(user));
        verify(httpClient).post(eq(BASE_URL + "signup"), isNull(), isNull(), eq(gson.toJson(reqBody)));
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
    public void getUser() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + USERS_PATH + encodedUid), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(user));

        UserData response = client.getUser(ACCESS_TOKEN, UID);
        assertEquals("Should return the user data", user, response);
    }

    @Test
    public void deleteUser() throws MyPetCareException {
        given(httpClient.delete(anyString(), isNull(), anyMap(), isNull())).willReturn(httpResponse);

        client.deleteUser(ACCESS_TOKEN, UID);
        verify(httpClient).delete(eq(BASE_URL + USERS_PATH + encodedUid), isNull(), eq(headers), isNull());
    }

    @Test
    public void deleteUserFromDatabase() throws MyPetCareException {
        given(httpClient.delete(anyString(), any(HttpParameter[].class), anyMap(), isNull())).willReturn(httpResponse);

        client.deleteUserFromDatabase(ACCESS_TOKEN, UID);

        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter("db", true);
        verify(httpClient).delete(eq(BASE_URL + USERS_PATH + encodedUid), eq(params), eq(headers), isNull());

    }

    @Test
    public void updateField() throws MyPetCareException {
        given(httpClient.put(anyString(), any(HttpParameter[].class), anyMap(), anyString())).willReturn(httpResponse);

        String newEmail = "user01@email.com";
        client.updateField(ACCESS_TOKEN, USERNAME, EMAIL_FIELD, newEmail);

        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(EMAIL_FIELD, newEmail);
        verify(httpClient).put(eq(BASE_URL + USERS_PATH + HttpParameter.encode(USERNAME)), eq(params), eq(headers),
                isNull());
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
