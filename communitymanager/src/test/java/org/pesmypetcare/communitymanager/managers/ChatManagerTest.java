package org.pesmypetcare.communitymanager.managers;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.pesmypetcare.communitymanager.ChatException;
import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.communitymanager.datacontainers.MessageReceiveData;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willCallRealMethod;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

/**
 * @author Santiago Del Rey
 */
@RunWith(MockitoJUnitRunner.class)
public class ChatManagerTest {
    private static final String GROUP_NAMES_PATH = "groups_names/";
    private List<DocumentSnapshot> documents;
    private MessageReceiveData messageReceiveData;
    private MutableLiveData<MessageDisplay> messageMutableLiveData = new MutableLiveData<>();

    @Mock
    private FirebaseFirestore db;
    @Mock
    private ListenerRegistration listenerRegistration;
    @Mock
    private DocumentReference documentReference;
    @Mock
    private CollectionReference collectionReference;
    @Mock
    private Task<DocumentSnapshot> task;
    @Mock
    private DocumentSnapshot documentSnapshot;
    @Mock
    private Query query;
    @Mock
    private QuerySnapshot querySnapshot;

    @InjectMocks
    private ChatManager chatManager = new ChatManager(db);
    private String group;
    private String forum;
    private String groupId;
    private String forumId;

    @Before
    public void setUp() {
        group = "Dogs";
        forum = "Walks";
        groupId = "1";
        forumId = "2";
    }

    /*@Test
    public void createMessageListener() throws ChatException {
        initializeMocks();
        given(task.isSuccessful()).willReturn(true);
        given(documentSnapshot.exists()).willReturn(true);

        chatManager.createMessageListener(group, forum, messageMutableLiveData);
        verify(db).document(or(eq(GROUP_NAMES_PATH + group), eq(GROUP_NAMES_PATH + group + "/forum_names/" + forum)));
        verify(db).collection(eq("groups/" + groupId + "/forums/" + forumId + "/messages"));
        verify(query).orderBy(eq("publicationDate"), same(Query.Direction.DESCENDING));
    }*/

    @Test
    public void removeListener() {
        chatManager.removeListener();
        verify(listenerRegistration).remove();
    }

    /*private void initializeMocks() {
        given(db.document(anyString())).willReturn(documentReference);
        given(documentReference.get()).willReturn(task);
        given(task.getResult()).willReturn(documentSnapshot);
        given(documentSnapshot.getString(eq("group"))).willReturn(groupId);
        given(documentSnapshot.getString(eq("forum"))).willReturn(forumId);
        given(db.collection(anyString())).willReturn(collectionReference);
        given(collectionReference.orderBy(anyString(), any(Query.Direction.class))).willReturn(query);
        given(query.addSnapshotListener(any())).willReturn(listenerRegistration);

        documents = new ArrayList<>();
        documents.add(documentSnapshot);
        given(querySnapshot.getDocuments()).willReturn(documents);
        given(documentSnapshot.toObject(any())).willReturn(messageReceiveData);
    }*/
}
