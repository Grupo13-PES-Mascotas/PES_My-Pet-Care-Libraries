package org.pesmypetcare.usermanager.clients;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.EventData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Marc Simó
 */
public class GoogleCalendarManagerClientTest {
    private static final String BASE_URL = BuildConfig.URL + "calendar/";
    private static final String EVENT = "event/";
    private static final String ACCESS_TOKEN = "my-token";

    private String owner;
    private String petName;
    private EventData eventData;
    private List<EventData> eventDataList;
    private String eventId;
    private String encodedOwner;
    private String encodedPetName;
    private Map<String, String> headers;
    private Gson gson;

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;

    @InjectMocks
    private GoogleCalendarManagerClient client = new GoogleCalendarManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        gson = new Gson();
        owner = "Manolo";
        encodedOwner = HttpParameter.encode(owner);
        petName = "Kawaguchi";
        encodedPetName = HttpParameter.encode(petName);
        eventId = "eventId";
        eventData = new EventData("eventId", "My summary", "My location", "My description", EventData.BANANA, 80, 14,
                "2020-02-13T10:30:00", "2020-02-13T12:30:00");
        eventDataList = new ArrayList<>();
        eventDataList.add(eventData);
        headers = new HashMap<>();
        headers.put("token", ACCESS_TOKEN);
    }

    @Test
    public void createSecondaryCalendar() throws MyPetCareException {
        given(httpClient.post(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.createSecondaryCalendar(ACCESS_TOKEN, owner, petName);
        verify(httpClient).post(eq(BASE_URL + encodedOwner + "/" + encodedPetName), isNull(), eq(headers), isNull());
    }

    @Test
    public void deleteSecondaryCalendar() throws MyPetCareException {
        given(httpClient.delete(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.deleteSecondaryCalendar(ACCESS_TOKEN, owner, petName);
        verify(httpClient).delete(eq(BASE_URL + encodedOwner + "/" + encodedPetName), isNull(), eq(headers), isNull());
    }

    @Test
    public void getAllEventsFromCalendar() throws MyPetCareException {
        given(httpClient.get(anyString(), isNull(), anyMap(), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(eventDataList));

        List<EventData> response = client.getAllEventsFromCalendar(ACCESS_TOKEN, owner, petName);
        assertEquals("Should return Event list", eventDataList, response);
    }

    @Test
    public void createEvent() throws MyPetCareException {
        given(httpClient.post(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.createEvent(ACCESS_TOKEN, owner, petName, eventData);
        verify(httpClient).post(eq(BASE_URL + EVENT + encodedOwner + "/" + encodedPetName), isNull(), eq(headers),
                eq(gson.toJson(eventData)));
    }

    @Test
    public void updateEvent() throws MyPetCareException {
        given(httpClient.put(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.updateEvent(ACCESS_TOKEN, owner, petName, eventData);
        verify(httpClient).put(eq(BASE_URL + EVENT + encodedOwner + "/" + encodedPetName), isNull(), eq(headers),
                eq(gson.toJson(eventData)));
    }

    @Test
    public void deleteEvent() throws MyPetCareException {
        given(httpClient.delete(anyString(), isNull(), anyMap(), anyString())).willReturn(httpResponse);

        client.deleteEvent(ACCESS_TOKEN, owner, petName, eventId);

        Map<String, String> reqData = new HashMap<>();
        reqData.put("eventId", eventId);
        verify(httpClient).delete(eq(BASE_URL + EVENT + encodedOwner + "/" + encodedPetName), isNull(), eq(headers),
                eq(gson.toJson(reqData)));
    }
}
