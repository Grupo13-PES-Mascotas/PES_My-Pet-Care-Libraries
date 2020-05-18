package org.pesmypetcare.httptools;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class HttpClient {
    private static final String TAG = "HttpClient";
    private static final int OK = 200;
    private static final int CREATED = 201;
    private static final int NO_CONTENT = 204;

    /**
     * Default constructor.
     */
    public HttpClient() {
    }

    /**
     * Makes a POST request.
     *
     * @param url The url of the request
     * @param params The parameters of the request
     * @param headers The headers of the request
     * @param body The body of the request
     * @return The request response
     * @throws MyPetCareException When the request fails
     */
    public HttpResponse post(@NonNull String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.POST, url, params, headers, body));
    }

    /**
     * Makes a GET request.
     *
     * @param url The url of the request
     * @param params The parameters of the request
     * @param headers The headers of the request
     * @param body The body of the request
     * @return The request response
     * @throws MyPetCareException When the request fails
     */
    public HttpResponse get(@NonNull String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.GET, url, params, headers, body));
    }

    /**
     * Makes a PUT request.
     *
     * @param url The url of the request
     * @param params The parameters of the request
     * @param headers The headers of the request
     * @param body The body of the request
     * @return The request response
     * @throws MyPetCareException When the request fails
     */
    public HttpResponse put(@NonNull String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.PUT, url, params, headers, body));
    }

    /**
     * Makes a DELETE request.
     *
     * @param url The url of the request
     * @param params The parameters of the request
     * @param headers The headers of the request
     * @param body The body of the request
     * @return The request response
     * @throws MyPetCareException When the request fails
     */
    public HttpResponse delete(@NonNull String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.DELETE, url, params, headers, body));
    }

    /**
     * Handles the connection of an http request.
     *
     * @param req The request to handle
     * @return The request response
     * @throws MyPetCareException When the request fails
     */
    private HttpResponse handleRequest(HttpRequest req) throws MyPetCareException {
        HttpResponse res;
        int responseCode = -1;
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "Start request: " + new Gson().toJson(req));
        }
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(req.getUrl()).openConnection();
            con.setDoInput(true);
            setHeaders(req, con);
            con.setRequestMethod(req.getMethod().name());
            setBody(req, con);
            responseCode = con.getResponseCode();
            res = new HttpResponse(con);
            if (responseCode != OK && responseCode != CREATED && responseCode != NO_CONTENT) {
                throw new MyPetCareException(res.asString(), res);
            }
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
            throw new MyPetCareException(e.getMessage(), e, responseCode);
        }
        return res;
    }

    /**
     * Sets the request body for an http connection.
     *
     * @param req The http request
     * @param con The http connection
     * @throws IOException When the writing of the body fails
     */
    private void setBody(HttpRequest req, HttpURLConnection con) throws IOException {
        if (req.getBody() != null) {
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Language", "UTF-8");
            byte[] bytes = req.getBody().getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(bytes);
                os.flush();
            } catch (IOException e) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, e.getMessage(), e);
                }
                throw e;
            }
        }
    }

    /**
     * Sets the request headers for a http connection.
     *
     * @param req The http request
     * @param con The http connection
     */
    private void setHeaders(HttpRequest req, HttpURLConnection con) {
        if (req.getRequestHeaders() != null) {
            for (String key : req.getRequestHeaders().keySet()) {
                con.addRequestProperty(key, req.getRequestHeaders().get(key));
            }
        }
    }
}
