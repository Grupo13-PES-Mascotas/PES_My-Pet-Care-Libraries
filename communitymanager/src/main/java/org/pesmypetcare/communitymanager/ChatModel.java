package org.pesmypetcare.communitymanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.pesmypetcare.communitymanager.datacontainers.MessageData;
import org.pesmypetcare.communitymanager.managers.ChatManager;

/**
 * @author Santiago Del Rey
 */
public class ChatModel extends ViewModel {
    private MutableLiveData<MessageData> messageMutableLiveData = new MutableLiveData<>();

    public LiveData<MessageData> getMessage() {
        return messageMutableLiveData;
    }

    public ChatModel() { }

    public void doAction(String group, String forum) throws ChatException {
        ChatManager manager = new ChatManager();
        manager.createMessageListener(group, forum, messageMutableLiveData);
    }
}
