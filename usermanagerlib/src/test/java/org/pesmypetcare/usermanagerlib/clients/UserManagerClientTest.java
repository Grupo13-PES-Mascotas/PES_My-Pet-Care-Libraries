package org.pesmypetcare.usermanagerlib.clients;

import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.UserData;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class UserManagerClientTest {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/";
    private static final String USERS_PATH = "users/";
    private static final String IMAGES_PATH = "storage/image/";
    private static final String EMAIL = "user@email.com";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "123456";
    private static final String ACCESS_TOKEN = "my-token";
    private static final String EMAIL_FIELD = "email";
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private final int expectedResponseCode = 200;
    private StringBuilder json;
    private UserData expected;
    private byte[] image;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private UserManagerClient client = new UserManagerClient();

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        json = new StringBuilder("{\n"
            + "  \"username\": \"user\",\n"
            + "  \"email\": \"user@email.com\"\n"
            + "}");
        Gson gson = new Gson();
        expected = gson.fromJson(json.toString(), UserData.class);
        image = json.toString().getBytes();
    }

    @Test
    public void signUp() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.signUp(USERNAME, PASSWORD, EMAIL);
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
        verify(taskManager).setTaskId("POST");
        verify(taskManager).setReqBody(isA(JSONObject.class));
        verify(taskManager).execute(BASE_URL + "signup?password=" + PASSWORD, "");
    }

    @Test
    public void getUser() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(json);
        UserData response = client.getUser(ACCESS_TOKEN, USERNAME);
        assertEquals("Should return the user data", expected, response);
        verify(taskManager).setTaskId(GET);
        verify(taskManager).execute(BASE_URL + USERS_PATH + USERNAME, ACCESS_TOKEN);
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
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deleteUser(ACCESS_TOKEN, USERNAME);
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
        verify(taskManager).setTaskId("DELETE");
        verify(taskManager).execute(BASE_URL + USERS_PATH + USERNAME + "/delete", ACCESS_TOKEN);
    }

    @Test
    public void updateField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateField(ACCESS_TOKEN, USERNAME, EMAIL_FIELD, "user01@email.com");
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
        verify(taskManager).setTaskId(PUT);
        verify(taskManager).setReqBody(isA(JSONObject.class));
        verify(taskManager).execute(BASE_URL + USERS_PATH + USERNAME + "/update/" + EMAIL_FIELD, ACCESS_TOKEN);
    }

    @Test
    public void saveProfileImage() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.saveProfileImage(ACCESS_TOKEN, USERNAME, image);
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
        verify(taskManager).setTaskId(PUT);
        verify(taskManager).setReqBody(isA(JSONObject.class));
        verify(taskManager).execute(BASE_URL + IMAGES_PATH, ACCESS_TOKEN);
    }

    @Test
    public void downloadProfileImage() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(json);
        mockStatic(Base64.class);
        given(Base64.decode(json.toString(), Base64.DEFAULT)).willReturn(image);
        byte[] response = client.downloadProfileImage(ACCESS_TOKEN, USERNAME);
        assertEquals("Should return the profile image of the user", image, response);
        verify(taskManager).setTaskId(GET);
        verify(taskManager).execute(BASE_URL + IMAGES_PATH + USERNAME + "?name=profile-image.png", ACCESS_TOKEN);
    }

    @Test(expected = ExecutionException.class)
    public void shouldThrowAnExceptionWhenExecutionFails() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.downloadProfileImage(ACCESS_TOKEN, USERNAME);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenExecutionInterrupted() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(InterruptedException.class).given(taskManager).get();
        client.downloadProfileImage(ACCESS_TOKEN, USERNAME);
    }
}
