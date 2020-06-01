package org.pesmypetcare.usermanager.clients.medal;

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
import org.pesmypetcare.usermanager.datacontainers.medal.MedalData;
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
 * @author Oriol Catalán
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"android.util.Base64"})
public class MedalManagerClientTest {
    private static final String USERNAME = "user";
    private static final String ACCESS_TOKEN = "my-token";
    private static final String CODE_OK = "Should return response code 200";
    private static final StringBuilder STATUS_OK = new StringBuilder("200");
    private static final String DATE_1 = "1990-01-08T15:20:30";
    private static final String DATE_2 = "1995-01-08T20:20:30";
    private final int expectedResponseCode = 200;
    private Map<String, Object> collectionElementBody;
    private MedalData medal;
    private List<MedalData> medalList;
    private byte[] image;
    private String medalName;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private MedalManagerClient client = new MedalManagerClient();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {

    }
}


