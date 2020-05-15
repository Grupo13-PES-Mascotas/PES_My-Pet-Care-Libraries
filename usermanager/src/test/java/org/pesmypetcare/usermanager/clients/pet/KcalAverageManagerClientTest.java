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
import org.pesmypetcare.usermanager.datacontainers.pet.KcalAverage;
import org.pesmypetcare.usermanager.datacontainers.pet.KcalAverageData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class KcalAverageManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String LINE_JUMP = "{\n";
    private static final String LINE_JUMP2 = "    }\n";
    private static final String KCALAVERAGE_VALUE = "      \"kcalAverage\": 2.0\n";
    private static final String CODE_OK = "Should return response code 200";
    private static final String CODE_RETURN = "Should return a kcalAverage data list";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String owner;
    private String petName;
    private KcalAverageData kcalAverageData;
    private KcalAverage kcalAverage;
    private List<KcalAverage> kcalAverageList;
    private DateTime date;
    private DateTime date2;
    private Double value;
    private StringBuilder jsonKcalAverageData;
    private StringBuilder jsonAllKcalAverages;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private KcalAverageManagerClient client = new KcalAverageManagerClient();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        date = DateTime.Builder.buildFullString("2020-02-13T10:30:00");
        date2 = DateTime.Builder.buildFullString("2021-02-13T10:30:00");
        value = 1.0;
        kcalAverageData = new KcalAverageData(2.0);
        kcalAverage = new KcalAverage(kcalAverageData);
        kcalAverageList = new ArrayList<>();
        kcalAverageList.add(kcalAverage);
        jsonKcalAverageData = new StringBuilder(LINE_JUMP
            + KCALAVERAGE_VALUE
            + LINE_JUMP2);
        jsonAllKcalAverages = new StringBuilder("[\n"
            + LINE_JUMP
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + KCALAVERAGE_VALUE
            + LINE_JUMP2
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createKcalAverage() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createKcalAverage(ACCESS_TOKEN, owner, petName, kcalAverage,
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
    public void deleteAllKcalAverages() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllKcalAverage(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getKcalAverageData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonKcalAverageData);

        KcalAverageData response = client.getKcalAverageData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return kcalAverage data", response, kcalAverageData);
    }

    @Test
    public void getAllKcalAverageData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllKcalAverages);

        List<KcalAverage> response = client.getAllKcalAverageData(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_RETURN, response, kcalAverageList);
    }

    @Test
    public void getAllKcalAveragesBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllKcalAverages);

        List<KcalAverage> response = client.getAllKcalAveragesBetween(ACCESS_TOKEN, owner, petName,
                date, date2);
        assertEquals(CODE_RETURN, response, kcalAverageList);
    }

    @Test
    public void updateKcalAverageField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateKcalAverageField(ACCESS_TOKEN, owner, petName, date, value);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }
}
