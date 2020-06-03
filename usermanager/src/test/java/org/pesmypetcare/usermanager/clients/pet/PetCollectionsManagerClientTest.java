package org.pesmypetcare.usermanager.clients.pet;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.httptools.HttpClient;
import org.pesmypetcare.httptools.HttpParameter;
import org.pesmypetcare.httptools.HttpResponse;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.usermanager.BuildConfig;
import org.pesmypetcare.usermanager.datacontainers.pet.Exercise;
import org.pesmypetcare.usermanager.datacontainers.pet.ExerciseData;
import org.pesmypetcare.usermanager.datacontainers.pet.Illness;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessData;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessType;
import org.pesmypetcare.usermanager.datacontainers.pet.Meal;
import org.pesmypetcare.usermanager.datacontainers.pet.MealData;
import org.pesmypetcare.usermanager.datacontainers.pet.Medication;
import org.pesmypetcare.usermanager.datacontainers.pet.MedicationData;
import org.pesmypetcare.usermanager.datacontainers.pet.SeverityType;
import org.pesmypetcare.usermanager.datacontainers.pet.Vaccination;
import org.pesmypetcare.usermanager.datacontainers.pet.VaccinationData;
import org.pesmypetcare.usermanager.datacontainers.pet.VetVisit;
import org.pesmypetcare.usermanager.datacontainers.pet.VetVisitData;
import org.pesmypetcare.usermanager.datacontainers.pet.Wash;
import org.pesmypetcare.usermanager.datacontainers.pet.WashData;
import org.pesmypetcare.usermanager.datacontainers.pet.Weight;
import org.pesmypetcare.usermanager.datacontainers.pet.WeightData;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Marc Simó & Santiago Del Rey
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
    private static final String BASE_URL = BuildConfig.URL;
    private static final String PETS_PATH = "pet/";
    private static final String EXERCISES_PATH = "/collection/exercises";
    private static final String ILLNESSES_PATH = "/collection/illnesses";
    private static final String MEALS_PATH = "/collection/meals";
    private static final String WASHES_PATH = "/collection/washes";
    private static final String WEIGHTS_PATH = "/collection/weights";
    private static final String MEDICATIONS_PATH = "/collection/medications";
    private static final String VACCINATIONS_PATH = "/collection/vaccinations";
    private static final String VET_VISITS_PATH = "/collection/vet_visits";
    private List<Exercise> exerciseCollectionList;
    private ExerciseData exerciseData;
    private List<Illness> illnessCollectionList;
    private IllnessData illnessData;
    private List<Meal> mealCollectionList;
    private MealData mealData;
    private List<Medication> medicationCollectionList;
    private MedicationData medicationData;
    private List<Vaccination> vaccinationCollectionList;
    private VaccinationData vaccinationData;
    private List<VetVisit> vetVisitCollectionList;
    private VetVisitData vetVisitData;
    private List<Wash> washCollectionList;
    private WashData washData;
    private List<Weight> weightCollectionList;
    private WeightData weightData;
    private Map<String, String> headers;
    private String encodedUsername;
    private String encodedPetName;
    private Gson gson;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse httpResponse;

    @InjectMocks
    private PetCollectionsManagerClient client = new PetCollectionsManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private String encodedDate1;
    private String encodedDate3;

    @Before
    public void setUp() {
        gson = new Gson();
        // Corregir exercise per ficar coordinates
        List<LatLng> coordinates = new ArrayList<>();
        coordinates.add(new LatLng(84, 58));
        coordinates.add(new LatLng(34, 58));
        coordinates.add(new LatLng(21, 58));
        exerciseData = new ExerciseData("Planking", "Staying horizontal over a plain surface", "2000-01-08T15:20:30",
                coordinates);
        exerciseCollectionList = new ArrayList<>();
        exerciseCollectionList.add(new Exercise(DATE_1, exerciseData));
        exerciseCollectionList.add(new Exercise(DATE_2, exerciseData));
        exerciseCollectionList.add(new Exercise(DATE_3, exerciseData));

        illnessData = new IllnessData("2000-01-08T15:20:30", "I'm not feeling so well", IllnessType.Normal,
                SeverityType.High);
        illnessCollectionList = new ArrayList<>();
        illnessCollectionList.add(new Illness(DATE_1, illnessData));
        illnessCollectionList.add(new Illness(DATE_2, illnessData));
        illnessCollectionList.add(new Illness(DATE_3, illnessData));

        mealData = new MealData("Tortilla", 85.44);
        mealCollectionList = new ArrayList<>();
        mealCollectionList.add(new Meal(DATE_1, mealData));
        mealCollectionList.add(new Meal(DATE_2, mealData));
        mealCollectionList.add(new Meal(DATE_3, mealData));

        medicationData = new MedicationData(85.0, 45, 36);
        medicationCollectionList = new ArrayList<>();
        medicationCollectionList.add(new Medication(DATE_1, "Cloroform85", medicationData));
        medicationCollectionList.add(new Medication(DATE_2, "Cloroform85", medicationData));
        medicationCollectionList.add(new Medication(DATE_3, "Cloroform85", medicationData));

        vaccinationData = new VaccinationData("It was really pleasant");
        vaccinationCollectionList = new ArrayList<>();
        vaccinationCollectionList.add(new Vaccination(DATE_1, vaccinationData));
        vaccinationCollectionList.add(new Vaccination(DATE_2, vaccinationData));
        vaccinationCollectionList.add(new Vaccination(DATE_3, vaccinationData));

        vetVisitData = new VetVisitData("It was really pleasant", "Calle 2, pt. Avenida");
        vetVisitCollectionList = new ArrayList<>();
        vetVisitCollectionList.add(new VetVisit(DATE_1, vetVisitData));
        vetVisitCollectionList.add(new VetVisit(DATE_2, vetVisitData));
        vetVisitCollectionList.add(new VetVisit(DATE_3, vetVisitData));

        washData = new WashData("It smelt so bad", 54);
        washCollectionList = new ArrayList<>();
        washCollectionList.add(new Wash(DATE_1, washData));
        washCollectionList.add(new Wash(DATE_2, washData));
        washCollectionList.add(new Wash(DATE_3, washData));

        weightData = new WeightData(54);
        weightCollectionList = new ArrayList<>();
        weightCollectionList.add(new Weight(DATE_1, weightData));
        weightCollectionList.add(new Weight(DATE_2, weightData));
        weightCollectionList.add(new Weight(DATE_3, weightData));
        headers = new HashMap<>();
        headers.put("token", ACCESS_TOKEN);
        encodedUsername = HttpParameter.encode(USERNAME);
        encodedPetName = HttpParameter.encode(PET_NAME);
        encodedDate1 = HttpParameter.encode(DATE_1);
        encodedDate3 = HttpParameter.encode(DATE_3);
    }

    @Test
    public void getAllExercises() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + EXERCISES_PATH), isNull(),
                        eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(exerciseCollectionList));

        List<Exercise> response = client.getAllExercises(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", exerciseCollectionList, response);
    }

    @Test
    public void getExercisesBetween() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + EXERCISES_PATH + "/"
                + encodedDate1 + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(exerciseCollectionList));

        List<Exercise> response = client.getExercisesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", exerciseCollectionList, response);
    }

    @Test
    public void getExercises() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + EXERCISES_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(exerciseData));

        ExerciseData response = client.getExercise(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", exerciseData, response);
    }

    @Test
    public void deleteExercisesPreviousToDate() throws MyPetCareException {
        given(httpClient.delete(anyString(), isNull(), anyMap(), isNull())).willReturn(httpResponse);

        client.deleteExercisesPreviousToDate(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        verify(httpClient).delete(eq(
                BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + "/fullcollection/exercises/"
                        + encodedDate1), isNull(), eq(headers), isNull());
    }

    @Test
    public void getAllIllnesses() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + ILLNESSES_PATH), isNull(),
                        eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(illnessCollectionList));

        List<Illness> response = client.getAllIllnesses(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", illnessCollectionList, response);
    }

    @Test
    public void getIllnessesBetween() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + ILLNESSES_PATH + "/"
                + encodedDate1 + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(illnessCollectionList));

        List<Illness> response = client.getIllnessesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", illnessCollectionList, response);
    }

    @Test
    public void getIllness() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + ILLNESSES_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(illnessData));

        IllnessData response = client.getIllness(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", illnessData, response);
    }

    @Test
    public void getAllMeals() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + MEALS_PATH), isNull(),
                eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(mealCollectionList));

        List<Meal> response = client.getAllMeals(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", mealCollectionList, response);
    }

    @Test
    public void getMealsBetween() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + MEALS_PATH + "/" + encodedDate1
                        + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(mealCollectionList));

        List<Meal> response = client.getMealsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", mealCollectionList, response);
    }

    @Test
    public void getMeal() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + MEALS_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(mealData));

        MealData response = client.getMeal(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", mealData, response);
    }

    @Test
    public void getAllMedications() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + MEDICATIONS_PATH), isNull(),
                        eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(medicationCollectionList));

        List<Medication> response = client.getAllMedications(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", medicationCollectionList, response);
    }

    @Test
    public void getMedicationsBetween() throws MyPetCareException {
        DateTime date2 = DateTime.Builder.buildFullString(DATE_3);
        date2.addSecond();
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + MEDICATIONS_PATH + "/"
                + encodedDate1 + "/" + HttpParameter.encode(date2.toString())), isNull(), eq(headers), isNull()))
                .willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(medicationCollectionList));

        List<Medication> response = client.getMedicationsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", medicationCollectionList, response);
    }

    @Test
    public void getMedication() throws MyPetCareException {
        String date = "1990-01-08T15:20:30-Cloroform85";
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + MEDICATIONS_PATH + "/"
                + HttpParameter.encode(date)), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(medicationData));

        MedicationData response = client
                .getMedication(ACCESS_TOKEN, USERNAME, PET_NAME, date);
        assertEquals("Should return the specified element", medicationData, response);
    }

    @Test
    public void getAllVaccinations() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + VACCINATIONS_PATH), isNull(),
                        eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(vaccinationCollectionList));

        List<Vaccination> response = client.getAllVaccinations(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", vaccinationCollectionList, response);
    }

    @Test
    public void getVaccinationsBetween() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + VACCINATIONS_PATH + "/"
                + encodedDate1 + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(vaccinationCollectionList));

        List<Vaccination> response = client.getVaccinationsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", vaccinationCollectionList, response);
    }

    @Test
    public void getVaccination() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + VACCINATIONS_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(vaccinationData));

        VaccinationData response = client.getVaccination(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", vaccinationData, response);
    }

    @Test
    public void getAllVetVisits() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + VET_VISITS_PATH), isNull(),
                        eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(vetVisitCollectionList));

        List<VetVisit> response = client.getAllVetVisits(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", vetVisitCollectionList, response);
    }

    @Test
    public void getVetVisitsBetween() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + VET_VISITS_PATH + "/"
                + encodedDate1 + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(vetVisitCollectionList));

        List<VetVisit> response = client.getVetVisitsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", vetVisitCollectionList, response);
    }

    @Test
    public void getVetVisit() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + VET_VISITS_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(vetVisitData));

        VetVisitData response = client.getVetVisit(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", vetVisitData, response);
    }

    @Test
    public void getAllWashes() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + WASHES_PATH), isNull(),
                eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(washCollectionList));

        List<Wash> response = client.getAllWashes(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", washCollectionList, response);
    }

    @Test
    public void getWashesBetween() throws MyPetCareException {
        given(httpClient
                .get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + WASHES_PATH + "/" + encodedDate1
                        + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(washCollectionList));

        List<Wash> response = client.getWashesBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", washCollectionList, response);
    }

    @Test
    public void getWashes() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + WASHES_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(washData));

        WashData response = client.getWash(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", washData, response);
    }

    @Test
    public void getAllWeights() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + WEIGHTS_PATH), isNull(),
                eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(weightCollectionList));

        List<Weight> response = client.getAllWeights(ACCESS_TOKEN, USERNAME, PET_NAME);
        assertEquals("Should return a list", weightCollectionList, response);
    }

    @Test
    public void getWeightsBetween() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + WEIGHTS_PATH + "/"
                + encodedDate1 + "/" + encodedDate3), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(weightCollectionList));

        List<Weight> response = client.getWeightsBetween(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1, DATE_3);
        assertEquals("Should return a list", weightCollectionList, response);
    }

    @Test
    public void getWeights() throws MyPetCareException {
        given(httpClient.get(eq(BASE_URL + PETS_PATH + encodedUsername + "/" + encodedPetName + WEIGHTS_PATH + "/"
                + encodedDate1), isNull(), eq(headers), isNull())).willReturn(httpResponse);
        given(httpResponse.asString()).willReturn(gson.toJson(weightData));

        WeightData response = client.getWeight(ACCESS_TOKEN, USERNAME, PET_NAME, DATE_1);
        assertEquals("Should return the specified element", weightData, response);
    }
}
