package org.pesmypetcare.communitymanager.managers;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import org.pesmypetcare.communitymanager.ChatException;
import org.pesmypetcare.communitymanager.datacontainers.MessageData;

/**
 * @author Santiago Del Rey
 */
public class ChatManager {
    private FirebaseFirestore db;
    private String groupId;
    private String forumId;
    private ListenerRegistration listener;
    private ChatException exception;

    public ChatManager() {
        db = FirebaseFirestore.getInstance();
        exception = null;
        listener = null;
    }

    /**
     * Creates a chat instance for the forum.
     *
     * @param group The group name
     * @param forum The forum name
     * @throws ChatException When the chat creation fails
     */
    public void createMessageListener(String group, String forum, MutableLiveData<MessageData> mutableData) throws ChatException {
        db.document("groups_names/" + group).get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot groupDoc = task.getResult();
                    if (groupDoc.exists()) {
                        groupId = (String) groupDoc.get("group");
                        System.out.println("ANTES DEL GET FORUM");
                        try {
                            getForumId(group, forum, mutableData);
                        } catch (ChatException e) {
                            e.printStackTrace();
                            exception = e;
                        }
                    } else {
                        exception = new ChatException("No such group");
                    }
                } else {
                    exception = new ChatException("Get group failed", task.getException());
                }
            });
        if (exception != null) {
            throw exception;
        }
    }

    private void getForumId(String group, String forum, MutableLiveData<MessageData> mutableData) throws ChatException {
        db.document("groups_names/" + group + "/forums/" + forum).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot forumDoc = task.getResult();
                if (forumDoc.exists()) {
                    forumId = (String) forumDoc.get("forum");
                    System.out.println("Group: " + groupId);
                    System.out.println("Forum: " + forumId);
                    try {
                        createListener(mutableData);
                    } catch (ChatException e) {
                        e.printStackTrace();
                        exception = e;
                    }
                } else {
                    exception = new ChatException("No such forum");
                }
            } else {
                exception = new ChatException("Get forum failed", task.getException());
            }
        });
        if (exception != null) {
            throw exception;
        }
    }

    /**
     * Creates a message listener for the instance.
     *
     * @param mutableData The mutable message data
     * @throws ChatException When the listener creation fails
     */
    private void createListener(MutableLiveData<MessageData> mutableData) throws ChatException {
        Query query = db.collection("groups/" + groupId + "/forums/" + forumId + "/messages")
            .orderBy("publicationDate", Query.Direction.DESCENDING);
        listener = query.addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (e != null) {
                    exception = new ChatException("Error creating the listener", e);
                } else if (queryDocumentSnapshots != null) {
                    int i = 0;
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        System.out.println("HOLA " + i++);
                        if (documentSnapshot.exists()) {
                            mutableData.setValue(documentSnapshot.toObject(MessageData.class));
                        }
                    }
                }
            });
        if (exception != null) {
            throw exception;
        }
    }

    /**
     * Removes the message listener
     */
    public void removeListener() {
        if (listener != null) {
            listener.remove();
        }
    }
}
