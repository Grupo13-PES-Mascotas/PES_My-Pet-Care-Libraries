package org.pesmypetcare.usermanagerlib.clients;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class MealManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final int STATUS_OK_INT = 200;

    private String owner;
    private String petName;
    private MealData mealData;
    private Meal meal;
    private List<Meal> mealList;
    private DateTime date;
    private DateTime date2;
    private String field;
    private Double value;
    private StringBuilder jsonMealData;
    private StringBuilder jsonAllMeals;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private MealManagerClient client = new MealManagerClient();

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
        mealData = new MealData("Ultra Asparagus", 54);
        meal = new Meal(date.toString(), mealData);
        mealList = new ArrayList<>();
        mealList.add(meal);
        jsonMealData = new StringBuilder("{\n"
            + "      \"mealName\": \"Ultra Asparagus\",\n"
            + "      \"kcal\": 54\n"
            + "    }\n");
        jsonAllMeals = new StringBuilder("[\n"
            + "{\n"
            + "    \"date\": \"2020-02-13T10:30:00\",\n"
            + "    \"body\": {\n"
            + "      \"mealName\": \"Ultra Asparagus\",\n"
            + "      \"kcal\": 54\n"
            + "    }\n"
            + "  }"
            + "   \n"
            + "]");
    }

    @Test
    public void createMeal() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.createMeal(ACCESS_TOKEN, owner, petName, meal);
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
    public void deleteAllMeals() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);

        int responseCode = client.deleteAllMeals(ACCESS_TOKEN, owner, petName);
        assertEquals("Should return response code 200", STATUS_OK_INT, responseCode);
    }

    @Test
    public void getMealData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonMealData);

        MealData response = client.getMealData(ACCESS_TOKEN, owner, petName, date);
        assertEquals("Should return meal data", response, mealData);
    }

    @Test
    public void getAllMealData() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllMeals);

        List<Meal> response = client.getAllMealData(ACCESS_TOKEN, owner, petName);
        assertEquals("Should return a meal data list", response, mealList);
    }

    @Test
    public void getAllMealsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllMeals);

        List<Meal> response = client.getAllMealsBetween(ACCESS_TOKEN, owner, petName, date, date2);
        assertEquals("Should return a meal data list", response, mealList);
    }

    @Test
    public void updateMealField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateMealField(ACCESS_TOKEN, owner, petName, date, field, value);
        assertEquals("Should return response code 200", STATUS_OK_INT, responseCode);
    }
}
