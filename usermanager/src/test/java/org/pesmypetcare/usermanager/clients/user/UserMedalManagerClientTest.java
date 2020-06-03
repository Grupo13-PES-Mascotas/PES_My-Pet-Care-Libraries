package org.pesmypetcare.usermanager.clients.user;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


/**
 * @author Oriol Catalán
 */
@RunWith(PowerMockRunner.class)
public class UserMedalManagerClientTest {
    private static final String BASE_URL = BuildConfig.URL;
    private static final String USERMEDAL_PATH = BASE_URL + "usermedal/";
    private static final String USERMEDAL_PARAMETER = "usermedal";

    private static String medalName;
    private static String owner;
    private static String token;
    private static String medalNameEncoded;
    private static String ownerNameEncoded;
    private static String fieldNameEncoded;
    private static String field;
    private static Double value;

    private static Gson gson;
    private static UserMedalData medal;
    private static Map<String, String> headers;

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;

    @InjectMocks
    private UserMedalManagerClient client = new UserMedalManagerClient();

    @Before
    public void setUp() {
        gson = new Gson();
        owner = "Rosa Melano";
        medalName = "Walker";
        token = "token";
        field = "progress";
        value = 3.0;
        fieldNameEncoded = HttpParameter.encode(field);
        ownerNameEncoded = HttpParameter.encode(owner);
        medal = new UserMedalData(medalName,
                new ArrayList<>(Arrays.asList(5., 10., 25., 50., 100.)),
                "You have to walk a lot!", new byte[] {10, 55, 67, 89, 103, 116},
                2.0, 1.0,
                new ArrayList<>(Arrays.asList("2020-05-12", "2020-08-08", "2020-03-09")));
        medalNameEncoded = HttpParameter.encode(medalName);
        headers = new HashMap<>();
        headers.put(token, token);
    }

    @Test
    public void getMedal() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(USERMEDAL_PARAMETER, medalName);
        given(httpClient
                .get(eq(USERMEDAL_PATH + ownerNameEncoded + "/" + medalNameEncoded),
                        eq(params), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(medal));
        UserMedalData medalData = client.getMedal(token, owner, medalName);
        Assert.assertEquals("Should return the requested medal data.", medal, medalData);
    }

    @Test
    public void getAllMedals() throws MyPetCareException {
        given(httpClient.get(eq(USERMEDAL_PATH + ownerNameEncoded),
                isNull(), eq(headers), isNull())).willReturn(httpResponse);
        List<UserMedalData> medals = new ArrayList<>();
        medals.add(medal);
        given(httpResponse.asString()).willReturn(gson.toJson(medals));
        List<UserMedalData> response = client.getAllMedals(token, owner);
        Assert.assertEquals("Should return all the existing medals.", medals, response);
    }

    @Test
    public void updateField() throws MyPetCareException {
        HttpParameter[] params = new HttpParameter[1];
        params[0] = new HttpParameter(field, value);
        given(httpClient.put(anyString(), any(HttpParameter[].class), isNull(), isNull()))
                .willReturn(httpResponse);

        client.updateField(token, owner, medalName, field, value);
        verify(httpClient).put(eq(USERMEDAL_PATH + ownerNameEncoded + "/"
                        + medalNameEncoded + "/" + fieldNameEncoded), eq(params), eq(headers),
                isNull());
    }

    @Test
    public void getField() throws MyPetCareException {
        given(httpClient.get(eq(USERMEDAL_PATH + ownerNameEncoded + "/" + medalNameEncoded
                        + "/" + fieldNameEncoded), isNull(), eq(headers), isNull()))
                .willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(value));
        Object response = client.getField(token, owner, medalName, field);
        Assert.assertEquals("Should return the value of the medal field.", value,
                response);
    }
}


