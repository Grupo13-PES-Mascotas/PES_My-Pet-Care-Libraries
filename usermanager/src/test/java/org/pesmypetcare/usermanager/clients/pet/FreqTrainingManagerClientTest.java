package org.pesmypetcare.usermanager.clients.pet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.FreqTraining;
import org.pesmypetcare.usermanager.datacontainers.pet.FreqTrainingData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class FreqTrainingManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String LINE_JUMP = "{\n";
    private static final String LINE_JUMP2 = "    }\n";
    private static final String FREQTRAINING_VALUE = "      \"freqTraining\": 2.0\n";
    private static final String CODE_OK = "Should return response code 200";
    private static final String CODE_RETURN = "Should return a freqTraining data list";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String owner;
    private String petName;
    private FreqTrainingData freqTrainingData;
    private FreqTraining freqTraining;
    private List<FreqTraining> freqTrainingList;
    private DateTime date;
    private DateTime date2;
    private Double value;
    private StringBuilder jsonFreqTrainingData;
    private StringBuilder jsonAllFreqTrainings;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private FreqTrainingManagerClient client = new FreqTrainingManagerClient();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        date = DateTime.Builder.buildFullString("2020-02-13T10:30:00");
        date2 = DateTime.Builder.buildFullString("2021-02-13T10:30:00");
        value = 1.0;
        freqTrainingData = new FreqTrainingData(2.0);
        freqTraining = new FreqTraining(freqTrainingData);
        freqTrainingList = new ArrayList<>();
        freqTrainingList.add(freqTraining);
        jsonFreqTrainingData = new StringBuilder(LINE_JUMP
            + FREQTRAINING_VALUE
            + LINE_JUMP2);
        jsonAllFreqTrainings = new StringBuilder("[\n"
            + LINE_JUMP
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + FREQTRAINING_VALUE
            + LINE_JUMP2
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createFreqTraining() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createFreqTraining(ACCESS_TOKEN, owner, petName, freqTraining,
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
    public void deleteAllFreqTrainings() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllFreqTraining(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getFreqTrainingData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonFreqTrainingData);

        FreqTrainingData response = client.getFreqTrainingData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return freqTraining data", response, freqTrainingData);
    }

    @Test
    public void getAllFreqTrainingData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllFreqTrainings);

        List<FreqTraining> response = client.getAllFreqTrainingData(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_RETURN, response, freqTrainingList);
    }

    @Test
    public void getAllFreqTrainingsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllFreqTrainings);

        List<FreqTraining> response = client.getAllFreqTrainingsBetween(ACCESS_TOKEN, owner,
                petName, date, date2);
        assertEquals(CODE_RETURN, response, freqTrainingList);
    }

    @Test
    public void updateFreqTrainingField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateFreqTrainingField(ACCESS_TOKEN, owner, petName, date,
                value);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }
}
