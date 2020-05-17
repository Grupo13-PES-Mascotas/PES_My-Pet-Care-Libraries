package org.pesmypetcare.httptools;

import android.util.Log;

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

    public HttpResponse post(String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.POST, url, params, headers, body));
    }

    public HttpResponse get(String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.GET, url, params, headers, body));
    }

    public HttpResponse put(String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.PUT, url, params, headers, body));
    }

    public HttpResponse delete(String url, HttpParameter[] params, Map<String, String> headers, String body)
            throws MyPetCareException {
        return handleRequest(new HttpRequest(RequestMethod.DELETE, url, params, headers, body));
    }

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

    private void setHeaders(HttpRequest req, HttpURLConnection con) {
        if (req.getRequestHeaders() != null) {
            for (String key : req.getRequestHeaders().keySet()) {
                con.addRequestProperty(key, req.getRequestHeaders().get(key));
            }
        }
    }
}
