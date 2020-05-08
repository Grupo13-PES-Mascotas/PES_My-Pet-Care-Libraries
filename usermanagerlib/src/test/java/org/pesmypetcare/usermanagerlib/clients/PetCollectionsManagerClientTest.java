package org.pesmypetcare.usermanagerlib.clients;

import com.google.android.gms.maps.model.LatLng;

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
import org.pesmypetcare.usermanagerlib.datacontainers.Illness;
import org.pesmypetcare.usermanagerlib.datacontainers.IllnessData;
import org.pesmypetcare.usermanagerlib.datacontainers.IllnessType;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;
import org.pesmypetcare.usermanagerlib.datacontainers.Medication;
import org.pesmypetcare.usermanagerlib.datacontainers.MedicationData;
import org.pesmypetcare.usermanagerlib.datacontainers.SeverityType;
import org.pesmypetcare.usermanagerlib.datacontainers.Vaccination;
import org.pesmypetcare.usermanagerlib.datacontainers.VaccinationData;
import org.pesmypetcare.usermanagerlib.datacontainers.VetVisit;
import org.pesmypetcare.usermanagerlib.datacontainers.VetVisitData;
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
    private StringBuilder exerciseCollectionJson;
    private StringBuilder exerciseDataJson;
    private List<Exercise> exerciseCollectionList;
    private ExerciseData exerciseData;
    private StringBuilder illnessCollectionJson;
    private StringBuilder illnessDataJson;
    private List<Illness> illnessCollectionList;
    private IllnessData illnessData;
    private StringBuilder mealCollectionJson;
    private StringBuilder mealDataJson;
    private List<Meal> mealCollectionList;
    private MealData mealData;
    private StringBuilder medicationCollectionJson;
    private StringBuilder medicationDataJson;
    private List<Medication> medicationCollectionList;
    private MedicationData medicationData;
    private StringBuilder vaccinationCollectionJson;
    private StringBuilder vaccinationDataJson;
    private List<Vaccination> vaccinationCollectionList;
    private VaccinationData vaccinationData;
    private StringBuilder vetVisitCollectionJson;
    private StringBuilder vetVisitDataJson;
    private List<VetVisit> vetVisitCollectionList;
    private VetVisitData vetVisitData;
    private StringBuilder washCollectionJson;
    private StringBuilder washDataJson;
    private List<Wash> washCollectionList;
    private WashData washData;
    private StringBuilder weightCollectionJson;
    private StringBuilder weightDataJson;
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
        // Corregir exercise per ficar coordinates
        exerciseCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\":{\n"
            + "      \"name\":\"Planking\",\n"
            + "      \"coordinates\":[\n"
            + "        {\n"
            + "          \"latitude\":84,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":34,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":21,\n"
            + "          \"longitude\":58\n"
            + "        }\n"
            + "      ],\n"
            + "      \"description\":\"Staying horizontal over a plain surface\",\n"
            + "      \"endDateTime\":\"2000-01-08T15:20:30\"\n"
            + "    },\n"
            + "    \"key\":\"1990-01-08T15:20:30\"\n"
            + "  },{\"body\":{\n"
            + "      \"name\":\"Planking\",\n"
            + "      \"coordinates\":[\n"
            + "        {\n"
            + "          \"latitude\":84,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":34,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":21,\n"
            + "          \"longitude\":58\n"
            + "        }\n"
            + "      ],\n"
            + "      \"description\":\"Staying horizontal over a plain surface\",\n"
            + "      \"endDateTime\":\"2000-01-08T15:20:30\"\n"
            + "    },\n"
            + "    \"key\":\"1995-01-08T15:20:30\"\n"
            + "  },{\"body\":{\n"
            + "      \"name\":\"Planking\",\n"
            + "      \"coordinates\":[\n"
            + "        {\n"
            + "          \"latitude\":84,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":34,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":21,\n"
            + "          \"longitude\":58\n"
            + "        }\n"
            + "      ],\n"
            + "      \"description\":\"Staying horizontal over a plain surface\",\n"
            + "      \"endDateTime\":\"2000-01-08T15:20:30\"\n"
            + "    },\n"
            + "    \"key\":\"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        exerciseDataJson = new StringBuilder("{\n"
            + "      \"name\":\"Planking\",\n"
            + "      \"coordinates\":[\n"
            + "        {\n"
            + "          \"latitude\":84,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":34,\n"
            + "          \"longitude\":58\n"
            + "        },{\n"
            + "          \"latitude\":21,\n"
            + "          \"longitude\":58\n"
            + "        }\n"
            + "      ],\n"
            + "      \"description\":\"Staying horizontal over a plain surface\",\n"
            + "      \"endDateTime\":\"2000-01-08T15:20:30\"\n"
            + "}"
        );
        List<LatLng> coordinates = new ArrayList<>();
        coordinates.add(new LatLng(84, 58));
        coordinates.add(new LatLng(34, 58));
        coordinates.add(new LatLng(21, 58));
        exerciseData = new ExerciseData("Planking", "Staying horizontal over a plain surface",
            "2000-01-08T15:20:30", coordinates);
        exerciseCollectionList = new ArrayList<>();
        exerciseCollectionList.add(new Exercise(DATE_1, exerciseData));
        exerciseCollectionList.add(new Exercise(DATE_2, exerciseData));
        exerciseCollectionList.add(new Exercise(DATE_3, exerciseData));

        illnessCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"endDateTime\": \"2000-01-08T15:20:30\",\n"
            + "      \"description\": \"I'm not feeling so well\",\n"
            + "      \"type\": \"Normal\",\n"
            + "      \"severity\": \"High\"\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"endDateTime\": \"2000-01-08T15:20:30\",\n"
            + "      \"description\": \"I'm not feeling so well\",\n"
            + "      \"type\": \"Normal\",\n"
            + "      \"severity\": \"High\"\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"endDateTime\": \"2000-01-08T15:20:30\",\n"
            + "      \"description\": \"I'm not feeling so well\",\n"
            + "      \"type\": \"Normal\",\n"
            + "      \"severity\": \"High\"\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        illnessDataJson = new StringBuilder("{\n"
            + "      \"endDateTime\": \"2000-01-08T15:20:30\",\n"
            + "      \"description\": \"I'm not feeling so well\",\n"
            + "      \"type\": \"Normal\",\n"
            + "      \"severity\": \"High\"\n"
            + "}"
        );
        illnessData = new IllnessData("2000-01-08T15:20:30", "I'm not feeling so well",
            IllnessType.Normal, SeverityType.High);
        illnessCollectionList = new ArrayList<>();
        illnessCollectionList.add(new Illness(DATE_1, illnessData));
        illnessCollectionList.add(new Illness(DATE_2, illnessData));
        illnessCollectionList.add(new Illness(DATE_3, illnessData));

        mealCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"kcal\": 85.44,\n"
            + "      \"mealName\": \"Tortilla\"\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"kcal\": 85.44,\n"
            + "      \"mealName\": \"Tortilla\"\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  },{\n"
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

        medicationCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"quantity\": 85.0,\n"
            + "      \"duration\": 45,\n"
            + "      \"periodicity\": 36\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30-Cloroform85\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"quantity\": 85.0,\n"
            + "      \"duration\": 45,\n"
            + "      \"periodicity\": 36\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30-Cloroform85\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"quantity\": 85.0,\n"
            + "      \"duration\": 45,\n"
            + "      \"periodicity\": 36\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30-Cloroform85\"\n"
            + "  }\n"
            + "]");
        medicationDataJson = new StringBuilder("{\n"
            + "      \"quantity\": 85.0,\n"
            + "      \"duration\": 45,\n"
            + "      \"periodicity\": 36\n"
            + "}"
        );
        medicationData = new MedicationData(85.0, 45, 36);
        medicationCollectionList = new ArrayList<>();
        medicationCollectionList.add(new Medication(DATE_1, "Cloroform85", medicationData));
        medicationCollectionList.add(new Medication(DATE_2, "Cloroform85", medicationData));
        medicationCollectionList.add(new Medication(DATE_3, "Cloroform85", medicationData));

        vaccinationCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"description\": \"It was really pleasant\"\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"description\": \"It was really pleasant\"\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"description\": \"It was really pleasant\"\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        vaccinationDataJson = new StringBuilder("{\n"
            + "      \"description\": \"It was really pleasant\"\n"
            + "}"
        );
        vaccinationData = new VaccinationData("It was really pleasant");
        vaccinationCollectionList = new ArrayList<>();
        vaccinationCollectionList.add(new Vaccination(DATE_1, vaccinationData));
        vaccinationCollectionList.add(new Vaccination(DATE_2, vaccinationData));
        vaccinationCollectionList.add(new Vaccination(DATE_3, vaccinationData));

        vetVisitCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"reason\": \"It was really pleasant\",\n"
            + "      \"address\": \"Calle 2, pt. Avenida\"\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"reason\": \"It was really pleasant\",\n"
            + "      \"address\": \"Calle 2, pt. Avenida\"\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"reason\": \"It was really pleasant\",\n"
            + "      \"address\": \"Calle 2, pt. Avenida\"\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        vetVisitDataJson = new StringBuilder("{\n"
            + "      \"reason\": \"It was really pleasant\",\n"
            + "      \"address\": \"Calle 2, pt. Avenida\"\n"
            + "}"
        );
        vetVisitData = new VetVisitData("It was really pleasant", "Calle 2, pt. Avenida");
        vetVisitCollectionList = new ArrayList<>();
        vetVisitCollectionList.add(new VetVisit(DATE_1, vetVisitData));
        vetVisitCollectionList.add(new VetVisit(DATE_2, vetVisitData));
        vetVisitCollectionList.add(new VetVisit(DATE_3, vetVisitData));

        washCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"description\": \"It smelt so bad\",\n"
            + "      \"duration\": 54\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  },{\n"
            + "  \"body\": {\n"
            + "    \"description\": \"It smelt so bad\",\n"
            + "    \"duration\": 54\n"
            + "  },\n"
            + "  \"key\": \"1995-01-08T15:20:30\"\n"
            + "},{\n"
            + "  \"body\": {\n"
            + "    \"description\": \"It smelt so bad\",\n"
            + "    \"duration\": 54\n"
            + "  },\n"
            + "  \"key\": \"1998-01-08T15:20:30\"\n"
            + "}\n"
            + "]");
        washDataJson = new StringBuilder("{\n"
            + "    \"description\": \"It smelt so bad\",\n"
            + "    \"duration\": 54\n"
            + "}"
        );
        washData = new WashData("It smelt so bad", 54);
        washCollectionList = new ArrayList<>();
        washCollectionList.add(new Wash(DATE_1, washData));
        washCollectionList.add(new Wash(DATE_2, washData));
        washCollectionList.add(new Wash(DATE_3, washData));

        weightCollectionJson = new StringBuilder("[\n"
            + "  {\n"
            + "    \"body\": {\n"
            + "      \"value\": 54\n"
            + "    },\n"
            + "    \"key\": \"1990-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"value\": 54\n"
            + "    },\n"
            + "    \"key\": \"1995-01-08T15:20:30\"\n"
            + "  },{\n"
            + "    \"body\": {\n"
            + "      \"value\": 54\n"
            + "    },\n"
            + "    \"key\": \"1998-01-08T15:20:30\"\n"
            + "  }\n"
            + "]");
        weightDataJson = new StringBuilder("{\n"
            + " \"value\": 54\n"
            + "}"
        );
        weightData = new WeightData(54);
        weightCollectionList = new ArrayList<>();
        weightCollectionList.add(new Weight(DATE_1, weightData));
        weightCollectionList.add(new Weight(DATE_2, weightData));
        weightCollectionList.add(new Weight(DATE_3, weightData));
    }

    @Test
    public void getAllExercises() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(exerciseCollectionJson);
        List<Exercise> response = client.getAllExercises(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", exerciseCollectionList, response);
    }

    @Test
    public void getExercisesBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(exerciseCollectionJson);
        List<Exercise> response = client.getExercisesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", exerciseCollectionList, response);
    }

    @Test
    public void getExercises() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(exerciseDataJson);
        ExerciseData response = client.getExercise(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", exerciseData, response);
    }

    @Test
    public void getAllIllnesses() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(illnessCollectionJson);
        List<Illness> response = client.getAllIllnesses(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", illnessCollectionList, response);
    }

    @Test
    public void getIllnessesBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(illnessCollectionJson);
        List<Illness> response = client.getIllnessesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", illnessCollectionList, response);
    }

    @Test
    public void getIllness() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(illnessDataJson);
        IllnessData response = client.getIllness(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", illnessData, response);
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
    public void getAllMedications() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(medicationCollectionJson);
        List<Medication> response = client.getAllMedications(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", medicationCollectionList, response);
    }

    @Test
    public void getMedicationsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(medicationCollectionJson);
        List<Medication> response = client.getMedicationsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", medicationCollectionList, response);
    }

    @Test
    public void getMedication() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(medicationDataJson);
        MedicationData response = client.getMedication(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", medicationData, response);
    }

    @Test
    public void getAllVaccinations() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(vaccinationCollectionJson);
        List<Vaccination> response = client.getAllVaccinations(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", vaccinationCollectionList, response);
    }

    @Test
    public void getVaccinationsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(vaccinationCollectionJson);
        List<Vaccination> response = client.getVaccinationsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", vaccinationCollectionList, response);
    }

    @Test
    public void getVaccination() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(vaccinationDataJson);
        VaccinationData response = client.getVaccination(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", vaccinationData, response);
    }

    @Test
    public void getAllVetVisits() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(vetVisitCollectionJson);
        List<VetVisit> response = client.getAllVetVisits(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", vetVisitCollectionList, response);
    }

    @Test
    public void getVetVisitsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(vetVisitCollectionJson);
        List<VetVisit> response = client.getVetVisitsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", vetVisitCollectionList, response);
    }

    @Test
    public void getVetVisit() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(vetVisitDataJson);
        VetVisitData response = client.getVetVisit(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", vetVisitData, response);
    }

    @Test
    public void getAllWashes() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(washCollectionJson);
        List<Wash> response = client.getAllWashes(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", washCollectionList, response);
    }

    @Test
    public void getWashesBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(washCollectionJson);
        List<Wash> response = client.getWashesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", washCollectionList, response);
    }

    @Test
    public void getWashes() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(washDataJson);
        WashData response = client.getWash(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", washData, response);
    }

    @Test
    public void getAllWeights() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(weightCollectionJson);
        List<Weight> response = client.getAllWeights(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", weightCollectionList, response);
    }

    @Test
    public void getWeightsBetween() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(weightCollectionJson);
        List<Weight> response = client.getWeightsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", weightCollectionList, response);
    }

    @Test
    public void getWeights() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(weightDataJson);
        WeightData response = client.getWeight(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", weightData, response);
    }
}
