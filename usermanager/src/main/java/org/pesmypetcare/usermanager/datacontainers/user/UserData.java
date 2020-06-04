package org.pesmypetcare.usermanager.datacontainers.user;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Santiago Del Rey
 */
public class UserData {
    private String username;
    private String password;
    private String email;
    private List<String> groupSubscriptions;
    private Integer messagesBanned;

    /**
     * Default constructor.
     */
    public UserData() {
    }

    /**
     * Creates a UserData object with the specified username, email and password.
     *
     * @param username The user's username
     * @param email The user's email
     * @param password The user's password
     * @param messagesBanned The number of messages from the user that are banned
     */
    public UserData(String username, String email, String password, Integer messagesBanned) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.groupSubscriptions = new ArrayList<>();
        this.messagesBanned = messagesBanned;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getGroupSubscriptions() {
        return groupSubscriptions;
    }

    public Integer getMessagesBanned() {
        return messagesBanned;
    }

    /**
     * Creates a user data JSONObject.
     *
     * @return The JSONObject for the user data
     * @throws JSONException Thrown to indicate a problem with the JSON API
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("email", email);
        json.put("password", password);
        return json;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserData{" + "username='" + username + '\'' + ", email='" + email + '\'' + ", password='" + password
            + '\'' + ", groupSubscriptions=" + groupSubscriptions + '\'' + ", messagesBanned=" + messagesBanned + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof UserData) {
            return ((UserData) obj).getUsername().equals(this.username) && ((UserData) obj).getEmail()
                    .equals(this.email) && ((UserData) obj).getPassword().equals(this.password) && ((UserData) obj)
                    .getGroupSubscriptions().equals(this.groupSubscriptions) && ((UserData) obj).getMessagesBanned()
                    .equals(this.messagesBanned);
        }
        return false;
    }
}
