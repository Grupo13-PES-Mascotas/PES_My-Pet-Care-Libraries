package org.pesmypetcare.usermanager.clients;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanager.datacontainers.EventData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * @author Marc Simó
 */
public class GoogleCalendarManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String SHOULD_RETURN_200 = "Should return response code 200";

    private String owner;
    private String petName;
    private EventData eventData;
    private List<EventData> eventDataList;
    private String eventId;
    private StringBuilder jsonEventData;
    private StringBuilder jsonAllEvents;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private GoogleCalendarManagerClient client = new GoogleCalendarManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        eventId = "eventId";
        eventData = new EventData("eventId", "My summary", "My location",
            "My description", EventData.BANANA, 80, 14,
            "2020-02-13T10:30:00", "2020-02-13T12:30:00");
        eventDataList = new ArrayList<>();
        eventDataList.add(eventData);
        jsonEventData = new StringBuilder("{\n"
            + "  \"id\": \"eventId\",\n"
            + "  \"summary\":\"My summary\",\n"
            + "  \"location\": \"My location\",\n"
            + "  \"description\": \"My description\",\n"
            + "  \"color\": \"5\",\n"
            + "  \"emailReminderMinutes\": 80,\n"
            + "  \"repetitionInterval\": 14,\n"
            + "  \"startDate\": \"2020-02-13T10:30:00\",\n"
            + "  \"endDate\": \"2020-02-13T12:30:00\"\n"
            + "}");
        jsonAllEvents = new StringBuilder("[\n"
            + "  {\n"
            + "    \"id\": \"eventId\",\n"
            + "    \"summary\":\"My summary\",\n"
            + "    \"location\": \"My location\",\n"
            + "    \"description\": \"My description\",\n"
            + "    \"color\": \"5\",\n"
            + "    \"emailReminderMinutes\": 80,\n"
            + "    \"repetitionInterval\": 14,\n"
            + "    \"startDate\": \"2020-02-13T10:30:00\",\n"
            + "    \"endDate\": \"2020-02-13T12:30:00\"\n"
            + "  "
            + "}"
            + "   \n"
            + "]");
    }

    @Test
    public void createSecondaryCalendar() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createSecondaryCalendar(ACCESS_TOKEN, owner, petName);
        assertEquals(SHOULD_RETURN_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteSecondaryCalendar() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteSecondaryCalendar(ACCESS_TOKEN, owner, petName);
        assertEquals(SHOULD_RETURN_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getAllEventsFromCalendar() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllEvents);

        List<EventData> response = client.getAllEventsFromCalendar(ACCESS_TOKEN, owner, petName);
        assertEquals("Should return Event list", eventDataList, response);
    }

    @Test
    public void createEvent() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createEvent(ACCESS_TOKEN, owner, petName, eventData);
        assertEquals(SHOULD_RETURN_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getEvent() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonEventData);

        EventData response = client.getEvent(ACCESS_TOKEN, owner, petName, eventId);
        assertEquals("Should return Event", eventData, response);

    }

    @Test
    public void updateEvent() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.updateEvent(ACCESS_TOKEN, owner, petName, eventData);
        assertEquals(SHOULD_RETURN_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteEvent() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteEvent(ACCESS_TOKEN, owner, petName, eventId);
        assertEquals(SHOULD_RETURN_200, STATUS_OK_INT, responseCode);
    }
}
