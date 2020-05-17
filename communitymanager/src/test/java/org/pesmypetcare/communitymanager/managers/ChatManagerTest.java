package org.pesmypetcare.communitymanager.managers;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @author Santiago Del Rey
 */
@RunWith(MockitoJUnitRunner.class)
public class ChatManagerTest {

    @Mock
    private FirebaseFirestore db;
    @Mock
    private ListenerRegistration listenerRegistration;

    @InjectMocks
    private ChatManager chatManager = new ChatManager(db);

    @Test
    public void removeListener() {
        chatManager.removeListener();
        verify(listenerRegistration).remove();
    }
}
