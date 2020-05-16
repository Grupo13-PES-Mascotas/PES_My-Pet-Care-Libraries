/*
package org.pesmypetcare.communitymanager.managers;

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
import org.pesmypetcare.communitymanager.datacontainers.MessageReceiveData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

*/
/**
 * @author Santiago Del Rey
 *//*

@RunWith(MockitoJUnitRunner.class)
public class ChatManagerTest {
    private List<DocumentSnapshot> documents;
    private MessageReceiveData messageReceiveData;

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
    private ChatManager chatManager = new ChatManager();

    @Before
    public void setUp() {
        lenient().when(db.document(anyString())).thenReturn(documentReference);
        given(documentReference.get()).willReturn(task);
        given(task.addOnCompleteListener(any())).willReturn(task);
        given(task.getResult()).willReturn(documentSnapshot);
        given(documentSnapshot.get(eq("group"))).willReturn("1");
        given(documentSnapshot.get(eq("forum"))).willReturn("2");
        given(db.collection(anyString())).willReturn(collectionReference);
        given(collectionReference.orderBy(anyString(), any(Query.Direction.class))).willReturn(query);
        given(query.addSnapshotListener(any())).willReturn(listenerRegistration);

        documents = new ArrayList<>();
        documents.add(documentSnapshot);
        given(querySnapshot.getDocuments()).willReturn(documents);
        given(documentSnapshot.toObject(any())).willReturn(messageReceiveData);
    }

    @Test
    public void createMessageListener() {
        given(task.isSuccessful()).willReturn(true);
        given(documentSnapshot.exists()).willReturn(true);
    }

    @Test
    public void removeListener() {
    }
}
*/
