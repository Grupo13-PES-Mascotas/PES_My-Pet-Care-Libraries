package org.pesmypetcare.httptools;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
class HttpRequest {
    private final RequestMethod method;
    private final String url;
    private final HttpParameter[] parameters;
    private final Map<String, String> requestHeaders;
    private final String body;

    /**
     * Creates an http request with the given request method, url, parameters, request headers and body.
     *
     * @param method The request method
     * @param url The request url
     * @param parameters The request parameters
     * @param requestHeaders The request headers
     * @param body The request body
     */
    HttpRequest(RequestMethod method, String url, HttpParameter[] parameters, Map<String, String> requestHeaders,
            String body) {
        this.method = method;
        if (parameters != null && parameters.length > 0) {
            this.url = url + "?" + HttpParameter.encodeParams(parameters);
        } else {
            this.url = url;
        }
        this.parameters = null;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HttpParameter[] getParameters() {
        return Arrays.copyOf(parameters, parameters.length);
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public String getBody() {
        return body;
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpRequest{" + "method=" + method + ", url='" + url + '\'' + ", parameters=" + Arrays
                .toString(parameters) + ", requestHeaders=" + requestHeaders + ", body='" + body + '\'' + '}';
    }
}
