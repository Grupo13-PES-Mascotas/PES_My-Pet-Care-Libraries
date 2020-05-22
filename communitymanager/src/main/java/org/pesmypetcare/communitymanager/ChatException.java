package org.pesmypetcare.communitymanager;

/**
 * @author Santiago Del Rey
 */
public class ChatException extends Exception {
    /**
     * Constructs a new exception with the specified message and cause.
     * @param message The error message
     * @param cause The error cause
     */
    public ChatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified message.
     * @param message The error message
     */
    public ChatException(String message) {
        this(message, null);
    }
}
