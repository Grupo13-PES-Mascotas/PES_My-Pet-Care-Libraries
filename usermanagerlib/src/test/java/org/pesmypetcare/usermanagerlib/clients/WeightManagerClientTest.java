package org.pesmypetcare.usermanagerlib.clients;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Weight;
import org.pesmypetcare.usermanagerlib.datacontainers.WeightData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class WeightManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;

    private String owner;
    private String petName;
    private WeightData weightData;
    private Weight weight;
    private List<Weight> weightList;
    private DateTime date;
    private DateTime date2;
    private String field;
    private Double value;
    private StringBuilder jsonWeightData;
    private StringBuilder jsonAllWeights;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private WeightManagerClient client = new WeightManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        date = new DateTime("2020-02-13T10:30:00");
        date2 = new DateTime("2021-02-13T10:30:00");
        field = "kcal";
        value = 60.0;
        weightData = new WeightData(5.4);
        weight = new Weight(weightData);
        weightList = new ArrayList<>();
        weightList.add(weight);
        jsonWeightData = new StringBuilder("{\n"
            + "      \"weight\": 5.4\n"
            + "    }\n");
        jsonAllWeights = new StringBuilder("[\n"
            + "{\n"
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + "      \"weight\": 5.4\n"
            + "    }\n"
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createWeight() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createWeight(ACCESS_TOKEN, owner, petName, weight, date);
        assertEquals("Should return response code 200", STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteByDate() throws ExecutionException, InterruptedException {

        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteByDate(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return response code 200", STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteAllWeights() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllWeight(ACCESS_TOKEN, owner, petName);
        assertEquals("Should return response code 200", STATUS_OK_INT, responseCode);
    }

    @Test
    public void getWeightData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonWeightData);

        WeightData response = client.getWeightData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return meal data", response, weightData);
    }

    @Test
    public void getAllMealData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllWeights);

        List<Weight> response = client.getAllWeightData(ACCESS_TOKEN, owner, petName);
        assertEquals("Should return a meal data list", response, weightList);
    }

    @Test
    public void getAllMealsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllWeights);

        List<Weight> response = client.getAllWeightsBetween(ACCESS_TOKEN, owner, petName, date, date2);
        assertEquals("Should return a meal data list", response, weightList);
    }

    @Test
    public void updateMealField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateMealField(ACCESS_TOKEN, owner, petName, date, value);
        assertEquals("Should return response code 200", STATUS_OK_INT, responseCode);
    }
}
