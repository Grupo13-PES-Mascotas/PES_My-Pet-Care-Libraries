package org.pesmypetcare.communitymanager.managers;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import org.pesmypetcare.communitymanager.BuildConfig;
import org.pesmypetcare.communitymanager.ChatException;
import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.communitymanager.datacontainers.MessageReceiveData;

import java.io.IOException;

/**
 * @author Santiago Del Rey
 */
public class ChatManager {
    private static final String TAG = "ChatManager";
    private static final String GROUP_NAMES_PATH = "groups_names/";
    private static FirebaseApp firebaseApp;
    private FirebaseFirestore db;
    private String groupId;
    private String forumId;
    private ListenerRegistration listener;
    private ChatException exception;

    /**
     * Default constructor.
     */
    public ChatManager(Context context) {
        if (firebaseApp == null) {
            FirebaseOptions.Builder builder = new FirebaseOptions.Builder()
                    .setApplicationId(BuildConfig.FIREBASE_APPLICATION_ID).setApiKey(BuildConfig.API_KEY)
                    .setDatabaseUrl(BuildConfig.DATABASE_URL).setStorageBucket(BuildConfig.STORAGE_BUCKET);
            firebaseApp = FirebaseApp.initializeApp(context, builder.build());
        }
        db = FirebaseFirestore.getInstance(firebaseApp);
        exception = null;
        listener = null;
    }

    /**
     * Creates a ChatManager with the given instance of Firestore.
     *
     * @param firestore The Firestore instance
     */
    public ChatManager(FirebaseFirestore firestore) {
        db = firestore;
        exception = null;
        listener = null;
    }

    /**
     * Creates a listener for the forum messages.
     *
     * @param group The name of the group where the forum belongs
     * @param forum The name of the group
     * @param mutableData The mutable message data
     * @throws ChatException When the chat creation fails
     */
    public void createMessageListener(String group, String forum, MutableLiveData<MessageDisplay> mutableData)
            throws ChatException {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Path to group name: " + GROUP_NAMES_PATH + group);
        }
        System.out.println("HERE 1");
        db.document(GROUP_NAMES_PATH + group).get().addOnCompleteListener(task -> {
            System.out.println("HERE 2");
            if (task.isSuccessful()) {
                DocumentSnapshot groupDoc = task.getResult();
                if (groupDoc != null) {
                    if (groupDoc.exists()) {
                        groupId = groupDoc.getString("group");
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Group ID: " + groupId);
                        }
                        try {
                            System.out.println("HERE");
                            getForumIdAndCreateListener(group, forum, mutableData);
                        } catch (ChatException e) {
                            e.printStackTrace();
                            exception = e;
                        }
                    } else {
                        exception = new ChatException("No such group");
                    }
                } else {
                    exception = new ChatException("No such group");
                }
            } else {
                exception = new ChatException("Get group failed", task.getException());
            }
        });
        if (exception != null) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, exception.getMessage(), exception);
            }
            throw exception;
        }
    }

    /**
     * Gets the forum ID and creates the listener for its messages.
     *
     * @param group The name of the group where the forum belongs
     * @param forum The name of the forum
     * @param mutableData The mutable message data
     * @throws ChatException When the creation of the listener fails
     */
    private void getForumIdAndCreateListener(String group, String forum, MutableLiveData<MessageDisplay> mutableData)
            throws ChatException {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Path to forum name: " + GROUP_NAMES_PATH + group + "/forum_names/" + forum);
        }
        db.document(GROUP_NAMES_PATH + group + "/forum_names/" + forum).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot forumDoc = task.getResult();
                if (forumDoc != null) {
                    if (forumDoc.exists()) {
                        forumId = forumDoc.getString("forum");
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Forum ID: " + forumId);
                        }
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
                    exception = new ChatException("No such group");
                }
            } else {
                exception = new ChatException("Get forum failed", task.getException());
            }
        });
        if (exception != null) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, exception.getMessage(), exception);
            }
            throw exception;
        }
    }

    /**
     * Creates a message listener for the instance.
     *
     * @param mutableData The mutable message data
     * @throws ChatException When the listener creation fails
     */
    private void createListener(MutableLiveData<MessageDisplay> mutableData) throws ChatException {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Path to messages: " + "groups/" + groupId + "/forums/" + forumId + "/messages");
        }
        Query query = db.collection("groups/" + groupId + "/forums/" + forumId + "/messages")
                .orderBy("publicationDate", Query.Direction.DESCENDING);
        listener = query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                exception = new ChatException("Error creating the listener", e);
            } else if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    if (documentSnapshot.exists()) {
                        MessageReceiveData messageReceiveData = documentSnapshot.toObject(MessageReceiveData.class);
                        mutableData.setValue(new MessageDisplay(messageReceiveData));
                        if (BuildConfig.DEBUG) {
                            Log.i(TAG, "Message correctly retrieved");
                        }
                    }
                }
            }
        });
        if (exception != null) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, exception.getMessage(), exception);
            }
            throw exception;
        }
    }

    /**
     * Removes the message listener.
     */
    public void removeListener() {
        if (listener != null) {
            listener.remove();
        }
    }
}
