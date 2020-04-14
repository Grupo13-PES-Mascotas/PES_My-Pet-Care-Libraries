package org.pesmypetcare.usermanagerlib.clients;

import android.util.Base64;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;
import org.pesmypetcare.usermanagerlib.datacontainers.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class PetManagerClientTest {
    private static final String USERNAME = "user";
    private static final String ACCESS_TOKEN = "my-token";
    private static final String BIRTH_FIELD = "birth";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private final double recommendedKcal = 2.5;
    private final double weight = 45.3;
    private final int expectedResponseCode = 200;
    private StringBuilder json;
    private StringBuilder jsonAllPets;
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
            + "  \"recommendedKcal\": \"2.5\",\n"
            + "  \"washFreq\": \"3\"\n"
            + "}");

        jsonAllPets = new StringBuilder("[{\n"
            + "  \"name\": \"Linux\",\n"
            + "  \"body\": {\n"
            + "    \"gender\": \"Male\",\n"
            + "    \"breed\": \"Golden Retriever\",\n"
            + "    \"birth\": \"2020-02-13T10:30:00\",\n"
            + "    \"weight\": \"45.3\",\n"
            + "    \"pathologies\": \"\",\n"
            + "    \"recommendedKcal\": \"2.5\",\n"
            + "    \"washFreq\": \"3\"\n"
            + "  }\n"
            + "}]");
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
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
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
    public void shouldThrowAnExceptionWhenTaskExecutionFails() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.getPet(ACCESS_TOKEN, USERNAME, petName);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenTaskExecutionInterrupted() throws ExecutionException, InterruptedException {
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
    public void shouldThrowAnExceptionWhenExecutionFails() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        willThrow(ExecutionException.class).given(taskManager).get();
        client.getAllPets(ACCESS_TOKEN, USERNAME);
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowAnExceptionWhenExecutionInterrupted() throws ExecutionException, InterruptedException {
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
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
    }

    @Test
    public void updateField() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.updateField(ACCESS_TOKEN, USERNAME, petName, BIRTH_FIELD, "2019-02-13T10:30:00");
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenWrongType() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        client.updateField(ACCESS_TOKEN, USERNAME, petName, PetManagerClient.RECOMMENDED_KCAL, "23.3");
    }

    @Test
    public void saveProfileImage() throws ExecutionException, InterruptedException {
        given(taskManager.resetTaskManager()).willReturn(taskManager);
        given(taskManager.execute(anyString(), anyString())).willReturn(taskManager);
        given(taskManager.get()).willReturn(STATUS_OK);
        int responseCode = client.saveProfileImage(ACCESS_TOKEN, USERNAME, petName, image);
        assertEquals("Should return response code 200", expectedResponseCode, responseCode);
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
        expectedPetData.setRecommendedKcal(recommendedKcal);
        expectedPetData.setWashFreq(3);
        expectedPetData.setWeight(weight);
        pet.setBody(expectedPetData);
    }
}
