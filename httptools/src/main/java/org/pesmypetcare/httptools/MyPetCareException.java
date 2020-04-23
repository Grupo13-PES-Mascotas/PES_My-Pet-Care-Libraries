package org.pesmypetcare.httptools;

/**
 * @author Santiago Del Rey
 */
public class MyPetCareException extends Exception {
    private int statusCode = -1;
    private int errorCode = -1;
    private HttpResponse response;
    private String errorMessage = null;

    public MyPetCareException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyPetCareException(String message) {
        this(message, (Throwable) null);
    }

    public MyPetCareException(String message, HttpResponse res) {
        this(message);
        response = res;
        this.statusCode = res.getStatusCode();
    }

    public MyPetCareException(String message, Exception cause, int statusCode) {
        this(message, cause);
        this.statusCode = statusCode;
    }

}
