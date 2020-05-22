package org.pesmypetcare.httptools.exceptions;

import org.pesmypetcare.httptools.HttpResponse;

/**
 * @author Santiago Del Rey
 */
public class MyPetCareException extends Exception {
    private int statusCode = -1;
    private int errorCode = -1;
    private HttpResponse response;
    private String errorMessage;

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message The error message
     * @param cause The error cause
     */
    public MyPetCareException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message The error message
     */
    public MyPetCareException(String message) {
        this(message, (Throwable) null);
    }

    /**
     * Constructs a new exception with the specified message and http response.
     *
     * @param message The error message
     * @param res The http response that threw the error
     */
    public MyPetCareException(String message, HttpResponse res) {
        this(message);
        response = res;
        statusCode = res.getStatusCode();
    }

    /**
     * Constructs a new exception with the specified message, cause and status code.
     *
     * @param message The error message
     * @param cause The error cause
     * @param statusCode The status code of the request that threw the error
     */
    public MyPetCareException(String message, Exception cause, int statusCode) {
        this(message, cause);
        this.statusCode = statusCode;
    }

}
