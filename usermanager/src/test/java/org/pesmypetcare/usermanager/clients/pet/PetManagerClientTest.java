package org.pesmypetcare.usermanager.clients.pet;

import android.util.Base64;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanager.clients.TaskManager;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;
import org.pesmypetcare.usermanager.datacontainers.pet.Pet;
import org.pesmypetcare.usermanager.datacontainers.pet.PetCollectionField;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Santiago Del Rey
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class PetManagerClientTest {
    private static final String USERNAME = "user";
    private static final String ACCESS_TOKEN = "my-token";
    private static final String BIRTH_FIELD = "birth";
    private static final String CODE_OK = "Should return response code 200";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final double RECOMMENDED_KCAL_EXAMPLE = 2.5;
    private static final String DATE_1 = "1990-01-08T15:20:30";
    private static final String DATE_2 = "1995-01-08T15:20:30";
    private static final String NEEDS_EXAMPLE = "None of your business";
    private final int expectedResponseCode = 200;
    private StringBuilder json;
    private StringBuilder jsonAllPets;
    private StringBuilder recKcalJson;
    private StringBuilder needsJson;
    private StringBuilder mealsFieldCollectionJson;
    private StringBuilder mealsFieldCollectionElementJson;
    private List<PetCollectionField> petCollectionFieldList;
    private Map<String, Object> collectionElementBody;
    private Pet pet;
    private PetData expectedPetData;
    private List<Pet> petList;
    private byte[] image;
    private String petName;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private PetManagerClient client = new PetManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        petName = "Linux";
        initializePet();
        json = new StringBuilder("{\n"
            + "  \"gender\": \"Male\",\n"
            + "  \"breed\": \"Golden Retriever\",\n"
            + "  \"birth\": \"2020-02-13T10:30:00\",\n"
            + "  \"weight\": \"45.3\",\n"
            + "  \"pathologies\": \"\",\n"
            + "  \"needs\": \"\",\n"
            + "  \"recommendedKcal\": \"2.5\"\n"
            + "}");
        jsonAllPets = new StringBuilder("[{\n"
            + "  \"name\": \"Linux\",\n"
            + "  \"body\": {\n"
            + "    \"gender\": \"Male\",\n"
            + "    \"breed\": \"Golden Retriever\",\n"
            + "    \"birth\": \"2020-02-13T10:30:00\",\n"
            + "    \"pathologies\": \"\",\n"
            + "    \"needs\": \"\",\n"
            + "    \"recommendedKcal\": \"2.5\"\n"
            + "  }\n"
            + "}]");
        recKcalJson = new StringBuilder("{\n"
            + "  \"recommendedKcal\": 2.5\n"
            + "}");
        needsJson = new StringBuilder("{\n"
            + "  \"needs\": \"None of your business\"\n"
            + "}");
        mealsFieldCollectionJson = new StringBuilder("[\n"
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
        mealsFieldCollectionElementJson = new StringBuilder("{\n"
                + "  \"kcal\": 85.44,\n"
                + "  \"mealName\": \"Tortilla\"\n"
                + "}"
        );
        collectionElementBody = new HashMap<>();
        collectionElementBody.put("kcal", 85.44);
        collectionElementBody.put("mealName", "Tortilla");

        petCollectionFieldList = new ArrayList<>();
        petCollectionFieldList.add(new PetCollectionField("1990-01-08T15:20:30", collectionElementBody));
        petCollectionFieldList.add(new PetCollectionField("1995-01-08T15:20:30", collectionElementBody));
        petCollectionFieldList.add(new PetCollectionField("1998-01-08T15:20:30", collectionElementBody));

        petList = new ArrayList<>();
        petList.add(pet);
        image = json.toString().getBytes();
    }

    @Test
    public void createPet() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.createPet(ACCESS_TOKEN, USERNAME, pet);
        assertEquals(CODE_OK, expectedResponseCode,
                responseCode);
    }

    @Test
    public void getPet() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(json);
        PetData response = client.getPet(ACCESS_TOKEN, USERNAME, petName);
        assertEquals("Should return the pet data", expectedPetData, response);
    }

    @Test(expected = ExecutionException.class)
    public void shouldThrowAnExceptionWhenTaskExecutionFails() throws ExecutionException,
            InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.getPet(ACCESS_TOKEN, USERNAME, petName);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenTaskExecutionInterrupted() throws ExecutionException,
            InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(InterruptedException.class).given(taskManager).get();
        client.getPet(ACCESS_TOKEN, USERNAME, petName);
    }

    @Test
    public void getAllPets() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(jsonAllPets);
        List<Pet> response = client.getAllPets(ACCESS_TOKEN, USERNAME);
        assertEquals("Should return all the pets data", petList, response);
    }

    @Test(expected = ExecutionException.class)
    public void shouldThrowAnExceptionWhenExecutionFails() throws ExecutionException,
            InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.getAllPets(ACCESS_TOKEN, USERNAME);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenExecutionInterrupted() throws ExecutionException,
            InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(InterruptedException.class).given(taskManager).get();
        client.getAllPets(ACCESS_TOKEN, USERNAME);
    }

    @Test
    public void deletePet() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deletePet(ACCESS_TOKEN, USERNAME, petName);
        assertEquals(CODE_OK, expectedResponseCode,
            responseCode);
    }

    @Test
    public void deleteAllPets() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deleteAllPets(ACCESS_TOKEN, USERNAME);
        assertEquals(CODE_OK, expectedResponseCode,
            responseCode);
    }

    @Test
    public void getSimpleFieldDouble() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(recKcalJson);
        Object response = client.getSimpleField(ACCESS_TOKEN, USERNAME, petName, PetData.RECOMMENDED_KCAL);
        assertEquals("Should return the gender value", RECOMMENDED_KCAL_EXAMPLE, response);
    }

    @Test
    public void getSimpleFieldString() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(needsJson);
        Object response = client.getSimpleField(ACCESS_TOKEN, USERNAME, petName, PetData.NEEDS);
        assertEquals("Should return the gender value", NEEDS_EXAMPLE, response);
    }

    @Test
    public void updateSimpleField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateSimpleField(ACCESS_TOKEN, USERNAME, petName, BIRTH_FIELD,
            "2019-02-13T10:30:00");
        assertEquals(CODE_OK, expectedResponseCode, responseCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenWrongType() throws ExecutionException,
            InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        client.updateSimpleField(ACCESS_TOKEN, USERNAME, petName, PetManagerClient.RECOMMENDED_KCAL,
                "23.3");
    }

    @Test
    public void deleteFieldCollection() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deleteFieldCollection(ACCESS_TOKEN, USERNAME, petName, PetData.MEALS);
        assertEquals(CODE_OK, expectedResponseCode, responseCode);
    }

    @Test
    public void getFieldCollection() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(mealsFieldCollectionJson);
        List<PetCollectionField> response = client.getFieldCollection(ACCESS_TOKEN, USERNAME, petName, PetData.MEALS);
        assertEquals("Should return a meals list", petCollectionFieldList, response);
    }

    @Test
    public void getFieldCollectionElementsBetweenKeys() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(mealsFieldCollectionJson);
        List<PetCollectionField> response = client.getFieldCollectionElementsBetweenKeys(ACCESS_TOKEN, USERNAME,
            petName, PetData.MEALS, DATE_1, DATE_2);
        assertEquals("Should return a meals list with elements between the keys", petCollectionFieldList,
            response);
    }

    @Test
    public void addFieldCollectionElement() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.addFieldCollectionElement(ACCESS_TOKEN, USERNAME, petName, PetData.MEALS, DATE_1,
            collectionElementBody);
        assertEquals(CODE_OK, expectedResponseCode, responseCode);
    }

    @Test
    public void deleteFieldCollectionElement() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.deleteFieldCollectionElement(ACCESS_TOKEN, USERNAME, petName, PetData.MEALS, DATE_1);
        assertEquals(CODE_OK, expectedResponseCode, responseCode);
    }

    @Test
    public void updateFieldCollectionElement() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateFieldCollectionElement(ACCESS_TOKEN, USERNAME, petName, PetData.MEALS, DATE_1,
            collectionElementBody);
        assertEquals(CODE_OK, expectedResponseCode, responseCode);
    }

    @Test
    public void getFieldCollectionElement() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(mealsFieldCollectionElementJson);
        Map<String, Object> response = client.getFieldCollectionElement(ACCESS_TOKEN, USERNAME, petName, PetData.MEALS,
            DATE_1);
        assertEquals("Should return the specified element", collectionElementBody, response);
    }

    @Test
    public void saveProfileImage() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.saveProfileImage(ACCESS_TOKEN, USERNAME, petName, image);
        assertEquals(CODE_OK, expectedResponseCode,
                responseCode);
    }

    @Test
    public void downloadProfileImage() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(json);
        mockStatic(Base64.class);
        given(Base64.decode(json.toString(), Base64.DEFAULT)).willReturn(image);
        byte[] response = client.downloadProfileImage(ACCESS_TOKEN, USERNAME, petName);
        assertEquals("Should return the pet's profile picture", image, response);
    }

    @Test(expected = ExecutionException.class)
    public void shouldThrowAnExceptionWhenDownloadExecutionFails()
        throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.downloadProfileImage(ACCESS_TOKEN, USERNAME, petName);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenDownloadExecutionInterrupted()
        throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(InterruptedException.class).given(taskManager).get();
        client.downloadProfileImage(ACCESS_TOKEN, USERNAME, petName);
    }

    @Test
    public void downloadAllProfileImages() throws ExecutionException, InterruptedException {
        StringBuilder responseJson = new StringBuilder("{\n"
            + "  \"Linux\": \"encodedImg\"\n"
            + "}");
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(responseJson);
        mockStatic(Base64.class);
        given(Base64.decode("encodedImg", Base64.DEFAULT)).willReturn(image);
        Map<String, byte[]> response = client.downloadAllProfileImages(ACCESS_TOKEN, USERNAME);
        Map<String, byte[]> expected = new HashMap<>();
        expected.put(petName, image);
        assertEquals("Should return all the pets profile pictures", expected, response);
    }

    @Test(expected = ExecutionException.class)
    public void shouldThrowAnExceptionWhenDownloadAllPetsPicturesExecutionFails()
        throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.downloadAllProfileImages(ACCESS_TOKEN, USERNAME);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenDownloadAllPetsPicturesExecutionInterrupted()
        throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(InterruptedException.class).given(taskManager).get();
        client.downloadAllProfileImages(ACCESS_TOKEN, USERNAME);
    }

    private void initializePet() {
        pet = new Pet();
        pet.setName(petName);
        expectedPetData = new PetData();
        expectedPetData.setBreed("Golden Retriever");
        expectedPetData.setGender(GenderType.Male);
        expectedPetData.setBirth("2020-02-13T10:30:00");
        expectedPetData.setPathologies("");
        expectedPetData.setNeeds("");
        expectedPetData.setRecommendedKcal(RECOMMENDED_KCAL_EXAMPLE);
        pet.setBody(expectedPetData);
    }
}
