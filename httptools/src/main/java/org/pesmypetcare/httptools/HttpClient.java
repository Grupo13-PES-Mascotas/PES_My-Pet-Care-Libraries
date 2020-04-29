package org.pesmypetcare.httptools;

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

    public HttpResponse request(RequestMethod method, String url, HttpParameter[] params,
                                Map<String, String> headers, String body) throws MyPetCareException {
        return handleRequest(new HttpRequest(method, url, params, headers, body));
    }

    public HttpResponse handleRequest(HttpRequest req) throws MyPetCareException {
        HttpResponse res = null;
        int responseCode = -1;
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(req.getUrl()).openConnection();
            con.setDoInput(true);
            setHeaders(req, con);
            OutputStream os = null;
            try {
                con.setRequestMethod(req.getMethod().name());
                setBody(os, req, con);
                responseCode = con.getResponseCode();
                res = new HttpResponse(con);
                if (responseCode != 200) {
                    throw new MyPetCareException(res.toString(), res);
                }
            } finally {
                try {
                    os.close();
                } catch (Exception ignore) { }
            }
        } catch (IOException e) {
            throw new MyPetCareException(e.getMessage(), e, responseCode);
        }
        return res;
    }

    private void setBody(OutputStream os, HttpRequest req, HttpURLConnection con) throws IOException {
        if (req.getBody() != null) {
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Language", "UTF-8");
            byte[] bytes = req.getBody().getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
            con.setDoOutput(true);
            os = con.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
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
