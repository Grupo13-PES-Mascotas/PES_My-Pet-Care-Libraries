package org.pesmypetcare.communitymanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
     * @param group The group name
     * @param forum The forum name
     * @throws ChatException When the action fails
     */
    public void doAction(String group, String forum) throws ChatException {
        ChatManager manager = new ChatManager();
        manager.createMessageListener(group, forum, messageMutableLiveData);
    }
}
