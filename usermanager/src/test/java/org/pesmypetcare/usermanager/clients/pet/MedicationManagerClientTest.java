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
import org.pesmypetcare.usermanager.datacontainers.pet.Medication;
import org.pesmypetcare.usermanager.datacontainers.pet.MedicationData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class MedicationManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;
    private static final String SHOULD_BE_200 = "Should return response code 200";
    private static final String SHOULD_RETURN_MED_LIST = "Should return medication data list";

    private String owner;
    private String petName;
    private String name;
    private MedicationData medicationData;
    private Medication medication;
    private List<Medication> medicationList;
    private DateTime date;
    private DateTime date2;
    private String field;
    private Double value;
    private StringBuilder jsonMedicationData;
    private StringBuilder jsonAllMedications;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private MedicationManagerClient client = new MedicationManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        owner = "Manolo";
        petName = "Kawaguchi";
        name = "Ibuprofeno";
        date = DateTime.Builder.buildFullString("2020-02-13T10:30:00");
        date2 = DateTime.Builder.buildFullString("2021-02-13T10:30:00");
        field = "quantity";
        value = 60.0;
        medicationData = new MedicationData(0.0, 2, 3);
        medication = new Medication(date.toString(), name, medicationData);
        medicationList = new ArrayList<>();
        medicationList.add(medication);
        jsonMedicationData = new StringBuilder("{\n"
                + "      \"quantity\": \"0.0\",\n"
                + "      \"duration\": \"2\",\n"
                + "      \"periodicity\": \"3\" \n"
                + "    }\n");
        jsonAllMedications = new StringBuilder("[\n"
                + "{\n"
                + "    \"date\": \"2020-02-13T10:30:00\",\n"
                + "    \"name\" : \"Ibuprofeno\", \n"
                + "    \"body\": {\n"
                + "      \"quantity\": \"0.0\",\n"
                + "      \"duration\": \"2\",\n"
                + "      \"periodicity\": \"3\" \n"
                + "    }\n"
                + "  }"
                + "   \n"
                + "]");
    }

    @Test
    public void createMedication() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createMedication(ACCESS_TOKEN, owner, petName, medication);
        assertEquals(SHOULD_BE_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteByDateName() throws ExecutionException, InterruptedException {

        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteByDateName(ACCESS_TOKEN, owner, petName, date, name);
        assertEquals(SHOULD_BE_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void deleteAllMedications() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllMedications(ACCESS_TOKEN, owner, petName);
        assertEquals(SHOULD_BE_200, STATUS_OK_INT, responseCode);
    }

    @Test
    public void getMedicationData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonMedicationData);

        MedicationData response = client.getMedicationData(ACCESS_TOKEN, owner, petName, date, name);
        assertEquals("Should return Medication data", response, medicationData);
    }

    @Test
    public void getAllMedicationData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllMedications);

        List<Medication> response = client.getAllMedicationData(ACCESS_TOKEN, owner, petName);
        assertEquals(SHOULD_RETURN_MED_LIST, response, medicationList);
    }

    @Test
    public void getAllMedicationsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllMedications);

        List<Medication> response = client.getAllMedicationsBetween(ACCESS_TOKEN, owner, petName, date, date2);
        assertEquals(SHOULD_RETURN_MED_LIST, response, medicationList);
    }

    @Test
    public void updateMedicationField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateMedicationField(ACCESS_TOKEN, owner, petName, date, name, field, value);
        assertEquals(SHOULD_BE_200, STATUS_OK_INT, responseCode);
    }
}
