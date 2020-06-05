package org.pesmypetcare.usermanager.datacontainers.user;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     */
    public UserData(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.groupSubscriptions = new ArrayList<>();
        this.messagesBanned = 0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserData userData = (UserData) o;
        return Objects.equals(getUsername(), userData.getUsername()) && Objects
                .equals(getPassword(), userData.getPassword()) && Objects.equals(getEmail(), userData.getEmail())
               && Objects.equals(getGroupSubscriptions(), userData.getGroupSubscriptions()) && Objects
                       .equals(getMessagesBanned(), userData.getMessagesBanned());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail(), getGroupSubscriptions(), getMessagesBanned());
    }

    @NonNull
    @Override
    public String toString() {
        return "UserData{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", email='" + email
               + '\'' + ", groupSubscriptions=" + groupSubscriptions + ", messagesBanned=" + messagesBanned + '}';
    }
}
