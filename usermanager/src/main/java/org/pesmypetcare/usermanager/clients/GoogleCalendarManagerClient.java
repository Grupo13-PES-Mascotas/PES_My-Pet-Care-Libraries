package org.pesmypetcare.usermanager.clients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.EventData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marc Simó
 */
public class GoogleCalendarManagerClient {
    private static final String BASE_URL = BuildConfig.URL + "calendar/";
    private static final String EVENT = "event/";
    private static Gson gson = new Gson();
    private HttpClient httpClient;
    private Map<String, String> httpHeaders;

    public GoogleCalendarManagerClient() {
        httpClient = new HttpClient();
        httpHeaders = new HashMap<>();
    }

    /**
     * Creates a Secondary Google Calendar in the account specified by the accessToken.
     *
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar is created for
     * @throws MyPetCareException When the request fails
     */
    public void createSecondaryCalendar(String accessToken, String owner, String petName) throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        httpClient.post(BASE_URL + HttpParameter.encode(owner) + "/" + HttpParameter.encode(petName), null, httpHeaders,
                null);
    }

    /**
     * Deletes a Secondary Google Calendar in the account specified by the accessToken.
     *
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @throws MyPetCareException When the request fails
     */
    public void deleteSecondaryCalendar(String accessToken, String owner, String petName) throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        httpClient
                .delete(BASE_URL + HttpParameter.encode(owner) + "/" + HttpParameter.encode(petName), null, httpHeaders,
                        null);
    }

    /**
     * Returns all Calendar Events from a specified Calendar.
     *
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @return List containing all the Events from the specified Calendar
     * @throws MyPetCareException When the request fails
     */
    public List<EventData> getAllEventsFromCalendar(String accessToken, String owner, String petName)
            throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        HttpResponse response = httpClient
                .get(BASE_URL + HttpParameter.encode(owner) + "/" + HttpParameter.encode(petName), null, httpHeaders,
                        null);
        Type listType = TypeToken.getParameterized(List.class, EventData.class).getType();
        return gson.fromJson(response.asString(), listType);
    }

    /**
     * Creates an Event in a specified Google Calendar.
     *
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventData Event to create
     * @throws MyPetCareException When the request fails
     */
    public void createEvent(String accessToken, String owner, String petName, EventData eventData)
            throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        httpClient.post(BASE_URL + EVENT + HttpParameter.encode(owner) + "/" + HttpParameter.encode(petName), null,
                httpHeaders, gson.toJson(eventData));
    }

    /**
     * Updates an Event in a specified Google Calendar.
     *
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventData New Event that overwrites the past event with the same id
     * @throws MyPetCareException When the request fails
     */
    public void updateEvent(String accessToken, String owner, String petName, EventData eventData)
            throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        httpClient.put(BASE_URL + EVENT + HttpParameter.encode(owner) + "/" + HttpParameter.encode(petName), null,
                httpHeaders, gson.toJson(eventData));
    }

    /**
     * Deletes an Event in a specified Google Calendar.
     *
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventId Id of the event to delete with key
     * @throws MyPetCareException When the request fails
     */
    public void deleteEvent(String accessToken, String owner, String petName, String eventId)
            throws MyPetCareException {
        httpHeaders.put("token", accessToken);
        Map<String, String> reqData = new HashMap<>();
        reqData.put("eventId", eventId);
        httpClient.delete(BASE_URL + EVENT + HttpParameter.encode(owner) + "/" + HttpParameter.encode(petName), null,
                httpHeaders, gson.toJson(reqData));
    }
}
