package org.pesmypetcare.communitymanager;

/**
 * @author Santiago Del Rey
 */
public class ChatException extends Exception {
    public ChatException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ChatException(String errorMessage) {
        this(errorMessage, null);
    }
}
