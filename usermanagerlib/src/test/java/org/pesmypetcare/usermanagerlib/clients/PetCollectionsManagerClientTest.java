package org.pesmypetcare.usermanagerlib.clients;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.datacontainers.Training;
import org.pesmypetcare.usermanagerlib.datacontainers.TrainingData;
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
    private static final String DATE_2 = "1998-01-08T15:20:30";
    private StringBuilder mealCollectionJson;
    private StringBuilder mealDataJson;
    private List<Meal> mealCollectionList;
    private MealData mealData;
    private StringBuilder valueCollectionJson;
    private StringBuilder valueDataJson;
    private List<Training> trainingCollectionList;
    private TrainingData trainingData;
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
        mealCollectionList.add(new Meal("1990-01-08T15:20:30", mealData));
        mealCollectionList.add(new Meal("1995-01-08T15:20:30", mealData));
        mealCollectionList.add(new Meal("1998-01-08T15:20:30", mealData));
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
        trainingData = new TrainingData(54);
        trainingCollectionList = new ArrayList<>();
        trainingCollectionList.add(new Training("1990-01-08T15:20:30", trainingData));
        trainingCollectionList.add(new Training("1995-01-08T15:20:30", trainingData));
        trainingCollectionList.add(new Training("1998-01-08T15:20:30", trainingData));
        washData = new WashData(54);
        washCollectionList = new ArrayList<>();
        washCollectionList.add(new Wash("1990-01-08T15:20:30", washData));
        washCollectionList.add(new Wash("1995-01-08T15:20:30", washData));
        washCollectionList.add(new Wash("1998-01-08T15:20:30", washData));
        weightData = new WeightData(54);
        weightCollectionList = new ArrayList<>();
        weightCollectionList.add(new Weight("1990-01-08T15:20:30", weightData));
        weightCollectionList.add(new Weight("1995-01-08T15:20:30", weightData));
        weightCollectionList.add(new Weight("1998-01-08T15:20:30", weightData));
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
        List<Meal> response = client.getMealsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_2);
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
    public void getAllTrainings() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Training> response = client.getAllTrainings(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", trainingCollectionList, response);
    }

    @Test
    public void getTrainingsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueCollectionJson);
        List<Training> response = client.getTrainingsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_2);
        assertEquals("Should return a list", trainingCollectionList, response);
    }

    @Test
    public void getTrainings() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(valueDataJson);
        TrainingData response = client.getTraining(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", trainingData, response);
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
        List<Wash> response = client.getWashesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_2);
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
        List<Weight> response = client.getWeightsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_2);
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
