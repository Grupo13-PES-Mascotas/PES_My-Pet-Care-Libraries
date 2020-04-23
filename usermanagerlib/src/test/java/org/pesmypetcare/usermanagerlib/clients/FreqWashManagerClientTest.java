package org.pesmypetcare.usermanagerlib.clients;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.FreqWash;
import org.pesmypetcare.usermanagerlib.datacontainers.FreqWashData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class FreqWashManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String LINE_JUMP = "{\n";
    private static final String LINE_JUMP2 = "    }\n";
    private static final String FREQWASH_VALUE = "      \"freqWash\": 2.0\n";
    private static final String CODE_OK = "Should return response code 200";
    private static final String CODE_RETURN = "Should return a freqWash data list";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String owner;
    private String petName;
    private FreqWashData freqWashData;
    private FreqWash freqWash;
    private List<FreqWash> freqWashList;
    private DateTime date;
    private DateTime date2;
    private Double value;
    private StringBuilder jsonFreqWashData;
    private StringBuilder jsonAllFreqWashs;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private FreqWashManagerClient client = new FreqWashManagerClient();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        date = new DateTime("2020-02-13T10:30:00");
        date2 = new DateTime("2021-02-13T10:30:00");
        value = 1.0;
        freqWashData = new FreqWashData(2.0);
        freqWash = new FreqWash(freqWashData);
        freqWashList = new ArrayList<>();
        freqWashList.add(freqWash);
        jsonFreqWashData = new StringBuilder(LINE_JUMP
            + FREQWASH_VALUE
            + LINE_JUMP2);
        jsonAllFreqWashs = new StringBuilder("[\n"
            + LINE_JUMP
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + FREQWASH_VALUE
            + LINE_JUMP2
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createFreqWash() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createFreqWash(ACCESS_TOKEN, owner, petName, freqWash, date);
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
    public void deleteAllFreqWashs() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllFreqWash(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getFreqWashData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonFreqWashData);

        FreqWashData response = client.getFreqWashData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return freqWash data", response, freqWashData);
    }

    @Test
    public void getAllFreqWashData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllFreqWashs);

        List<FreqWash> response = client.getAllFreqWashData(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_RETURN, response, freqWashList);
    }

    @Test
    public void getAllFreqWashsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllFreqWashs);

        List<FreqWash> response = client.getAllFreqWashsBetween(ACCESS_TOKEN, owner, petName, date,
                date2);
        assertEquals(CODE_RETURN, response, freqWashList);
    }

    @Test
    public void updateFreqWashField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateFreqWashField(ACCESS_TOKEN, owner, petName, date, value);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }
}
