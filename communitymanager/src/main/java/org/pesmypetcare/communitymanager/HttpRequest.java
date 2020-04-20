package org.pesmypetcare.communitymanager;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Santiago Del Rey
 */
public class HttpRequest {
    private final RequestMethod method;
    private final String url;
    private final HttpParameter[] parameters;
    private final Map<String, String> requestHeaders;
    private final String body;

    public HttpRequest(RequestMethod method, String url, HttpParameter[] parameters, Map<String, String> requestHeaders,
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
        return parameters;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
            "method=" + method +
            ", url='" + url + '\'' +
            ", parameters=" + Arrays.toString(parameters) +
            ", requestHeaders=" + requestHeaders +
            ", body='" + body + '\'' +
            '}';
    }
}
