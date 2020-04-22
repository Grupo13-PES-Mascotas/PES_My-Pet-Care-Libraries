package org.pesmypetcare.usermanagerlib.clients;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.pesmypetcare.usermanagerlib.datacontainers.EventData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Marc Simó
 */
public class GoogleCalendarManagerClient {
    private static final String BASE_URL = "https://pes-my-pet-care.herokuapp.com/calendar/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static Gson GSON = new Gson();
    private TaskManager taskManager;

    /**
     * Creates a Secondary Google Calendar in the account specified by the accessToken
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar is created for
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createSecondaryCalendar(String accessToken, String owner, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes a Secondary Google Calendar in the account specified by the accessToken
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int deleteSecondaryCalendar(String accessToken, String owner, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Returns all Calendar Events from a specified Calendar
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @return List containing all the Events from the specified Calendar
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public List<EventData> getAllEventsFromCalendar(String accessToken, String owner, String petName)
        throws ExecutionException, InterruptedException {
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        StringBuilder response = taskManager.execute(BASE_URL + owner + "/" + petName, accessToken).get();
        List<EventData> eventList = new ArrayList<>();
        if (response.length() > 2) {
            String jsonArray = response.substring(1, response.length() - 1);
            String[] events = jsonArray.split(",\\{");
            eventList.add(GSON.fromJson(events[0], EventData.class));
            for (int i = 1; i < events.length; i++) {
                events[i] = "{" + events[i];
                eventList.add(GSON.fromJson(events[i], EventData.class));
            }
        }
        return eventList;
    }

    /**
     * Creates an Event in a specified Google Calendar
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventData Event to create
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int createEvent(String accessToken, String owner, String petName, EventData eventData)
        throws ExecutionException, InterruptedException {
        JSONObject reqJson = eventData.buildJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(POST);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + "/event/" + owner + "/" + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Retrieves an Event in a specified Google Calendar
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventId Id of the event to retrieve with key
     * @return Event retrieved
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public EventData getEvent(String accessToken, String owner, String petName, String eventId)
        throws ExecutionException, InterruptedException {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("eventId", eventId);
        JSONObject reqJson = new JSONObject(reqData);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(GET);
        taskManager.setReqBody(reqJson);
        StringBuilder json = taskManager.execute(BASE_URL + "/event/" + owner + "/" + petName, accessToken).get();
        return GSON.fromJson(json.toString(), EventData.class);
    }

    /**
     * Updates an Event in a specified Google Calendar
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventData New Event that overwrites the past event with the same id
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int updateEvent(String accessToken, String owner, String petName, EventData eventData)
        throws ExecutionException, InterruptedException {
        JSONObject reqJson = eventData.buildJson();
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(PUT);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + "/event/" + owner + "/" + petName, accessToken).get();
        return Integer.parseInt(response.toString());
    }

    /**
     * Deletes an Event in a specified Google Calendar
     * @param accessToken oauth2 token needed to access the Google Calendar
     * @param owner Name of the owner of the pet
     * @param petName Name of the pet the calendar belongs to
     * @param eventId Id of the event to delete with key
     * @return The response code
     * @throws ExecutionException When the retrieval fails
     * @throws InterruptedException When the retrieval is interrupted
     */
    public int deleteEvent(String accessToken, String owner, String petName, String eventId)
        throws ExecutionException, InterruptedException {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("eventId", eventId);
        JSONObject reqJson = new JSONObject(reqData);
        taskManager = taskManager.resetTaskManager();
        taskManager.setTaskId(DELETE);
        taskManager.setReqBody(reqJson);
        StringBuilder response = taskManager.execute(BASE_URL + "/event/" + owner + "/" + petName , accessToken).get();
        return Integer.parseInt(response.toString());
    }
}
