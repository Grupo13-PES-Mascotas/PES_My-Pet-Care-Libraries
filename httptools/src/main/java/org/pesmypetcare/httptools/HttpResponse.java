package org.pesmypetcare.httptools;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private int statusCode;

    private HttpURLConnection connection;
    private InputStream is;
    private boolean consumed;
    private String responseAsString;
    private JSONObject json;
    private JSONArray jsonArray;

    public int getStatusCode() {
        return statusCode;
    }

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

    public InputStream asStream() {
        if (consumed) {
            throw new IllegalStateException("Stream has already been consumed");
        }
        return is;
    }

    public String asString() throws MyPetCareException {
        if (null == responseAsString) {
            BufferedReader br = null;
            InputStream stream = null;
            try {
                stream = asStream();
                if (null == stream) {
                    return null;
                }
                br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    buf.append(line).append("\n");
                }
                this.responseAsString = buf.toString();
                stream.close();
                consumed = true;
            } catch (IOException ioe) {
                throw new MyPetCareException(ioe.getMessage(), ioe);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ignore) {
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ignore) {
                    }
                }
                connection.disconnect();
            }
        }
        return responseAsString;
    }

    public JSONObject asJSONObject() throws MyPetCareException {
        if (json == null) {
            try {
                json = new JSONObject(asString());
            } catch (JSONException jsonEx) {
                if (responseAsString == null) {
                    throw new MyPetCareException(jsonEx.getMessage(), jsonEx);
                } else {
                    throw new MyPetCareException(jsonEx.getMessage() + ":" + this.responseAsString, jsonEx);
                }
            } finally {
                connection.disconnect();
            }
        }
        return json;
    }

    public JSONArray asJSONArray() throws MyPetCareException {
        if (jsonArray == null) {
            try {
                jsonArray = new JSONArray(asString());
            } catch (JSONException jsonEx) {
                throw new MyPetCareException(jsonEx.getMessage(), jsonEx);
            } finally {
                connection.disconnect();
            }
        }
        return jsonArray;
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpResponse{" +
            "statusCode=" + statusCode +
            ", is=" + is +
            ", consumed=" + consumed +
            ", responseAsString='" + responseAsString + '\'' +
            '}';
    }
}
