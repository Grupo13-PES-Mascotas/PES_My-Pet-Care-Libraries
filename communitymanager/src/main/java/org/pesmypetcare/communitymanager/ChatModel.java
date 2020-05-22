package org.pesmypetcare.communitymanager;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.communitymanager.managers.ChatManager;

/**
 * @author Santiago Del Rey
 */
public class ChatModel extends ViewModel {
    private MutableLiveData<MessageDisplay> messageMutableLiveData = new MutableLiveData<>();

    /**
     * Default constructor.
     */
    public ChatModel() {
    }

    public LiveData<MessageDisplay> getMessage() {
        return messageMutableLiveData;
    }

    /**
     * Starts the action defined in the observer.
     * @param context The application context
     * @param group The group name
     * @param forum The forum name
     * @throws ChatException When the action fails
     */
    public void doAction(Context context, String group, String forum) throws ChatException {
        ChatManager manager = new ChatManager(context);
        manager.createMessageListener(group, forum, messageMutableLiveData);
    }

    /**
     * Starts the action defined in the observer.
     * @param firestore The firestore instance for the database
     * @param group The group name
     * @param forum The forum name
     * @throws ChatException When the action fails
     */
    public void doAction(FirebaseFirestore firestore, String group, String forum) throws ChatException {
        ChatManager manager = new ChatManager(firestore);
        manager.createMessageListener(group, forum, messageMutableLiveData);
    }
}
