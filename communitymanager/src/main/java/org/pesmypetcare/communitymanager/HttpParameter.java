package org.pesmypetcare.communitymanager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Santiago Del Rey
 */
public class HttpParameter {
    private String name;
    private String value;

    public HttpParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public HttpParameter(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public HttpParameter(String name, boolean value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    /**
     * Encodes the given params.
     * @param parameters The url parameters
     * @return The encoded string
     */
    public static String encodeParams(HttpParameter[] parameters) {
        if (null == parameters) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            if (i != 0) {
                builder.append('&');
            }
            builder.append(encode(parameters[i].name)).append('=').append(encode(parameters[i].value));
        }
        return builder.toString();
    }

    /**
     * Encodes the given value.
     * @param value The value to be encoded
     * @return The encoded string
     */
    public static String encode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder builder = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                builder.append("%2A");
            } else if (focus == '+') {
                builder.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length()
                && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                builder.append('~');
                i += 2;
            } else {
                builder.append(focus);
            }
        }
        return builder.toString();
    }
}
