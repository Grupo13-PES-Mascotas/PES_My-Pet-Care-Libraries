package org.pesmypetcare.httptools;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author Santiago Del Rey
 */
public class HttpResponse {
    private static final String TAG = "HttpResponse";
    private int statusCode;

    private HttpURLConnection connection;
    private InputStream is;
    private boolean consumed;
    private String responseAsString;
    private JSONObject json;
    private JSONArray jsonArray;

    /**
     * Default constructor.
     */
    private HttpResponse() {
    }

    /**
     * Creates an http response for the given http connection.
     *
     * @param connection The http connection
     * @throws IOException If an error occurred connecting to the server
     */
    public HttpResponse(HttpURLConnection connection) throws IOException {
        this.connection = connection;
        statusCode = connection.getResponseCode();
        if ((is = connection.getErrorStream()) == null) {
            is = connection.getInputStream();
        }
        consumed = false;
        responseAsString = null;
        json = null;
        jsonArray = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the connection response as a stream.
     *
     * @return The connection response as stream
     */
    public InputStream asStream() {
        if (consumed) {
            throw new IllegalStateException("Stream has already been consumed");
        }
        return is;
    }

    /**
     * Returns the connection response as a string.
     *
     * @return The connection response as a string
     * @throws MyPetCareException If an error occurred connecting to the server
     */
    public String asString() throws MyPetCareException {
        if (null == responseAsString) {
            BufferedReader br = null;
            InputStream stream = null;
            try {
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "Start reading response");
                }
                stream = asStream();
                if (null == stream) {
                    return null;
                }
                br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    buf.append(line).append('\n');
                }
                this.responseAsString = buf.toString();
                stream.close();
                consumed = true;
            } catch (IOException ioe) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, ioe.getMessage(), ioe);
                }
                throw new MyPetCareException(ioe.getMessage(), ioe);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                        if (BuildConfig.DEBUG) {
                            Log.i(TAG, "InputStream closed");
                        }
                    } catch (IOException ignore) {
                        //Ignore exception when closing the input buffer
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                        if (BuildConfig.DEBUG) {
                            Log.i(TAG, "BufferedReader closed");
                        }
                    } catch (IOException ignore) {
                        //Ignore exception when closing the buffer
                    }
                }
                connection.disconnect();
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "Connection closed in asString() call");
                }
            }
        }
        return responseAsString;
    }

    /**
     * Returns the connection response as a JSONObject.
     *
     * @return The connection response as a JSONObject
     * @throws MyPetCareException If an error occurred connecting to the server
     */
    public JSONObject asJsonObject() throws MyPetCareException {
        if (json == null) {
            try {
                json = new JSONObject(asString());
            } catch (JSONException jsonEx) {
                if (responseAsString == null) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, jsonEx.getMessage(), jsonEx);
                    }
                    throw new MyPetCareException(jsonEx.getMessage(), jsonEx);
                } else {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, jsonEx.getMessage() + " : " + this.responseAsString, jsonEx);
                    }
                    throw new MyPetCareException(jsonEx.getMessage() + " : " + this.responseAsString, jsonEx);
                }
            } finally {
                connection.disconnect();
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "Connection closed in asJSONObject() call");
                }
            }
        }
        return json;
    }

    /**
     * Returns the connection response as a JSONArray.
     *
     * @return The connection response as a JSONArray
     * @throws MyPetCareException If an error occurred connecting to the server
     */
    public JSONArray asJsonArray() throws MyPetCareException {
        if (jsonArray == null) {
            try {
                jsonArray = new JSONArray(asString());
            } catch (JSONException jsonEx) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, jsonEx.getMessage(), jsonEx);
                }
                throw new MyPetCareException(jsonEx.getMessage(), jsonEx);
            } finally {
                connection.disconnect();
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "Connection closed in asJSONArray() call");
                }
            }
        }
        return jsonArray;
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpResponse{" + "statusCode=" + statusCode + ", is=" + is + ", consumed=" + consumed
                + ", responseAsString='" + responseAsString + '\'' + '}';
    }
}
