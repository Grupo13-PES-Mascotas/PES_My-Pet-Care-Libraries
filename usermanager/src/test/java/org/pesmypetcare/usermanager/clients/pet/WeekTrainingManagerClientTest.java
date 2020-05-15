package org.pesmypetcare.usermanager.clients.pet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.pet.WeekTraining;
import org.pesmypetcare.usermanager.datacontainers.pet.WeekTrainingData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class WeekTrainingManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String LINE_JUMP = "{\n";
    private static final String LINE_JUMP2 = "    }\n";
    private static final String WEEKTRAINING_VALUE = "      \"weekTraining\": 2.0\n";
    private static final String CODE_OK = "Should return response code 200";
    private static final String CODE_RETURN = "Should return a weekTraining data list";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String owner;
    private String petName;
    private WeekTrainingData weekTrainingData;
    private WeekTraining weekTraining;
    private List<WeekTraining> weekTrainingList;
    private DateTime date;
    private DateTime date2;
    private Double value;
    private StringBuilder jsonWeekTrainingData;
    private StringBuilder jsonAllWeekTrainings;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private WeekTrainingManagerClient client = new WeekTrainingManagerClient();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        date = DateTime.Builder.buildFullString("2020-02-13T10:30:00");
        date2 = DateTime.Builder.buildFullString("2021-02-13T10:30:00");
        value = 1.0;
        weekTrainingData = new WeekTrainingData(2.0);
        weekTraining = new WeekTraining(weekTrainingData);
        weekTrainingList = new ArrayList<>();
        weekTrainingList.add(weekTraining);
        jsonWeekTrainingData = new StringBuilder(LINE_JUMP
            + WEEKTRAINING_VALUE
            + LINE_JUMP2);
        jsonAllWeekTrainings = new StringBuilder("[\n"
            + LINE_JUMP
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + WEEKTRAINING_VALUE
            + LINE_JUMP2
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createWeekTraining() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createWeekTraining(ACCESS_TOKEN, owner, petName, weekTraining,
                date);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteByDate() throws ExecutionException, InterruptedException {

        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteByDate(ACCESS_TOKEN, owner, petName, date);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteAllWeekTrainings() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllWeekTraining(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getWeekTrainingData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonWeekTrainingData);

        WeekTrainingData response = client.getWeekTrainingData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return weekTraining data", response, weekTrainingData);
    }

    @Test
    public void getAllWeekTrainingData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllWeekTrainings);

        List<WeekTraining> response = client.getAllWeekTrainingData(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_RETURN, response, weekTrainingList);
    }

    @Test
    public void getAllWeekTrainingsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllWeekTrainings);

        List<WeekTraining> response = client.getAllWeekTrainingsBetween(ACCESS_TOKEN, owner,
                petName, date, date2);
        assertEquals(CODE_RETURN, response, weekTrainingList);
    }

    @Test
    public void updateWeekTrainingField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateWeekTrainingField(ACCESS_TOKEN, owner, petName, date,
                value);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }
}
