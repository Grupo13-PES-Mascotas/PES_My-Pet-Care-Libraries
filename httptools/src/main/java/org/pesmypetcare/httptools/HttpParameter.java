package org.pesmypetcare.httptools;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class HttpParameter {
    private String name;
    private String value;

    /**
     * Default constructor.
     */
    private HttpParameter() {
    }

    /**
     * Creates an http parameter with a string as value.
     *
     * @param name The parameter name
     * @param value The parameter value
     */
    public HttpParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Creates an http parameter with an integer as value.
     *
     * @param name The parameter name
     * @param value The parameter value
     */
    public HttpParameter(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    /**
     * Creates an http parameter with an boolean as value.
     *
     * @param name The parameter name
     * @param value The parameter value
     */
    public HttpParameter(String name, boolean value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    /**
     * Encodes the given params.
     *
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
     *
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
            } else if (focus == '%' && (i + 1) < encoded.length() && encoded.charAt(i + 1) == '7'
                    && encoded.charAt(i + 2) == 'E') {
                builder.append('~');
                i += 2;
            } else {
                builder.append(focus);
            }
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HttpParameter that = (HttpParameter) o;

        if (!Objects.equals(getName(), that.getName())) {
            return false;
        }
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpParameter{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
    }
}
