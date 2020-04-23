package org.pesmypetcare.usermanagerlib.clients.pet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.clients.TaskManager;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.pet.Kcal;
import org.pesmypetcare.usermanagerlib.datacontainers.pet.KcalData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class KcalManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String LINE_JUMP = "{\n";
    private static final String LINE_JUMP2 = "    }\n";
    private static final String KCAL_VALUE = "      \"kcal\": 2.0\n";
    private static final String CODE_OK = "Should return response code 200";
    private static final String CODE_RETURN = "Should return a kcal data list";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String owner;
    private String petName;
    private KcalData kcalData;
    private Kcal kcal;
    private List<Kcal> kcalList;
    private DateTime date;
    private DateTime date2;
    private Double value;
    private StringBuilder jsonKcalData;
    private StringBuilder jsonAllKcals;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private KcalManagerClient client = new KcalManagerClient();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        date = DateTime.Builder.buildFullString("2020-02-13T10:30:00");
        date2 = DateTime.Builder.buildFullString("2021-02-13T10:30:00");
        value = 1.0;
        kcalData = new KcalData(2.0);
        kcal = new Kcal(kcalData);
        kcalList = new ArrayList<>();
        kcalList.add(kcal);
        jsonKcalData = new StringBuilder(LINE_JUMP
            + KCAL_VALUE
            + LINE_JUMP2);
        jsonAllKcals = new StringBuilder("[\n"
            + LINE_JUMP
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + KCAL_VALUE
            + LINE_JUMP2
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createKcal() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createKcal(ACCESS_TOKEN, owner, petName, kcal, date);
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
    public void deleteAllKcals() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllKcal(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getKcalData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonKcalData);

        KcalData response = client.getKcalData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return kcal data", response, kcalData);
    }

    @Test
    public void getAllKcalData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllKcals);

        List<Kcal> response = client.getAllKcalData(ACCESS_TOKEN, owner, petName);
        assertEquals(CODE_RETURN, response, kcalList);
    }

    @Test
    public void getAllKcalsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllKcals);

        List<Kcal> response = client.getAllKcalsBetween(ACCESS_TOKEN, owner, petName, date, date2);
        assertEquals(CODE_RETURN, response, kcalList);
    }

    @Test
    public void updateKcalField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateKcalField(ACCESS_TOKEN, owner, petName, date, value);
        assertEquals(CODE_OK, STATUS_OK_INT, responseCode);
    }
}
