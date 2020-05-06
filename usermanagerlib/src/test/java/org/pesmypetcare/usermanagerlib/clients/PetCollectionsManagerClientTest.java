package org.pesmypetcare.usermanagerlib.clients;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.Exercise;
import org.pesmypetcare.usermanagerlib.datacontainers.ExerciseData;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.datacontainers.Wash;
import org.pesmypetcare.usermanagerlib.datacontainers.WashData;
import org.pesmypetcare.usermanagerlib.datacontainers.Weight;
import org.pesmypetcare.usermanagerlib.datacontainers.WeightData;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * @author Marc Simó
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class PetCollectionsManagerClientTest {
    private static final String ACCESS_TOKEN = "my-token";
    private static final String USERNAME = "user";
    private static final String PET_NAME = "Gustavo";
    private static final String DATE_1 = "1990-01-08T15:20:30";
    private static final String DATE_2 = "1995-01-08T15:20:30";
    private static final String DATE_3 = "1998-01-08T15:20:30";
    private StringBuilder mealCollectionJson;
    private StringBuilder mealDataJson;
    private List<Meal> mealCollectionList;
    private MealData mealData;
    private StringBuilder valueCollectionJson;
    private StringBuilder valueDataJson;
    private List<Exercise> exerciseCollectionList;
    private ExerciseData exerciseData;
    private List<Wash> washCollectionList;
    private WashData washData;
    private List<Weight> weightCollectionList;
    private WeightData weightData;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private PetCollectionsManagerClient client = new PetCollectionsManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        mealCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"kcal\": 85.44,\n"
            + "      \"mealName\": \"Tortilla\"\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  }\n"
            + "  ,{\n"
            + "    \"body\": {\n"
            + "      \"kcal\": 85.44,\n"
            + "      \"mealName\": \"Tortilla\"\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  }\n"
            + "  ,{\n"
            + "    \"body\": {\n"
            + "      \"kcal\": 85.44,\n"
            + "      \"mealName\": \"Tortilla\"\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        mealDataJson = new StringBuilder("{\n"
            + "  \"kcal\": 85.44,\n"
            + "  \"mealName\": \"Tortilla\"\n"
            + "}"
        );
        mealData = new MealData("Tortilla", 85.44);
        mealCollectionList = new ArrayList<>();
        mealCollectionList.add(new Meal(DATE_1, mealData));
        mealCollectionList.add(new Meal(DATE_2, mealData));
        mealCollectionList.add(new Meal(DATE_3, mealData));
        valueCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"value\": 54\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  }\n"
            + "  ,{\n"
            + "    \"body\": {\n"
            + "      \"value\": 54\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  }\n"
            + "  ,{\n"
            + "    \"body\": {\n"
            + "      \"value\": 54\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        valueDataJson = new StringBuilder("{\n"
            + " \"value\": 54\n"
            + "}"
        );
        exerciseData = new ExerciseData(54);
        exerciseCollectionList = new ArrayList<>();
        exerciseCollectionList.add(new Exercise(DATE_1, exerciseData));
        exerciseCollectionList.add(new Exercise(DATE_2, exerciseData));
        exerciseCollectionList.add(new Exercise(DATE_3, exerciseData));
        washData = new WashData(54);
        washCollectionList = new ArrayList<>();
        washCollectionList.add(new Wash(DATE_1, washData));
        washCollectionList.add(new Wash(DATE_2, washData));
        washCollectionList.add(new Wash(DATE_3, washData));
        weightData = new WeightData(54);
        weightCollectionList = new ArrayList<>();
        weightCollectionList.add(new Weight(DATE_1, weightData));
        weightCollectionList.add(new Weight(DATE_2, weightData));
        weightCollectionList.add(new Weight(DATE_3, weightData));
    }

    @Test
    public void getAllMeals() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(mealCollectionJson);
        List<Meal> response = client.getAllMeals(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", mealCollectionList, response);
    }

    @Test
    public void getMealsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(mealCollectionJson);
        List<Meal> response = client.getMealsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", mealCollectionList, response);
    }

    @Test
    public void getMeal() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(mealDataJson);
        MealData response = client.getMeal(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", mealData, response);
    }

    @Test
    public void getAllExercises() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Exercise> response = client.getAllExercises(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", exerciseCollectionList, response);
    }

    @Test
    public void getExercisesBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Exercise> response = client.getExercisesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", exerciseCollectionList, response);
    }

    @Test
    public void getExercises() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueDataJson);
        ExerciseData response = client.getExercise(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", exerciseData, response);
    }

    @Test
    public void getAllWashes() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Wash> response = client.getAllWashes(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", washCollectionList, response);
    }

    @Test
    public void getWashesBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Wash> response = client.getWashesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", washCollectionList, response);
    }

    @Test
    public void getWashes() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueDataJson);
        WashData response = client.getWash(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", washData, response);
    }

    @Test
    public void getAllWeights() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Weight> response = client.getAllWeights(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", weightCollectionList, response);
    }

    @Test
    public void getWeightsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Weight> response = client.getWeightsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", weightCollectionList, response);
    }

    @Test
    public void getWeights() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueDataJson);
        WeightData response = client.getWeight(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", weightData, response);
    }
}
